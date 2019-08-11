package com.example.asus.subthreemvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.model.MovieResponse;
import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.model.TvshowResponse;
import com.example.asus.subthreemvvm.service.MovieService;
import com.example.asus.subthreemvvm.service.TvshowService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvshowViewModel extends ViewModel {
    private TvshowService tvshowService;
    private String lang;
    private String tvshowName;

    private MutableLiveData<ArrayList<TvshowItem>> listTvshowSearch = new MutableLiveData<>();

    public void setTvshowSearch(String lang,String tvshowName){
        this.lang = lang;
        this.tvshowName = tvshowName;
        if (this.tvshowName == null){
            tvshowService = new TvshowService();
        }

        tvshowService.getTvshowApi().getSearchTvshow(lang,tvshowName).enqueue(new Callback<TvshowResponse>() {
            @Override
            public void onResponse(Call<TvshowResponse> call, Response<TvshowResponse> response) {
                TvshowResponse tvshowResponse = response.body();
                if (tvshowResponse != null && tvshowResponse.getResults() != null){
                    ArrayList<TvshowItem> movieItems = tvshowResponse.getResults();
                    Log.d("SearchMovieViewModel","onResponse success" + movieItems);
                    listTvshowSearch.postValue(movieItems);
                }
            }

            @Override
            public void onFailure(Call<TvshowResponse> call, Throwable t) {
                Log.d("SearchMovieViewModel","onFailure " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvshowItem>> getSearchMovies(){return listTvshowSearch;}
}
