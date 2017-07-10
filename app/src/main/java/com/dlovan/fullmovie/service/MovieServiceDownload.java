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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dlovan on 7/8/17.
 */

public class MovieServiceDownload extends IntentService {

    public static final String DETAIL = "detail";

    public MovieServiceDownload() {
        super("Movie");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getMoviePopular("5ba1e2bf08bea434def560bf5014dbb8");
        getMovieTopRated("5ba1e2bf08bea434def560bf5014dbb8");
        int movieId = intent.getIntExtra(DETAIL, 0);
        if (movieId != 0) {
            getMovie(movieId, "5ba1e2bf08bea434def560bf5014dbb8");
        }

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

    private void getMovie(final int movieId, String apiKey) {
        MovieClient.getInstance()
                .getMovie(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("TAG", "In onError()");
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Log.i("TAG", "onNext: detail");
                        List<ContentValues> values = new ArrayList<>();
                        ContentValues value = new ContentValues();
                        value.put(Columns.DetailMovie.ID, movie.getId());
                        value.put(Columns.DetailMovie.TITLE, movie.getTitle());
                        value.put(Columns.DetailMovie.OVERVIEW, movie.getOverview());
                        value.put(Columns.DetailMovie.POSTER_PATH, movie.getPosterPath());
                        value.put(Columns.DetailMovie.BACKDROP_PATH, movie.getBackdropPath());
                        value.put(Columns.DetailMovie.VOTE, movie.getVoteAverage());
                        values.add(value);

                        getContentResolver()
                                .bulkInsert(MovieContentProvider.DetailMovie.withId(movieId),
                                        values.toArray(new ContentValues[values.size()]));
                    }
                });
    }

    private void movieInsert(List<Movie> list, String type) {
        List<ContentValues> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContentValues value = new ContentValues();
            Movie movie = list.get(i);
            value.put(Columns.ListMovie.ID, movie.getId());
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
