package com.example.asus.subthreemvvm.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.asus.subthreemvvm.database.MovieModelDb;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.view.activity.DetailMovieActivity;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private ArrayList<MovieModelDb> movieItems = new ArrayList<>();
    private Context context;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieModelDb> items) {
        movieItems.clear();
        movieItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_movie_item, viewGroup, false);
        return new FavoriteMovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder viewHolder, int i) {
        final MovieModelDb item = movieItems.get(i);

        Glide.with(context).load(item.getPosterPath()).into(viewHolder.ivPoster);
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
                    items.setPosterPath(item.getPosterPath());
                    items.setOverview(item.getOverview());
                    items.setVoteAverage(item.getVoteAverage());

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

            ivPoster = itemView.findViewById(R.id.favorite_movie_item_ivposter);
            tvTitle = itemView.findViewById(R.id.favorite_movie_item_tvtitle);
            tvRating = itemView.findViewById(R.id.favorite_movie_item_tvrating);
            cvFilm = itemView.findViewById(R.id.favorite_movie_item_cv);
        }
    }
}
