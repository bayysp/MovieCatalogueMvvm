package com.example.asus.subthreemvvm.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieService {
    private Retrofit retrofit;

    public MovieApi getMovieApi(){
        String BASE_URL = "https://api.themoviedb.org/";

        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(MovieApi.class);
    }
}
