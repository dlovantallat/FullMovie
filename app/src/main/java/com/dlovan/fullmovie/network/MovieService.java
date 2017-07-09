package com.dlovan.fullmovie.network;

import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dlovan on 7/2/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<Movies> getMoviesPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Observable<Movies> getMoviesTopRated(@Query("api_key") String apiKey);

    @GET("movie/{movieId}")
    Observable<Movie> getMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
