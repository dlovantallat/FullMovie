package com.dlovan.fullmovie;

import rx.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dlovan on 7/2/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<Movies> getMovies(@Query("api_key") String apiKey);
}
