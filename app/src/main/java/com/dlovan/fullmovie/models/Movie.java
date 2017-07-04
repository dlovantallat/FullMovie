package com.dlovan.fullmovie.models;

/**
 * Created by dlovan on 7/2/17.
 */

public class Movie {

    private String posterPath;
    private String title;

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getTitle() {
        return title;
    }
}
