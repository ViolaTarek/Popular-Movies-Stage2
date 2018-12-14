package com.example.viola.movies.api;

import com.example.viola.movies.Reviews;
import com.example.viola.movies.Response.fetchMovies;
import com.example.viola.movies.Response.fetchTrailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface server {
    @GET("movie/top_rated")
    Call<fetchMovies> getTopRatedMovies(@Query("api_key") String apikey);

    @GET("movie/popular")
    Call<fetchMovies> getPopularMovies(@Query("api_key") String apikey);

    @GET("movie/{movie_id}/videos")
    Call<fetchTrailer> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apikey);
    @GET("movie/{movie_id}/reviews")
    Call<Reviews> getReview(@Path("movie_id") int id, @Query("api_key") String apikey);

}
