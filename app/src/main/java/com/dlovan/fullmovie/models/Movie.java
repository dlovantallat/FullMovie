package com.dlovan.fullmovie.models;

/**
 * Movie contain all variable detail
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

    /**
     * @return backdrop path image
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @return the overview of the movie
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @return the vote of the movie
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * @return poster path image
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @return the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the movie id of the movie
     */
    public int getId() {
        return id;
    }
}
