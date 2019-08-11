package com.example.asus.subthreemvvm.view.activity;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.view.fragment.FavoriteFragment;
import com.example.asus.subthreemvvm.view.fragment.MovieFragment;
import com.example.asus.subthreemvvm.view.fragment.TvshowFragment;
import com.example.asus.subthreemvvm.viewmodel.SearchMovieViewModel;

import java.util.ArrayList;

import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_FRAGMENT;
import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_TITLE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;

    Fragment selectedFragment = new MovieFragment();
    String fragmentParam = "movie";

    String title = "Home";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            loadFragment(selectedFragment);
            setTitle(title);
        } else {
            selectedFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            title = savedInstanceState.getString(KEY_TITLE);
            loadFragment(selectedFragment);
            setTitle(title);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            Log.d("MainActivityOptions", "selectedFragment is : "+selectedFragment);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_bottomnav_movie:
                selectedFragment = new MovieFragment();
                fragmentParam = "movie";
                break;

            case R.id.menu_bottomnav_tvshows:
                selectedFragment = new TvshowFragment();
                fragmentParam = "tvshow";
                break;

            case R.id.menu_bottomnav_fav:
                selectedFragment = new FavoriteFragment();
                break;
        }
        return loadFragment(selectedFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        if (item.getItemId() == R.id.search_menu_activity){
            if (fragmentParam.equals("movie")){
                Intent intent = new Intent(this,SearchMovieActivity.class);
                intent.putExtra("fragmentparam",fragmentParam);
                Log.d("MainActivityOptions", "Move to SearchMovieActivity");

                startActivity(intent);
            }else{
                Intent intent = new Intent(this,SearchMovieActivity.class);
                intent.putExtra("fragmentparam",fragmentParam);
                Log.d("MainActivityOptions", "Move to SearchTvshowActivity");

                startActivity(intent);
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, title);

        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, selectedFragment);
    }

}
