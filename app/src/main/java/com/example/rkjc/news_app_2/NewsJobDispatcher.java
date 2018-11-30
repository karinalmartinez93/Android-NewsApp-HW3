package com.example.rkjc.news_app_2;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class NewsJobDispatcher {

    private static final int SYNC_FLEXTIME_SECONDS = 10;

    private static final int REMINDER_INTERVAL_SECONDS = 10;

    private static final String SYNC_JOB_TAG = "news_api_sync";

    private static boolean sInitialized;

    synchronized public static void syncDatabaseWithDataFromAPI(@NonNull Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job syncJob = firebaseJobDispatcher.newJobBuilder()
                .setService(AutoSyncJobService.class)
                .setTag(SYNC_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER).setRecurring(true)
                .setTrigger(Trigger.executionWindow(REMINDER_INTERVAL_SECONDS,SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(syncJob);
        sInitialized = true;
    }
}
