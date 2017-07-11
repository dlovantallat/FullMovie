package com.dlovan.fullmovie.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * use for rx_java Retrofit network
 * Created by dlovan on 7/2/17.
 */
public class ApiClient {

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";
    private static ApiClient instance;
    private ApiInterface mApiInterface;

    private ApiClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_MOVIE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        mApiInterface = retrofit.create(ApiInterface.class);
    }

    /**
     * this method make sure that this class has one instance
     *
     * @return the instance of this class
     */
    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    /**
     * Return created retrofit instance.
     *
     * @return {@link ApiInterface}
     */
    public ApiInterface getmApiInterface() {
        return mApiInterface;
    }
}
