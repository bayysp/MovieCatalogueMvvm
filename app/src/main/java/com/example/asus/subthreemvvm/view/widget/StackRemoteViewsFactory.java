package com.example.asus.subthreemvvm.view.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.database.AppDatabase;
import com.example.asus.subthreemvvm.database.MovieModelDb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<MovieModelDb> favoriteWidgetItems = new ArrayList<>();
    private final Context mContext;
    private AppDatabase appDatabase;

    public StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (this.appDatabase == null) {
            appDatabase = AppDatabase.initDatabase(mContext);
            Log.d("StackRemoteViewsFactory", "init appDatabase");
        }


        favoriteWidgetItems.addAll(appDatabase.movieDAO().getMovieDb());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return favoriteWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_favorite);

        URL url;
        Bitmap myBitmap = null;
        try {
            url = new URL(favoriteWidgetItems.get(position).getPosterPath());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (IOException io) {
            Log.e("StackRemoteViews", io.getMessage());
        }

        remoteViews.setImageViewBitmap(R.id.imageView, myBitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.WIDGET_ITEM, position);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
