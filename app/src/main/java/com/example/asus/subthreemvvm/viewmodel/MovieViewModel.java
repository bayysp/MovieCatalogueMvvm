package com.example.asus.subthreemvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.model.MovieResponse;
import com.example.asus.subthreemvvm.service.MovieService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {

    private MovieService movieService;
    private String lang;

    private MutableLiveData<ArrayList<MovieItem>> listMovie = new MutableLiveData<>();


    public void setMovie(String lang){
        this.lang = lang;
        if (this.movieService == null){
            movieService = new MovieService();
        }

        movieService.getMovieApi().getMovie(lang).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                if (movieResponse != null && movieResponse.getResults() != null){
                    ArrayList<MovieItem> movieItems = movieResponse.getResults();
                    Log.d("MovieViewModel","onResponse success" + movieItems);
                    listMovie.postValue(movieItems);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("MovieViewModel","onFailure " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieItem>> getMovies(){
        return listMovie;
    }
}
