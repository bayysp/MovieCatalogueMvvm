package com.example.asus.subthreemvvm.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.subthreemvvm.R;
import com.example.asus.subthreemvvm.notifications.DailyReminderReceiver;
import com.example.asus.subthreemvvm.notifications.NotificationsPreference;
import com.example.asus.subthreemvvm.notifications.ReleaseReceiver;

public class SettingsActivity extends AppCompatActivity {

    private Switch swDailyReminder,swReleaseReminder;

    private DailyReminderReceiver dailyReminderReceiver;
    private ReleaseReceiver releaseReceiver;

    private NotificationsPreference notificationsPreference;

    private SharedPreferences spDailyReminder,spReleaseReminder;
    private SharedPreferences.Editor editorDailyReminder,editorReleaseReminder;

    private static final String KEY_DAILY_REMINDER = "dailyreminder";
    private static final String STATE_DAILY_REMINDER = "statedailyreminder";
    private static final String DAILY_REMINDER_ALARM = "dailyreminderalarm";

    private static final String KEY_RELEASE_REMINDER = "releasereminder";
    private static final String STATE_RELEASE_REMINDER = "statereleasereminder";
    private static final String RELEASE_REMINDER_ALARM = "releasereminderalarm";


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
                if (isChecked){
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER,true);
                    editorDailyReminder.apply();
                    setOnDailyReminder();
                    Toast.makeText(getApplicationContext(),"Daily Reminder Get ON",Toast.LENGTH_SHORT).show();


                }else{
                    editorDailyReminder.putBoolean(STATE_DAILY_REMINDER,false);
                    editorDailyReminder.apply();
                    setOffDailyReminder();
                    Toast.makeText(getApplicationContext(),"Daily Reminder Get OFF",Toast.LENGTH_SHORT).show();

                }
            }
        });

        swReleaseReminder = findViewById(R.id.settings_switch_release);
        swReleaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editorReleaseReminder = spReleaseReminder.edit();
                if (isChecked){
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER,true);
                    editorReleaseReminder.apply();
                    setOnReleaseReminder();
                    Toast.makeText(getApplicationContext(),"ReleaseReminder Get ON",Toast.LENGTH_SHORT).show();
                }else{
                    editorReleaseReminder.putBoolean(STATE_RELEASE_REMINDER,false);
                    editorReleaseReminder.apply();
                    setOffReleaseReminder();
                    Toast.makeText(getApplicationContext(),"ReleaseReminder Get OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dailyReminderReceiver = new DailyReminderReceiver();
        releaseReceiver = new ReleaseReceiver();

        notificationsPreference = new NotificationsPreference(this);
        setPreferences();

        if (swDailyReminder.isChecked()){
            swDailyReminder.setText("ON");
        }else{
            swDailyReminder.setText("OFF");
        }

        if (swReleaseReminder.isChecked()){
            swReleaseReminder.setText("ON");
        }else{
            swReleaseReminder.setText("OFF");
        }


    }

    private void setPreferences() {
        spDailyReminder = getSharedPreferences(KEY_DAILY_REMINDER,MODE_PRIVATE);
        boolean setStateDailyReminder = spDailyReminder.getBoolean(STATE_DAILY_REMINDER,false);
        swDailyReminder.setChecked(setStateDailyReminder);

        spReleaseReminder = getSharedPreferences(KEY_RELEASE_REMINDER,MODE_PRIVATE);
        boolean setStateReleaseReminder = spReleaseReminder.getBoolean(STATE_RELEASE_REMINDER,false);
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

    private void setOnDailyReminder(){
        String message = getResources().getString(R.string.set_daily_reminder);
        notificationsPreference.setTimeDailyReminder("07:00");
        notificationsPreference.setMessageDailyReminder(message);
        dailyReminderReceiver.setAlarmNotification(SettingsActivity.this,DAILY_REMINDER_ALARM,"07:00",message);
        Log.d("SettingActivity","DailyReminder Alarm get on");
        swDailyReminder.setText("ON");
    }

    private void setOffDailyReminder(){
        dailyReminderReceiver.cancelAlarm(getApplicationContext());
        Log.d("SettingActivity","DailyReminder Alarm get off");
        swDailyReminder.setText("OFF");
    }

    private void setOnReleaseReminder(){
        String message = getResources().getString(R.string.set_release_reminder);
        notificationsPreference.setTimeRelaseReminder("08:00");
        notificationsPreference.setMessageReleaseReminderKey(message);
        releaseReceiver.setAlarm(SettingsActivity.this,RELEASE_REMINDER_ALARM,"08:00",message);
        Log.d("SettingActivity","ReleaseReminder Alarm get on");
        swReleaseReminder.setText("ON");
    }

    private void setOffReleaseReminder(){
        releaseReceiver.cancelAlarm(getApplicationContext());
        Log.d("SettingActivity","ReleaseReminder Alarm get off");
        swReleaseReminder.setText("OFF");
    }


}
