package com.example.asus.subthreemvvm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.model.TvshowItem;

import java.util.ArrayList;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TvshowItem> tvshowItems = new ArrayList<>();
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/";

    public TvshowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TvshowItem> items){
        tvshowItems.clear();
        tvshowItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvshowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tvshow_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvshowAdapter.ViewHolder viewHolder, int i) {
        final TvshowItem item = tvshowItems.get(i);

        Glide.with(context).load(BASE_URL_IMAGE+item.getPosterPath()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.tvRating.setText(String.valueOf(item.getVoteAverage()));

//        viewHolder.cvFilm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent intent = new Intent(context,DetailTvshowActivity.class);
//
//                    TvshowItem items = new TvshowItem();
//                    items.setName(item.getName());
//                    items.setPosterPath(BASE_URL_IMAGE+item.getPosterPath());
//                    items.setOverview(item.getOverview());
//                    items.setVoteAverage(item.getVoteAverage());
//                    items.setBackdropPath(item.getBackdropPath());
//
//                    intent.putExtra(DetailTvshowActivity.DETAIL_TVSHOW,items);
//                    context.startActivity(intent);
//                }catch (Exception e){
//                    Log.d("ClickMovie" , "GAGAL KLIK MOVIENYA");
//                    Toast.makeText(context,"Gagal Menampilkan Detail",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return tvshowItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPoster;
        TextView tvTitle;
        TextView tvRating;
        CardView cvFilm;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.tvshow_item_ivposter);
            tvTitle = itemView.findViewById(R.id.tvshow_item_tvtitle);
            tvRating = itemView.findViewById(R.id.tvshow_item_tvrating);
            cvFilm = itemView.findViewById(R.id.tvshow_item_cv);

        }

    }
}
