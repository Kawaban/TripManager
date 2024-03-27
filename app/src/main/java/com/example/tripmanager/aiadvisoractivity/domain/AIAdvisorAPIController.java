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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIAdvisorAPIController extends AsyncTask<String, Void, String> {
    private final OkHttpClient client;
    private final AppDatabase db;
    public AIAdvisorAPIController(Context applicationContext) {
        client = new OkHttpClient();
        db = AppDatabase.getInstance(applicationContext);
    }

    public String getRecommendations(String input) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, input);
        Request request = new Request.Builder()
                .url("https://chatgpt-api8.p.rapidapi.com/")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "chatgpt-api8.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string()).optString("text");
    }

    public String prepareInput() {
        JSONArray jsonObject = new JSONArray();
        List<TripEntity> trips =  db.tripDao().getAll();
        String tripsNames = "";
        for (TripEntity trip : trips) {
            tripsNames += trip.location + ", ";
        }
        try {
            JSONObject inputv1 = new JSONObject();
            inputv1.put("content", R.string.advisorRoleContent);
            inputv1.put("role", "system");
            jsonObject.put(inputv1);

            JSONObject inputv2 = new JSONObject();
            inputv2.put("content", R.string.advisorRequestPreambula + tripsNames);
            inputv2.put("role", "user");
            jsonObject.put(inputv2);


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
