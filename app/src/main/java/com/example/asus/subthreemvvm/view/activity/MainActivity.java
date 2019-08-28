package com.example.asus.subthreemvvm.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.asus.subthreemvvm.MovieCatalogueProvider;
import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.adapter.MovieAdapter;
import com.example.asus.subthreemvvm.notifications.DailyReminderReceiver;
import com.example.asus.subthreemvvm.view.fragment.FavoriteFragment;
import com.example.asus.subthreemvvm.view.fragment.MovieFragment;
import com.example.asus.subthreemvvm.view.fragment.TvshowFragment;

import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_FRAGMENT;
import static com.example.asus.subthreemvvm.base.BaseAppCompatActivity.KEY_TITLE;
import static com.example.asus.subthreemvvm.utils.Utils.KEY_DAILY_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.KEY_RELEASE_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.STATE_DAILY_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.STATE_RELEASE_REMINDER;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNav;

    Fragment selectedFragment = new MovieFragment();
    String fragmentParam = "movie";
    MovieAdapter movieAdapter;
    boolean setStateDailyReminder, setStateReleaseReminder;

    String title = "Home";

    private SharedPreferences spDailyReminder, spReleaseReminder;

    DailyReminderReceiver dailyReminderReceiver;

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

        dailyReminderReceiver = new DailyReminderReceiver();
        setPreferences();

        movieAdapter = new MovieAdapter(getApplicationContext());

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            Log.d("MainActivityOptions", "selectedFragment is : " + selectedFragment);
            return true;
        }
        return false;
    }

    private void setPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER, false);
        Log.d("MainActivity", "preference daily value is : " + setStateDailyReminder);

        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER, false);
        Log.d("MainActivity", "preference release value is : " + setStateReleaseReminder);
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

        if (item.getItemId() == R.id.search_menu_activity) {
            if (fragmentParam.equals("movie")) {
                Log.d("MainActivity","move into SearchMovieActivity");
                Intent intent = new Intent(this, SearchMovieActivity.class);
                intent.putExtra("fragmentparam", fragmentParam);
                Log.d("MainActivityOptions", "Move to SearchMovieActivity");

                startActivity(intent);
            } else {
                fragmentParam = "tvshow";
                Intent intent = new Intent(this, SearchTvshowActivity.class);
                intent.putExtra("fragmentparam", fragmentParam);
                Log.d("MainActivityOptions", "Move to SearchTvshowActivity");

                startActivity(intent);
            }


        }

        if (item.getItemId() == R.id.action_reminder_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            Log.d("MainActivityOptions", "Move to SettingsActivity");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, title);

        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, selectedFragment);
    }

    private LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            switch (i) {
                case 1:
                    return new CursorLoader(
                            getApplicationContext(),
                            MovieCatalogueProvider.URI_FAVORITE,
                            new String[]{
                                    "title",
                                    "poster_path",
                                    "overview",
                                    "vote_average",
                                    "category"
                            },
                            null,
                            null,
                            null
                    );

                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            switch (loader.getId()) {
                case 1:
                    movieAdapter.setDataCursor(cursor);
                    break;
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            switch (loader.getId()) {
                case 1:
                    movieAdapter.setDataCursor(null);
                    break;
            }
        }
    };

}
