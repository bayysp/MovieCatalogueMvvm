package com.example.asus.subthreemvvm.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.view.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ReleaseReceiver extends BroadcastReceiver {

    private final static int NOTIFICATION_ID = 101;

    private static final String EXTRA_MESSAGE_PREF = "message";
    private static final String EXTRA_TYPE_PREF = "type";

    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncHttpClient client = new AsyncHttpClient();

        Date dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = dateFormat.format(dateToday);


        String urlApi = "https://api.themoviedb.org/3/discover/movie?api_key=05faacecb1bb8a123ad56542b1708bad&primary_release_date.gte=" + dateFormatted + "&primary_release_date.lte=" + dateFormatted + "";
        Log.e("ReleaseReceiver", "get from " + urlApi);
        client.get(urlApi, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("ReleaseReceiver", "result is : " + result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    String movieName = responseObject.getJSONArray("result").getJSONObject(0).getString("title");

                    int idNotification = 501;
                    String message = "Today Movie Release is " + movieName;
                    sendNotifications(context, "MOVIE RELEASE", message, idNotification);
                } catch (Exception e) {
                    Log.e("ReleaseReceiver", "Error get API : " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("ReleaseReceiver", "onFailure: " + error.toString());
            }
        });

    }

    private void sendNotifications(Context context, String title, String message, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_black)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(uriTone);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, message);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 601, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Release Reminder Alarm Get OFF", Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 601, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, "Release Reminder Alarm Get OFF", Toast.LENGTH_SHORT).show();
    }
}
