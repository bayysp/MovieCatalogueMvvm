package com.example.asus.subthreemvvm.service;

import com.example.asus.subthreemvvm.model.TvshowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvshowApi {
    @GET("3/discover/tv?api_key=05faacecb1bb8a123ad56542b1708bad")
    Call<TvshowResponse> getTvshow(@Query("language")String language);

    @GET("3/search/tv?api_key=05faacecb1bb8a123ad56542b1708bad")
    Call<TvshowResponse> getSearchTvshow(@Query("language")String language, @Query("query")String query);
}
