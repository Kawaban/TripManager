package com.example.tripmanager.infrastructure.model;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
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

                    try {
                       A response = doInBackground(request);
                        activityRef.runOnUiThread(new Runnable() {
                            public void run() {
                                onPostExecute(activityRef, response);
                            }
                        });
                    } catch (IOException | JSONException e) {

                        activityRef.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(activityRef, "Error: " + "Cannot connect to the service", Toast.LENGTH_SHORT).show();
                                onException();
                            }
                        });
                    }


                }
            }
        }).start();
    }
    public void execute(T request){
        startBackground(request);
    }

    public abstract A doInBackground(T request) throws IOException, JSONException;
    public abstract void onPreExecute();
    public abstract void onPostExecute(Activity activity, A response);
    public abstract void onException();

}
