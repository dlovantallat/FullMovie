package com.dlovan.fullmovie.network;

import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;

import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by dlovan on 7/11/17.
 */

public class MovieApiClient {

    private static final String API_KEY = "5ba1e2bf08bea434def560bf5014dbb8";

    /**
     * get list of popular movies from the server
     *
     * @param moviesMovieCallback {@link MovieCallback<Movies>}
     */
    public static void getMoviePopular(final MovieCallback<Movies> moviesMovieCallback) {
        ApiClient.getInstance().
                getmApiInterface().
                getMoviesPopular(API_KEY).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesMovieCallback.onError(e);
                    }

                    @Override
                    public void onNext(Movies movies) {
                        moviesMovieCallback.onSuccess(movies);
                    }
                });
    }

    /**
     * get list of topRated movies from the server
     *
     * @param moviesMovieCallback {@link MovieCallback<Movies>}
     */
    public static void getMovieTopRated(final MovieCallback<Movies> moviesMovieCallback) {
        ApiClient.getInstance().
                getmApiInterface().
                getMoviesTopRated(API_KEY).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<Movies>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesMovieCallback.onError(e);
                    }

                    @Override
                    public void onNext(Movies movies) {
                        moviesMovieCallback.onSuccess(movies);
                    }
                });
    }

    /**
     * get movie data from the server
     *
     * @param movieId             each movie has own id in the list of movies
     * @param moviesMovieCallback {@link MovieCallback<Movie>}
     */
    public static void getMovie(int movieId, final MovieCallback<Movie> moviesMovieCallback) {
        ApiClient.getInstance().
                getmApiInterface().
                getMovie(movieId, API_KEY).
                subscribeOn(Schedulers.io()).
                subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesMovieCallback.onError(e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        moviesMovieCallback.onSuccess(movie);
                    }
                });
    }
}
