package com.dlovan.fullmovie;

/**
 * Created by dlovan on 7/2/17.
 */

public class Movie {

    private String poster_path;
    private String title;

    public String getPoster_path() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public String getTitle() {
        return title;
    }
}
