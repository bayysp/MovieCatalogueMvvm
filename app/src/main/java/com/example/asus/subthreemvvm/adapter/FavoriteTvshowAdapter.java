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
import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.view.activity.DetailTvshowActivity;

import java.util.ArrayList;

public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.ViewHolder> {

    private ArrayList<MovieModelDb> tvshowItems = new ArrayList<>();
    private Context context;

    public FavoriteTvshowAdapter(Context context) {
        this.context = context;
    }

//    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/";

    public void setData(ArrayList<MovieModelDb> items) {
        tvshowItems.clear();
        tvshowItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTvshowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_tvshow_item, viewGroup, false);
        return new FavoriteTvshowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvshowAdapter.ViewHolder viewHolder, int i) {
        final MovieModelDb item = tvshowItems.get(i);

        Glide.with(context).load(item.getPosterPath()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvRating.setText(String.valueOf(item.getVoteAverage()));

        viewHolder.cvFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DetailTvshowActivity.class);

                    TvshowItem items = new TvshowItem();
                    items.setId(item.getId());
                    items.setName(item.getTitle());
                    items.setPosterPath(item.getPosterPath());
                    items.setOverview(item.getOverview());
                    items.setVoteAverage(item.getVoteAverage());

                    intent.putExtra(DetailTvshowActivity.DETAIL_TVSHOW, items);
                    context.startActivity(intent);
                    Log.d("AdapterClickTvshow", "MOVE INTO DETAIL ACTIVITY");
                } catch (Exception e) {
                    Log.d("AdapterClickTvshow", "GAGAL KLIK MOVIENYA");
                    Toast.makeText(context, "Gagal Menampilkan Detail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvshowItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvRating;
        CardView cvFilm;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.favorite_tvshow_item_ivposter);
            tvTitle = itemView.findViewById(R.id.favorite_tvshow_item_tvtitle);
            tvRating = itemView.findViewById(R.id.favorite_tvshow_item_tvrating);
            cvFilm = itemView.findViewById(R.id.favorite_tvshow_item_cv);
        }
    }
}
