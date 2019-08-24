package com.example.asus.subthreemvvm.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.view.activity.DetailMovieActivity;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<MovieItem> movieItems = new ArrayList<>();
    private Context context;
    private Cursor mCursor;
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieItem> items) {
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setDataCursor(Cursor dataCursor){
        mCursor = dataCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int i) {
        final MovieItem item = movieItems.get(i);

        Glide.with(context).load(BASE_URL_IMAGE + item.getPosterPath()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvRating.setText(String.valueOf(item.getVoteAverage()));

        viewHolder.cvFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DetailMovieActivity.class);

                    MovieItem items = new MovieItem();
                    items.setId(item.getId());
                    items.setTitle(item.getTitle());
                    items.setPosterPath(BASE_URL_IMAGE + item.getPosterPath());
                    items.setOverview(item.getOverview());
                    items.setVoteAverage(item.getVoteAverage());
                    items.setBackdropPath(item.getBackdropPath());

                    intent.putExtra(DetailMovieActivity.DETAIL_MOVIE, items);
                    context.startActivity(intent);
                    Log.d("AdapterClickMovie", "MOVE INTO DETAIL ACTIVITY");
                } catch (Exception e) {
                    Log.d("AdapterClickMovie", "GAGAL KLIK MOVIENYA");
                    Toast.makeText(context, "Gagal Menampilkan Detail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvRating;
        CardView cvFilm;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.movie_item_ivposter);
            tvTitle = itemView.findViewById(R.id.movie_item_tvtitle);
            tvRating = itemView.findViewById(R.id.movie_item_tvrating);
            cvFilm = itemView.findViewById(R.id.movie_item_cv);

        }
    }
}
