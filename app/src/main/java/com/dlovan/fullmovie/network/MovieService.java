package com.dlovan.fullmovie.network;

import com.dlovan.fullmovie.models.Movies;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dlovan on 7/2/17.
 */

public interface MovieService {

    @GET("movie/{type}")
    Observable<Movies> getMovies(@Path("type") String type, @Query("api_key") String apiKey);
}
