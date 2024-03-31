package com.example.tripmanager.infrastructure.util;

import android.app.Activity;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class BackgroundTask<T,A> {

    private final WeakReference<Activity> activity;
    public BackgroundTask(Activity activity) {
        this.activity =  new WeakReference<Activity>(activity);
    }

    private void startBackground(T request) {
        new Thread(new Runnable() {
            public void run() {
                final Activity activityRef = activity.get();
                if (activityRef != null) {

                    activityRef.runOnUiThread(new Runnable() {
                        public void run() {
                            onPreExecute();
                        }
                    });

                    A response = doInBackground(request);
                    activityRef.runOnUiThread(new Runnable() {
                        public void run() {

                            onPostExecute(activityRef, response);
                        }
                    });

                }
            }
        }).start();
    }
    public void execute(T request){
        startBackground(request);
    }

    public abstract A doInBackground(T request);
    public abstract void onPreExecute();
    public abstract void onPostExecute(Activity activity, A response);

}
