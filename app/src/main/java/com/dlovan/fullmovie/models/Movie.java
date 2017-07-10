package com.dlovan.fullmovie.models;

/**
 * Created by dlovan on 7/2/17.
 */

public class Movie {

    private String posterPath;
    private String title;
    private int id;
    private String overview;
    private double voteAverage;
    private String backdropPath;

    public Movie(int id, String title, String imagePath) {
        this.id = id;
        this.title = title;
        this.posterPath = imagePath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
