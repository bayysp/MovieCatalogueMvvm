package com.example.asus.subthreemvvm.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class NotificationsPreference {
    private final static String PREFERENCE_NAME = "reminderPreferences";
    public final static String DAILY_REMINDER_KEY = "dailyreminder";
    public final static String MESSAGE_DAILY_REMINDER_KEY = "dailyremindermessage";

    public final static String RELEASE_REMINDER_KEY = "releasereminder";
    public final static String MESSAGE_RELEASE_REMINDER_KEY = "releaseremindermessage";

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public NotificationsPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
    }

    public void setTimeDailyReminder(String time) {
        editor.putString(DAILY_REMINDER_KEY, time);
        editor.commit();
    }

    public void setMessageDailyReminder(String message) {
        editor.putString(MESSAGE_DAILY_REMINDER_KEY, message);
    }

    public void setTimeRelaseReminder(String time) {
        editor.putString(RELEASE_REMINDER_KEY, time);
        editor.commit();
    }

    public void setMessageReleaseReminderKey(String message) {
        editor.putString(MESSAGE_RELEASE_REMINDER_KEY, message);
    }
}
