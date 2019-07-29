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
import com.example.asus.subthreemvvm.adapter.FavoriteTvshowAdapter;
import com.example.asus.subthreemvvm.database.AppDatabase;
import com.example.asus.subthreemvvm.database.MovieModelDb;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabTvshowFragment extends Fragment {

    private RecyclerView rvTvshowFav;
    private ArrayList<MovieModelDb> listTvshowFav = new ArrayList<>();
    private AppDatabase appDatabase;
    private FavoriteTvshowAdapter favoriteTvshowAdapter;

    private TextView tvStatus;

    public TabTvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvshowFav = view.findViewById(R.id.fragment_tabtvshow_rv);
        tvStatus = view.findViewById(R.id.fragment_tabtvshow_tv);
        rvTvshowFav.setLayoutManager(new LinearLayoutManager(getContext()));

        if (this.appDatabase == null){
            appDatabase = AppDatabase.initDatabase(getContext());
            Log.d("TabTvshowFragment" , "init appDatabase");
        }

        listTvshowFav.addAll(appDatabase.movieDAO().getByCategory("tvshow"));
        favoriteTvshowAdapter = new FavoriteTvshowAdapter(getContext());
        favoriteTvshowAdapter.notifyDataSetChanged();
        favoriteTvshowAdapter.setData(listTvshowFav);

        rvTvshowFav.setAdapter(favoriteTvshowAdapter);

        if (listTvshowFav.isEmpty()){
            tvStatus.setVisibility(View.VISIBLE);
        }else{
            tvStatus.setVisibility(View.GONE);
        }
    }
}
