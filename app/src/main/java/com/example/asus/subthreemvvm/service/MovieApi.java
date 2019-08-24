package com.example.asus.subthreemvvm.service;

import com.example.asus.subthreemvvm.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("3/discover/movie?api_key=05faacecb1bb8a123ad56542b1708bad")
    Call<MovieResponse> getMovie(@Query("language")String language);

    @GET("3/search/movie?api_key=05faacecb1bb8a123ad56542b1708bad")
    Call<MovieResponse> getSearchMovie(@Query("language")String language,@Query("query")String query);

    @GET("3/discover/movie?api_key=05faacecb1bb8a123ad56542b1708bad")
    Call<MovieResponse> getNewReleaseMovie(@Query("primary_release_date.gte")String dateGte,@Query("primary_release_date.lte")String dateLte);
}
