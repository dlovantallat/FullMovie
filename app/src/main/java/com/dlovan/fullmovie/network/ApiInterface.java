package com.dlovan.fullmovie.network;

import com.dlovan.fullmovie.models.Movie;
import com.dlovan.fullmovie.models.Movies;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * connect with movie client
 * Created by dlovan on 7/2/17.
 */
interface ApiInterface {

    /**
     * @param apiKey is a private key with my account in https://www.themoviedb.org
     * @return list of popular movie
     */
    @GET("movie/popular")
    Observable<Movies> getMoviesPopular(@Query("api_key") String apiKey);

    /**
     * @param apiKey is a private key with my account in https://www.themoviedb.org
     * @return list of top rated movie
     */
    @GET("movie/top_rated")
    Observable<Movies> getMoviesTopRated(@Query("api_key") String apiKey);

    /**
     * @param movieId each movie has own id in the list of movies
     * @param apiKey  is a private key with my account in https://www.themoviedb.org
     * @return all the detail of movie
     */
    @GET("movie/{movieId}")
    Observable<Movie> getMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
