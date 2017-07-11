package com.dlovan.fullmovie.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import com.dlovan.fullmovie.database.Columns;
import com.dlovan.fullmovie.database.MovieContentProvider;
import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;
import com.dlovan.fullmovie.network.MovieApiClient;
import com.dlovan.fullmovie.network.MovieCallback;

import java.util.ArrayList;
import java.util.List;

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
        MovieApiClient.getMoviePopular(new MovieCallback<Movies>() {
            @Override
            public void onSuccess(Movies movies) {
                movieInsert(movies.getResults(), "popular");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * get list of topRated movies from the server
     */
    private void getMovieTopRated() {
        MovieApiClient.getMovieTopRated(new MovieCallback<Movies>() {
            @Override
            public void onSuccess(Movies movies) {
                movieInsert(movies.getResults(), "top_rated");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * get movie data from the server
     *
     * @param movieId each movie has own id in the list of movies
     */
    private void getMovie(final int movieId) {

        MovieApiClient.getMovie(movieId, new MovieCallback<Movie>() {
            @Override
            public void onSuccess(Movie movie) {
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

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
