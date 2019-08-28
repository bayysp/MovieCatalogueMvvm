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
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.model.TvshowItem;
import com.example.asus.subthreemvvm.view.activity.DetailMovieActivity;
import com.example.asus.subthreemvvm.view.activity.DetailTvshowActivity;

import java.util.ArrayList;

public class SearchTvshowAdapter extends RecyclerView.Adapter<SearchTvshowAdapter.ViewHolder> {

    private ArrayList<TvshowItem> tvshowItems = new ArrayList<>();
    private Context context;
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/";

    public SearchTvshowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TvshowItem> items) {
        tvshowItems.clear();
        tvshowItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchTvshowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        return new SearchTvshowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvshowAdapter.ViewHolder viewHolder, int i) {
        final TvshowItem item = tvshowItems.get(i);

        Glide.with(context).load(BASE_URL_IMAGE + item.getPosterPath()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.tvRating.setText(String.valueOf(item.getVoteAverage()));

        viewHolder.cvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, DetailTvshowActivity.class);

                    MovieItem items = new MovieItem();
                    items.setId(item.getId());
                    items.setTitle(item.getName());
                    items.setPosterPath(BASE_URL_IMAGE + item.getPosterPath());
                    items.setOverview(item.getOverview());
                    items.setVoteAverage(item.getVoteAverage());
                    items.setBackdropPath(item.getBackdropPath());

                    intent.putExtra(DetailMovieActivity.DETAIL_MOVIE, items);
                    context.startActivity(intent);
                    Log.d("SearchAdapterClickMovie", "MOVE INTO DETAIL ACTIVITY");
                } catch (Exception e) {
                    Log.d("SearchAdapterClickMovie", "GAGAL KLIK MOVIENYA");
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
        TextView tvTitle, tvRating;
        CardView cvSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvSearch = itemView.findViewById(R.id.search_item_cv);
            ivPoster = itemView.findViewById(R.id.search_item_ivposter);
            tvTitle = itemView.findViewById(R.id.search_item_tvtitle);
            tvRating = itemView.findViewById(R.id.search_item_tvrating);
        }
    }
}
