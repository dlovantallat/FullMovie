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
 * Created by dlovan on 7/2/17.
 */

public class MovieClient {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";

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

    public static MovieClient getInstance() {
        if (instance == null) {
            instance = new MovieClient();
        }
        return instance;
    }

    public Observable<Movies> getMoviesPopular(String apiKey) {
        return movieService.getMoviesPopular(apiKey);
    }

    public Observable<Movies> getMoviesTopRated(String apiKey) {
        return movieService.getMoviesTopRated(apiKey);
    }

    public Observable<Movie> getMovie(int movieId, String apiKey) {
        return movieService.getMovie(movieId, apiKey);
    }
}
