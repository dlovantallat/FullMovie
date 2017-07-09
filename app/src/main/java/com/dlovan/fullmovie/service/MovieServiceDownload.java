package com.dlovan.fullmovie.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;
import com.dlovan.fullmovie.network.MovieClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dlovan on 7/8/17.
 */

public class MovieServiceDownload extends IntentService {

    private Subscription subscription;
    public static final String Api_key = "api_key";
    public static final String TYPE = "type";

    public MovieServiceDownload() {
        super("Movie");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String apiKey = intent.getStringExtra(Api_key);
        String type = intent.getStringExtra(TYPE);

        getMovie(type, apiKey);
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getMovie(final String type, String apiKey) {
        subscription = MovieClient.getInstance()
                .getMovies(type, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("TAG", "In onError()");
                    }

                    @Override
                    public void onNext(Movies movies) {

                        List<Movie> list = movies.getResults();

                        List<ContentValues> values = new ArrayList<>();

                        for (int i = 0; i < list.size(); i++) {
                            ContentValues value = new ContentValues();
                            Movie movie = list.get(i);
                            value.put(Columns.ListMovie.TITLE, movie.getTitle());
                            value.put(Columns.ListMovie.POSTER_PATH, movie.getPosterPath());
                            value.put(Columns.ListMovie.STATUS, type);

                            values.add(value);
                        }

                        getContentResolver().delete(MovieContentProvider.ListMovie.CONTENT_URI,
                                null, null);

                        getContentResolver()
                                .bulkInsert(MovieContentProvider.ListMovie.CONTENT_URI,
                                        values.toArray(new ContentValues[values.size()]));
                    }
                });
    }
}
