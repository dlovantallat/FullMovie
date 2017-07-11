package com.dlovan.fullmovie.network;

import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * use for rx_java Retrofit network
 * Created by dlovan on 7/2/17.
 */
public class MovieClient {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "5ba1e2bf08bea434def560bf5014dbb8";
    private static MovieClient instance;
    private MovieService movieService;

    private MovieClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        movieService = retrofit.create(MovieService.class);
    }

    /**
     * this method make sure that this class has one instance
     *
     * @return the instance of this class
     */
    public static MovieClient getInstance() {
        if (instance == null) {
            instance = new MovieClient();
        }
        return instance;
    }

    /**
     * @return list of popular movie
     */
    public Observable<Movies> getMoviesPopular() {
        return movieService.getMoviesPopular(API_KEY);
    }

    /**
     * @return list of top rated movie
     */
    public Observable<Movies> getMoviesTopRated() {
        return movieService.getMoviesTopRated(API_KEY);
    }

    /**
     * @param movieId each movie has own id in the list of movies
     * @return all the detail of movie
     */
    public Observable<Movie> getMovie(int movieId) {
        return movieService.getMovie(movieId, API_KEY);
    }
}
