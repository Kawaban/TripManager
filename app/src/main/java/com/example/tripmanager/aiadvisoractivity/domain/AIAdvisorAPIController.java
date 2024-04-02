package com.example.tripmanager.aiadvisoractivity.domain;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.infrastructure.database.TripEntity;
import com.example.tripmanager.infrastructure.model.BackgroundTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIAdvisorAPIController extends BackgroundTask<RequestDTO, String> {
    private final OkHttpClient client;
    private final AppDatabase db;
    private TextView textViewOutput;
    private View mainLayout;
    private View loadingLayout;
    private final Context applicationContext;
    public AIAdvisorAPIController(Context applicationContext, Activity activity, View mainLayout, View loadingLayout, TextView textView) {
        super(activity);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout
        client = builder.build();
        db = AppDatabase.getInstance(applicationContext);
        this.applicationContext = applicationContext;
        this.textViewOutput=textView;
        this.mainLayout= mainLayout;
        this.loadingLayout = loadingLayout;
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

    public String prepareInput(RequestDTO requestDTO) {
        JSONObject jsonObject = new JSONObject();
        List<TripEntity> trips =  db.tripDao().getAll();
        StringBuilder tripsNames = new StringBuilder();
        for (TripEntity trip : trips) {
            tripsNames.append(trip.location).append(", ");
        }
        tripsNames.append(requestDTO.getCurrentLocation());
        try {
            jsonObject.put("question", applicationContext.getResources().getString(R.string.question));
            jsonObject.put("context", applicationContext.getResources().getString(R.string.context) + tripsNames.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    @Override
    public String doInBackground(RequestDTO requestDTO) throws IOException, JSONException {
            return getRecommendations(prepareInput(requestDTO));
    }

    @Override
    public void onPreExecute() {
        mainLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(Activity activity, String response) {
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        textViewOutput.setText(response);
    }

    @Override
    public void onException() {
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);

    }
}
