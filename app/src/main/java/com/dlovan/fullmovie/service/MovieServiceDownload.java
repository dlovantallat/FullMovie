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
import rx.schedulers.Schedulers;

/**
 * Created by dlovan on 7/8/17.
 */

public class MovieServiceDownload extends IntentService {

    public MovieServiceDownload() {
        super("Movie");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getMoviePopular("5ba1e2bf08bea434def560bf5014dbb8");
        getMovieTopRated("5ba1e2bf08bea434def560bf5014dbb8");
    }

    private void getMoviePopular(String apiKey) {
        MovieClient.getInstance()
                .getMoviesPopular(apiKey)
                .subscribeOn(Schedulers.io())
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
                        movieInsert(list, "popular");
                    }
                });
    }

    private void getMovieTopRated(String apiKey) {
        MovieClient.getInstance()
                .getMoviesTopRated(apiKey)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("TAG", "In onError()");
                    }

                    @Override
                    public void onNext(Movies movies) {
                        List<Movie> list = movies.getResults();
                        movieInsert(list, "top_rated");
                    }
                });
    }

    private void movieInsert(List<Movie> list, String type) {
        List<ContentValues> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContentValues value = new ContentValues();
            Movie movie = list.get(i);
            value.put(Columns.ListMovie.TITLE, movie.getTitle());
            value.put(Columns.ListMovie.POSTER_PATH, movie.getPosterPath());
            value.put(Columns.ListMovie.TYPE, type);
            values.add(value);
        }

        getContentResolver().delete(MovieContentProvider.ListMovie.withType(type),
                null, null);
        getContentResolver()
                .bulkInsert(MovieContentProvider.ListMovie.CONTENT_URI,
                        values.toArray(new ContentValues[values.size()]));
    }
}
