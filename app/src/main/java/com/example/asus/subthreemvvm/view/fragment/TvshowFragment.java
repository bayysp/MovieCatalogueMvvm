package com.example.asus.subthreemvvm.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.adapter.TvshowAdapter;
import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.viewmodel.TvshowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private TvshowAdapter tvshowAdapter;
    private ProgressBar pbTvshow;
    private RecyclerView rvTvshow;
    private TvshowViewModel tvshowViewModel;


    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("TvshowFragment","OnCreateView");
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvshowAdapter = new TvshowAdapter(getContext());
        tvshowAdapter.notifyDataSetChanged();

        pbTvshow = view.findViewById(R.id.fragment_tvshow_pb);
        rvTvshow = view.findViewById(R.id.fragment_tvshow_rv);
        rvTvshow.setLayoutManager(new LinearLayoutManager(getContext()));

        tvshowViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvshowViewModel.setTvshow(getResources().getString(R.string.code_language));
        tvshowViewModel.getMovies().observe(this,getTvshow);

        rvTvshow.setAdapter(tvshowAdapter);

    }

    private Observer<ArrayList<TvshowItem>> getTvshow = new Observer<ArrayList<TvshowItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvshowItem> tvshowItems) {
            if (tvshowItems != null){
                tvshowAdapter.setData(tvshowItems);
                showLoading(false);
            }else{
                showLoading(true);
            }
        }
    };

    private void showLoading(boolean state){
        if (state){
            pbTvshow.setVisibility(View.VISIBLE);
        }else{
            pbTvshow.setVisibility(View.GONE);
        }
    }
}
