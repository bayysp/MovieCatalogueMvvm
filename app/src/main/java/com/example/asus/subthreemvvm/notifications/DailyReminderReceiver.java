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
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.view.activity.MainActivity;

import java.util.Calendar;

public class DailyReminderReceiver extends BroadcastReceiver {
    private final static int NOTIFICATION_ID = 101;

    private static final String EXTRA_MESSAGE_PREF = "message";
    private static final String EXTRA_TYPE_PREF = "type";

    public DailyReminderReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private void sendNotifications(Context context, String title,String desc,int id){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(
                        NOTIFICATION_ID,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context,desc)
                .setSmallIcon(R.drawable.ic_movie_black)
                .setContentTitle(desc)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context,android.R.color.transparent))
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(toneUri);

        if (notificationManager != null){
            notificationManager.notify(id,builder.build());
        }

    }

    public void setAlarmNotification(Context context,String type,String time,String message){
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_PREF,message);
        intent.putExtra(EXTRA_TYPE_PREF,type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);


    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
