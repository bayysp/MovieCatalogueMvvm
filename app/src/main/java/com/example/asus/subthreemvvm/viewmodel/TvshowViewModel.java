package com.example.asus.subthreemvvm.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.model.TvshowResponse;
import com.example.asus.subthreemvvm.service.TvshowService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvshowViewModel extends ViewModel {

    private TvshowService tvshowService;
    private String lang;

    private static final String API_KEY = "05faacecb1bb8a123ad56542b1708bad";
    private MutableLiveData<ArrayList<TvshowItem>> listTvshow = new MutableLiveData<>();


    public void setTvshow(String lang){
        this.lang = lang;
        if (this.tvshowService == null){
            tvshowService = new TvshowService();
        }

        tvshowService.getTvshowApi().getTvshow(lang).enqueue(new Callback<TvshowResponse>() {
            @Override
            public void onResponse(Call<TvshowResponse> call, Response<TvshowResponse> response) {
                TvshowResponse tvshowResponse = response.body();
                if (tvshowResponse != null && tvshowResponse.getResults() != null){
                    ArrayList<TvshowItem> tvshowItems = tvshowResponse.getResults();
                    Log.d("TvshowViewModel","onResponse success" + tvshowItems);
                    listTvshow.postValue(tvshowItems);
                }
            }

            @Override
            public void onFailure(Call<TvshowResponse> call, Throwable t) {
                Log.d("TvshowViewModel","onFailure success" + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvshowItem>> getMovies(){
        return listTvshow;
    }
}
