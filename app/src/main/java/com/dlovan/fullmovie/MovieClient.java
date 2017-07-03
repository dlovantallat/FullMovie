package com.dlovan.fullmovie;

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
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_MOVIE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        movieService = retrofit.create(MovieService.class);
    }

    public static MovieClient getInstance() {
        if (instance == null) {
            instance = new MovieClient();
        }
        return instance;
    }

    public Observable<Movies> getMovies(String type, String apiKey) {
        return movieService.getMovies(type, apiKey);
    }
}
