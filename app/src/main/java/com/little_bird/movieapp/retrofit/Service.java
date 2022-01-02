package com.little_bird.movieapp.retrofit;

import com.little_bird.movieapp.model.ListOfMovie;
import com.little_bird.movieapp.model.MovieResults;
import com.little_bird.movieapp.model.Slider;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/{category}")
    Call<MovieResults> getAllSlider(
            @Path("category") String category,
            @Query("api_key") String api_key);

    @GET("movie/{category}")
    Call<ListOfMovie> getItemCard(
            @Path("category") String category,
            @Query("api_key") String api_key);

//    @GET("movie/top_rated")
//
//
//    @GET("movie/{movie_id}/videos")
//    Call<List<Slider>> getAllSlider(@Query("api_key") String api_key);
}
