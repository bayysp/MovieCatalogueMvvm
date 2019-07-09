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
import com.example.asus.subthreemvvm.model.TvshowItem;

public class DetailTvshowActivity extends AppCompatActivity {

    ImageView ivPoster;
    TextView tvTitle, tvOverview, tvRated;

    public static final String DETAIL_TVSHOW = "detail_tvshow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        ivPoster = findViewById(R.id.detail_activity_tv_ivposter);
        tvTitle = findViewById(R.id.detail_activity_tv_tvtitle);
        tvOverview = findViewById(R.id.detail_activity_tv_tvdesc);
        tvRated = findViewById(R.id.detail_activity_tv_tvrated);

        TvshowItem getTvshow = getIntent().getParcelableExtra(DETAIL_TVSHOW);

        Toast.makeText(getApplicationContext(), getTvshow.getName(), Toast.LENGTH_SHORT).show();


        Glide.with(getApplicationContext()).load(getTvshow.getPosterPath()).into(ivPoster);

        tvTitle.setText(getTvshow.getName());
        tvOverview.setText(getTvshow.getOverview());
        tvRated.setText("Rate : " + String.valueOf(getTvshow.getVoteAverage()));
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
