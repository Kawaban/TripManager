package com.example.tripmanager.aiadvisoractivity.domain;


import android.content.Context;
import android.os.AsyncTask;


import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.infrastructure.database.TripEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIAdvisorAPIController extends AsyncTask<String, Void, String> {
    private final OkHttpClient client;
    private final AppDatabase db;
    private final Context applicationContext;
    public AIAdvisorAPIController(Context applicationContext) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout
        client = builder.build();
        db = AppDatabase.getInstance(applicationContext);
        this.applicationContext = applicationContext;
    }

    public String getRecommendations(String input) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, input);
        Request request = new Request.Builder()
            .url("https://open-ai21.p.rapidapi.com/qa")
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
            .addHeader("X-RapidAPI-Host", "open-ai21.p.rapidapi.com")
            .build();

        Response response = client.newCall(request).execute();
        String stringJson = response.body().string();
        JSONObject jsonObject = new JSONObject(stringJson);
        return jsonObject.optString("result");
    }

    public String prepareInput() {
        JSONObject jsonObject = new JSONObject();
        List<TripEntity> trips =  db.tripDao().getAll();
        StringBuilder tripsNames = new StringBuilder();
        for (TripEntity trip : trips) {
            tripsNames.append(trip.location).append(", ");
        }
        try {
            jsonObject.put("question", applicationContext.getResources().getString(R.string.question));
            jsonObject.put("context", applicationContext.getResources().getString(R.string.context) + tripsNames.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            return getRecommendations(prepareInput());
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
