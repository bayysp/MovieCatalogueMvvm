package com.example.asus.subthreemvvm.notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.model.MovieItem;
import com.example.asus.subthreemvvm.model.MovieResponse;
import com.example.asus.subthreemvvm.service.MovieService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReceiver extends BroadcastReceiver {

    private static final String TAG = "ReleaseReceiver";
    private static final String ID = "id";
    private static final String TITLE = "title";

    private List<MovieItem> listMovie = new ArrayList<>();
    private List<MovieItem> mList = new ArrayList<>();
    private int id;
    private int delay;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive");
        id = intent.getIntExtra(ID,0);
        String title = intent.getStringExtra(TITLE);
        setMovieRelease(context,id,title);
    }

    public void checkMovieRelease(final Context context){
        Log.d(TAG,"checkMovieRelease called");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        final String dateNow = simpleDateFormat.format(date);

        MovieService movieService = new MovieService();
        movieService.getMovieApi().getNewReleaseMovie(dateNow,dateNow).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.d(TAG,"onresponse");
                if (response.body() != null){
                    Log.d(TAG,"onresponse not null");

                    List<MovieItem> result = response.body().getResults();
                    mList.clear();
                    mList.addAll(result);

                    for (MovieItem movieItem : mList){
                        if (movieItem.getReleaseDate().equals(dateNow)){
                            Log.d(TAG,"release today is equal");
                            listMovie.add(movieItem);
                        }else{
                            Log.d(TAG,"release today not found");
                        }
                    }
                    setReleaseNotification(context,listMovie);
                }else{
                    Log.d(TAG, "onResponse: not found");
                }


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(TAG, "onFailure called");
            }
        });
    }

    public void setReleaseNotification(Context context, List<MovieItem> movieItems) {
        Log.d(TAG,"releaseNotification");

        for (MovieItem movieItem : movieItems){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReleaseReceiver.class);
            intent.putExtra(ID, id);
            intent.putExtra(TITLE, movieItem.getTitle());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    id,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            id = id + 1;
            delay = delay + 2000;
            Log.d(TAG, "called movie " + id + " " + movieItem.getTitle());
        }
    }

    public void setMovieRelease(Context context, int id, String title) {
        Log.d(TAG,"setMovieRelease called");
        String CHANNEL_ID = "Channel_Daily";
        String CHANNEL_NAME = "Daily Reminder Alarm";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black)
                .setContentTitle(title)
                .setContentText("Release Movie Today")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(id, notification);
        }

    }

    public void setOffReleaseNotification(Context context) {
        Log.d(TAG, "cancelUpdateNotification: called");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                202,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.cancel(pendingIntent);
    }
}
