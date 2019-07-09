package com.example.asus.subthreemvvm.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvshowService {
    private Retrofit retrofit;

    public TvshowApi getTvshowApi(){
        String BASE_URL = "https://api.themoviedb.org/";

        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(TvshowApi.class);
    }
}
