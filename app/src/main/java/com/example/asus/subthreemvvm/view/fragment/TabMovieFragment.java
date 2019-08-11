package com.example.asus.subthreemvvm.view.fragment;


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
import android.widget.TextView;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.adapter.FavoriteMovieAdapter;
import com.example.asus.subthreemvvm.database.AppDatabase;
import com.example.asus.subthreemvvm.database.MovieModelDb;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabMovieFragment extends Fragment {

    //    private ProgressBar pbMovieFav;
    private RecyclerView rvMovieFav;
    private ArrayList<MovieModelDb> listMovieFav = new ArrayList<>();
    private AppDatabase appDatabase;
    private FavoriteMovieAdapter favoriteMovieAdapter;

    private TextView tvStatus;

    public TabMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovieFav = view.findViewById(R.id.fragment_tabmovies_rv);
        tvStatus = view.findViewById(R.id.fragment_tabmovies_tv);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(getContext()));

        if (this.appDatabase == null) {
            appDatabase = AppDatabase.initDatabase(getContext());
            Log.d("TabMovieFragment", "init appDatabase");
        }

        listMovieFav.addAll(appDatabase.movieDAO().getByCategory("movie"));
        favoriteMovieAdapter = new FavoriteMovieAdapter(getContext());
        favoriteMovieAdapter.notifyDataSetChanged();
        favoriteMovieAdapter.setData(listMovieFav);

        rvMovieFav.setAdapter(favoriteMovieAdapter);

        if (listMovieFav.isEmpty()) {
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            tvStatus.setVisibility(View.GONE);
        }
    }


}
