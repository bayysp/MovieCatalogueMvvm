package com.example.asus.subthreemvvm.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.notifications.DailyReminderReceiver;
import com.example.asus.subthreemvvm.notifications.NotificationsPreference;
import com.example.asus.subthreemvvm.notifications.ReleaseReceiver;

import static com.example.asus.subthreemvvm.utils.Utils.KEY_DAILY_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.KEY_RELEASE_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.STATE_DAILY_REMINDER;
import static com.example.asus.subthreemvvm.utils.Utils.STATE_RELEASE_REMINDER;

public class SettingsActivity extends AppCompatActivity {

    private Switch swDailyReminder, swReleaseReminder;

    private DailyReminderReceiver dailyReminderReceiver;
    private ReleaseReceiver releaseReceiver;

    private NotificationsPreference notificationsPreference;

    private SharedPreferences spDailyReminder, spReleaseReminder;
    private SharedPreferences.Editor editorDailyReminder, editorReleaseReminder;

    private static final String DAILY_REMINDER_ALARM = "dailyreminderalarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        swDailyReminder = findViewById(R.id.settings_switch_daily);
        swDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorDailyReminder = spDailyReminder.edit();
                if (isChecked) {
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, true);
                    editorDailyReminder.apply();
                    setOnDailyReminder();
                    Toast.makeText(getApplicationContext(), "Daily Reminder Get ON", Toast.LENGTH_SHORT).show();


                } else {
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER, false);
                    editorDailyReminder.apply();
                    setOffDailyReminder();
                    Toast.makeText(getApplicationContext(), "Daily Reminder Get OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });

        swReleaseReminder = findViewById(R.id.settings_switch_release);
        swReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorReleaseReminder = spReleaseReminder.edit();
                if (isChecked) {
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, true);
                    editorReleaseReminder.apply();
                    setOnReleaseReminder();
                    Toast.makeText(getApplicationContext(), "ReleaseReminder Get ON", Toast.LENGTH_SHORT).show();
                } else {
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER, false);
                    editorReleaseReminder.apply();
                    setOffReleaseReminder();
                    Toast.makeText(getApplicationContext(), "ReleaseReminder Get OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dailyReminderReceiver = new DailyReminderReceiver();
        releaseReceiver = new ReleaseReceiver();

        notificationsPreference = new NotificationsPreference(this);
        setPreferences();

        if (swDailyReminder.isChecked()) {
            swDailyReminder.setText("ON");
        } else {
            swDailyReminder.setText("OFF");
        }

        if (swReleaseReminder.isChecked()) {
            swReleaseReminder.setText("ON");
        } else {
            swReleaseReminder.setText("OFF");
        }


    }

    private void setPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER, MODE_PRIVATE);
        boolean setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER, false);
        swDailyReminder.setChecked(setStateDailyReminder);

        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER, MODE_PRIVATE);
        boolean setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER, false);
        swReleaseReminder.setChecked(setStateReleaseReminder);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    private void setOnDailyReminder() {
        String message = getResources().getString(R.string.set_daily_reminder);
        String time = "07:00";
        notificationsPreference.setTimeDailyReminder(time);
        notificationsPreference.setMessageDailyReminder(message);
        dailyReminderReceiver.setDailyReminderAlarm(SettingsActivity.this, DAILY_REMINDER_ALARM, time, message);
        Log.d("SettingActivity", "DailyReminder Alarm get on");
        swDailyReminder.setText("ON");
    }

    private void setOffDailyReminder() {
        dailyReminderReceiver.cancelAlarm(getApplicationContext());
        Log.d("SettingActivity", "DailyReminder Alarm get off");
        swDailyReminder.setText("OFF");
    }


    private void setOnReleaseReminder() {
        String message = "SET RELEASE REMINDER";
        String time = "23:05";
        notificationsPreference.setTimeRelaseReminder(time);
        notificationsPreference.setMessageReleaseReminderKey(message);
        releaseReceiver.checkMovieRelease(getApplicationContext());
        swReleaseReminder.setText("ON");
    }

    private void setOffReleaseReminder() {
        releaseReceiver.setOffReleaseNotification(getApplicationContext());
        swReleaseReminder.setText("OFF");
    }

}
