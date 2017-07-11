package com.dlovan.fullmovie.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

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
 * this is the service for the app
 * Created by dlovan on 7/8/17.
 */
public class MovieServiceDownload extends IntentService {

    public static final String DETAIL = "detail";

    public MovieServiceDownload() {
        super("Movie");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getMoviePopular();
        getMovieTopRated();
        int movieId = intent.getIntExtra(DETAIL, 0);
        if (movieId != 0) {
            getMovie(movieId);
        }
    }

    /**
     * get list of popular movies from the server
     */
    private void getMoviePopular() {
        MovieClient.getInstance()
                .getMoviesPopular()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Movies movies) {
                        List<Movie> list = movies.getResults();
                        movieInsert(list, "popular");
                    }
                });
    }

    /**
     * get list of topRated movies from the server
     */
    private void getMovieTopRated() {
        MovieClient.getInstance()
                .getMoviesTopRated()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Movies movies) {
                        List<Movie> list = movies.getResults();
                        movieInsert(list, "top_rated");
                    }
                });
    }

    /**
     * get movie data from the server
     *
     * @param movieId each movie has own id in the list of movies
     */
    private void getMovie(final int movieId) {
        MovieClient.getInstance()
                .getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Movie movie) {
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

    /**
     * insert data to the database
     *
     * @param list list of movies
     * @param type popular or topRated
     */
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

        //delete popular or topRated before added new data
        getContentResolver().delete(MovieContentProvider.ListMovie.withType(type),
                null, null);
        getContentResolver()
                .bulkInsert(MovieContentProvider.ListMovie.CONTENT_URI,
                        values.toArray(new ContentValues[values.size()]));
    }
}
