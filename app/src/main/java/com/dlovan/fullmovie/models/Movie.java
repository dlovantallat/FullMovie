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
    private String status;

    public Movie(String title, String image, String status) {
        this.title = title;
        this.posterPath = image;
        this.status = status;
    }

    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/w500" + backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
