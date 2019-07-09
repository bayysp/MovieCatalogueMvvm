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
import com.example.asus.subthreemvvm.adapter.MovieAdapter;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.viewmodel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ProgressBar pbMovie;
    private RecyclerView rvMovie;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("MovieFragment","OnCreateView");
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        pbMovie = view.findViewById(R.id.fragment_movies_pb);
        rvMovie = view.findViewById(R.id.fragment_movies_rv);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));




        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.setMovie(getResources().getString(R.string.code_language));

        movieViewModel.getMovies().observe(this,getMovie);

        rvMovie.setAdapter(movieAdapter);
    }

    private Observer<ArrayList<MovieItem>> getMovie = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieItem> movieItems) {
            if (movieItems != null){
                movieAdapter.setData(movieItems);
                showLoading(false);
            }else{
                showLoading(true);
            }
        }
    };

    private void showLoading(boolean state){
        if (state == true){
            pbMovie.setVisibility(View.VISIBLE);
        }else{
            pbMovie.setVisibility(View.GONE);
        }
    }


}
