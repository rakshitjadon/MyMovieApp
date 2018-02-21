package com.example.rakshit.moviesapp;

import android.net.Uri;

/**
 * Created by rakshit on 13/02/18.
 */

public class Movie {
    private static final String LOG_TAG = Movie.class.getName();
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String API_KEY = "0b101b9c97ade062c5754b376c59939e";
    private String title;
    private String movieId;
    private String overview;
    private String releaseDate;
    private String posterPath;
    private String backDropPath;
    private Double voteAvg;

    public Movie(String id, String posterPath) {
        this.movieId = id;
        this.posterPath = posterPath;

    }

    public Movie(String movie_title, String image_poster, String plot_synopsis, String vote_average, String release_date) {
        title=movie_title;
        posterPath=image_poster;
        overview=plot_synopsis;
        voteAvg=Double.parseDouble(vote_average);
        releaseDate=release_date;
    }

    public  String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {

        return releaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public Uri getPoster() {
        return Uri.parse(IMAGE_BASE_URL).buildUpon().appendEncodedPath(posterPath).build();
    }

    public Uri getBackDrop() {
        return Uri.parse(IMAGE_BASE_URL).buildUpon().appendEncodedPath(backDropPath).build();
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

}