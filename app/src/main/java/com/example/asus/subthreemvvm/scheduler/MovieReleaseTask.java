package com.example.asus.subthreemvvm.scheduler;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class MovieReleaseTask {
    private GcmNetworkManager gcmNetworkManager;

    public MovieReleaseTask(Context context) {
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setPeriod(3 * 60 * 100)
                .setPersisted(true)
                .setService(ReleaseReminderService.class)
                .setTag(ReleaseReminderService.TAG_RELEASE)
                .setFlex(10)
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }
}
