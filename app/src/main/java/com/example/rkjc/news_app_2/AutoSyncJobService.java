package com.example.rkjc.news_app_2;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


public class AutoSyncJobService extends JobService {

    private AsyncTask mBackgroundTask;


    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        System.out.println("*** STOPPED SYNCJOBSERVICE *** ");
        return true;
    }

    @Override
    public boolean onStartJob(final JobParameters job) {

        mBackgroundTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                System.out.println("Job Finished");
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] params) {
                NewsItemRepository newsItemRepository = new NewsItemRepository(AutoSyncJobService.this);
                newsItemRepository.syncDatabase();


                Intent cancelIntent = new Intent(AutoSyncJobService.this, CancelIntentService.class);
                cancelIntent.setAction("CANCEL_ACTION");

                PendingIntent cancelPendingIntent = PendingIntent.getService(
                        AutoSyncJobService.this,
                        1499,
                        cancelIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Action cancelAction = new NotificationCompat.Action(R.drawable.ic_visibility_off_black_24dp,
                        "Cancel",
                        cancelPendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AutoSyncJobService.this);
                String textContent = "You can now check for new articles!!";
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(AutoSyncJobService.this, "news_app")
                        .setSmallIcon(R.drawable.ic_public_black_24dp)
                        .setContentTitle("News App Updated")
                        .setContentText(textContent)
                        .addAction(cancelAction)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                notificationManager.notify(1599, mBuilder.build());

                return null;
            }
        };

        mBackgroundTask.execute();
        return true;
    }


}
