package com.example.asus.subthreemvvm.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.adapter.SearchMovieAdapter;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.viewmodel.SearchMovieViewModel;

import java.util.ArrayList;

import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_FRAGMENT;
import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_TITLE;

public class SearchMovieActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchMovieViewModel searchMovieViewModel;
    private SearchMovieAdapter searchMovieAdapter;
    private RecyclerView rvSearchMovie;
    private ProgressBar pbSearchMovie;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rvSearchMovie = findViewById(R.id.searchactivity_rv);
        pbSearchMovie = findViewById(R.id.searchactivity_pb);
        rvSearchMovie.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



//        searchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel.class);
//        searchMovieViewModel.setMovieSearch(getResources().getString(R.string.code_language), " ");
//        searchMovieViewModel.getSearchMovies().observe(this, getSearchMovie);


    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.input_text));
        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String keyword) {
        searchMovieAdapter = new SearchMovieAdapter(getApplicationContext());
        searchMovieAdapter.notifyDataSetChanged();

        searchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel.class);
        searchMovieViewModel.setMovieSearch(getResources().getString(R.string.code_language), keyword);
        searchMovieViewModel.getSearchMovies().observe(this, getSearchMovie);

        rvSearchMovie.setAdapter(searchMovieAdapter);
    }

    private Observer<ArrayList<MovieItem>> getSearchMovie = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieItem> movieItems) {
            if (movieItems != null) {
                Log.d("GetSearchMovieData", String.valueOf(movieItems));

                searchMovieAdapter.setData(movieItems);

                try {
                    showLoading(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    showLoading(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.d("SeachMovieActivity", "OptionsMenu - onQueryTextSubmit");
        try {
            searchData(s);
            return true;
        } catch (Exception e) {
            Log.d("SeachMovieActivity", "OptionsMenu - onQueryTextSubmit - Error");
            Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("SeachMovieActivity", "OptionsMenu - onQueryTextChange");
        try {
            searchData(s);
            return true;
        } catch (Exception e) {
            Log.d("SeachMovieActivity", "OptionsMenu - onQueryTextChange - Error");
            Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void showLoading(boolean state) throws InterruptedException {
        if (state) {
            pbSearchMovie.setVisibility(View.VISIBLE);
            Thread.sleep(1000);
            pbSearchMovie.setVisibility(View.GONE);
        } else {
            pbSearchMovie.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
