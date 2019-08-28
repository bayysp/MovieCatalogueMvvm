package com.example.asus.subthreemvvm.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.adapter.SearchTvshowAdapter;
import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.viewmodel.SearchTvshowViewModel;

import java.util.ArrayList;

public class SearchTvshowActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchTvshowViewModel searchTvshowViewModel;
    private SearchTvshowAdapter searchTvshowAdapter;
    private RecyclerView rvSearchTvshow;
    private ProgressBar pbSearchTvshow;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tvshow);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchTvshowAdapter = new SearchTvshowAdapter(getApplicationContext());

        rvSearchTvshow = findViewById(R.id.searchactivitytv_rv);
        pbSearchTvshow = findViewById(R.id.searchactivitytv_pb);


        rvSearchTvshow.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
        searchTvshowAdapter = new SearchTvshowAdapter(getApplicationContext());
        searchTvshowAdapter.notifyDataSetChanged();

        searchTvshowViewModel = ViewModelProviders.of(this).get(SearchTvshowViewModel.class);
        searchTvshowViewModel.setTvshowSearch(getResources().getString(R.string.code_language), keyword);
        searchTvshowViewModel.getSearchTvshow().observe(this, getTvshowSearch);

        rvSearchTvshow.setAdapter(searchTvshowAdapter);

    }

    private Observer<ArrayList<TvshowItem>> getTvshowSearch = new Observer<ArrayList<TvshowItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvshowItem> tvshowItems) {
            if (tvshowItems != null) {
                Log.d("GetSearchTvData", String.valueOf(tvshowItems));
                searchTvshowAdapter.setData(tvshowItems);

                try {
                    showLoading(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
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
        Log.d("SeachTvshowActivity", "OptionsMenu - onQueryTextSubmit");
        try {
            searchData(s);
            return true;
        } catch (Exception e) {
            Log.d("SeachTvshowActivity", "OptionsMenu - onQueryTextSubmit - Error");
            Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("SeachTvshowActivity", "OptionsMenu - onQueryTextChange");
        try {
            searchData(s);
            return true;
        } catch (Exception e) {
            Log.d("SeachTvshowActivity", "OptionsMenu - onQueryTextChange - Error");
            Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            Log.e("SearchTvShowActivity", e.getMessage());
            return false;
        }
    }


    private void showLoading(boolean state) throws InterruptedException {
        if (state) {
            pbSearchTvshow.setVisibility(View.VISIBLE);
            Thread.sleep(1000);
            pbSearchTvshow.setVisibility(View.GONE);
        } else {
            pbSearchTvshow.setVisibility(View.GONE);
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
        finish();
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
