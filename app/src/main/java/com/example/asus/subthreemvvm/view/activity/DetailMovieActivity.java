package com.example.asus.subthreemvvm.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.database.AppDatabase;
import com.example.asus.subthreemvvm.database.MovieModelDb;
import com.example.asus.subthreemvvm.model.MovieItem;

import java.util.List;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView ivPoster;
    boolean isFavorite = false;
    TextView tvTitle, tvOverview, tvRated;
    private AppDatabase appDatabase;
    private MovieItem getMovie;

    private Menu menuItem = null;

    public static final String DETAIL_MOVIE = "detail_film";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ivPoster = findViewById(R.id.detail_activity_ivposter);
        tvTitle = findViewById(R.id.detail_activity_tvtitle);
        tvOverview = findViewById(R.id.detail_activity_tvdesc);
        tvRated = findViewById(R.id.detail_activity_tvrated);

        getMovie = getIntent().getParcelableExtra(DETAIL_MOVIE);

        Toast.makeText(getApplicationContext(), getMovie.getTitle(), Toast.LENGTH_SHORT).show();


        Glide.with(getApplicationContext()).load(getMovie.getPosterPath()).into(ivPoster);

        tvTitle.setText(getMovie.getTitle());
        tvOverview.setText(getMovie.getOverview());
        tvRated.setText(getResources().getString(R.string.rating) + " : " + String.valueOf(getMovie.getVoteAverage()));
        appDatabase = AppDatabase.initDatabase(getApplicationContext());
        favoriteState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_detail_favorite:
                if (isFavorite) {
                    deleteFavorite();
                    isFavorite = false;
                    setFavorite();
                } else {
                    saveToFavorite();
                    isFavorite = true;
                    setFavorite();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteFavorite() {
        try {
            MovieModelDb movieModelDb = new MovieModelDb();

            movieModelDb.setId(getMovie.getId());
            movieModelDb.setTitle(getMovie.getTitle());
            movieModelDb.setPosterPath(getMovie.getPosterPath());
            movieModelDb.setOverview(getMovie.getOverview());
            movieModelDb.setVoteAverage(getMovie.getVoteAverage());

            appDatabase.movieDAO().deleteMovie(movieModelDb);
            Toast.makeText(getApplicationContext(), "DELETE FROM FAVORITE", Toast.LENGTH_SHORT).show();
            Log.d("DeleteFromFavorite", "Success" );

        } catch (Exception e) {
            Log.d("DeleteFromFavorite", "failed delete" );
            Toast.makeText(getApplicationContext(), "FAILED DELETE FROM FAVORITE", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveToFavorite() {

        try {
            MovieModelDb movieModelDb = new MovieModelDb();

            movieModelDb.setId(getMovie.getId());
            movieModelDb.setTitle(getMovie.getTitle());
            movieModelDb.setPosterPath(getMovie.getPosterPath());
            movieModelDb.setOverview(getMovie.getOverview());
            movieModelDb.setVoteAverage(getMovie.getVoteAverage());

            appDatabase.movieDAO().insertMovie(movieModelDb);

            Toast.makeText(getApplicationContext(), "SAVE TO FAVORITE", Toast.LENGTH_SHORT).show();
            Log.d("SaveFromFavorite", "Success" );

        } catch (Exception e) {
            Log.d("SaveFromFavorite", "failed save" );
            Toast.makeText(getApplicationContext(), "FAILED SAVE", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        menuItem = menu;
        favoriteState();
        if (isFavorite) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_black_check));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_uncheck));
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavorite() {
        if (isFavorite) {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_black_check));
        } else {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_uncheck));
        }
    }

    public void favoriteState() {

        List<MovieModelDb> getById = appDatabase.movieDAO().getById(getMovie.getId());
        Log.d("FavotiteState", "get a data" );
        if (!getById.isEmpty()) {
            isFavorite = true;
        }
    }


}
