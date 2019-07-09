package com.example.asus.subthreemvvm.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.model.MovieItem;

public class DetailMovieActivity extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle, tvOverview, tvRated;

    public static final String DETAIL_MOVIE = "detail_film";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ivPoster = findViewById(R.id.detail_activity_ivposter);
        tvTitle = findViewById(R.id.detail_activity_tvtitle);
        tvOverview = findViewById(R.id.detail_activity_tvdesc);
        tvRated = findViewById(R.id.detail_activity_tvrated);

        MovieItem getMovie = getIntent().getParcelableExtra(DETAIL_MOVIE);

        Toast.makeText(getApplicationContext(), getMovie.getTitle(), Toast.LENGTH_SHORT).show();


        Glide.with(getApplicationContext()).load(getMovie.getPosterPath()).into(ivPoster);

        tvTitle.setText(getMovie.getTitle());
        tvOverview.setText(getMovie.getOverview());
        tvRated.setText("Rate : " + String.valueOf(getMovie.getVoteAverage()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
