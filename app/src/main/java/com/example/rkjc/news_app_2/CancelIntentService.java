package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class CancelIntentService extends IntentService{

    public CancelIntentService(){
        super("CancelIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        executeCancelTask(this, action);
    }

    public static void executeCancelTask(Context context, String action){
        clearAllNotifications(context, action);
    }

    public static void clearAllNotifications(Context context, String action) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
