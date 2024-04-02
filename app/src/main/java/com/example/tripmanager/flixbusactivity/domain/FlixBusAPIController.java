package com.example.tripmanager.flixbusactivity.domain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.tripmanager.flixbusactivity.FlixBusResultsActivity;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.infrastructure.database.CityEntity;
import com.example.tripmanager.infrastructure.model.BackgroundTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlixBusAPIController extends BackgroundTask<RequestDTO, ArrayList<ResponseDTO>> {
    private final AppDatabase db;
    private final OkHttpClient client;
    private final View mainLayout;
    private final View loadingLayout;

    public FlixBusAPIController(Context applicationContext, Activity activity, View mainLayout, View loadingLayout) {
        super(activity);
        client = new OkHttpClient();
        db = AppDatabase.getInstance(applicationContext);
        this.mainLayout = mainLayout;
        this.loadingLayout = loadingLayout;
    }

    public CityEntity requestFlixBusIdOfCity(String cityName) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url("https://flixbus2.p.rapidapi.com/autocomplete?query=" + cityName)
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "flixbus2.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String stringJson = response.body().string();
        JSONArray jsonObject = new JSONArray(stringJson);
        String flixBusId = jsonObject.getJSONObject(0).getJSONObject("city").getString("id");
        insertCity(cityName, flixBusId);
        return db.cityDao().findByName(cityName);
    }

    public void insertCity(String cityName, String flixBusId) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.city_name = cityName;
        cityEntity.flixbus_id = flixBusId;
        db.cityDao().insert(cityEntity);
    }


    public ArrayList<ResponseDTO> getResponses(RequestDTO requestDTO) throws IOException, JSONException {

        CityEntity origin = db.cityDao().findByName(requestDTO.getOrigin());
        if (origin == null) {
            origin = requestFlixBusIdOfCity(requestDTO.getOrigin());
        }
        CityEntity destination = db.cityDao().findByName(requestDTO.getDestination());
        if (destination == null) {
            destination = requestFlixBusIdOfCity(requestDTO.getDestination());
        }


        Request request = new Request.Builder()
                .url("https://flixbus2.p.rapidapi.com/trips?from_id=" + origin.flixbus_id + "&to_id=" + destination.flixbus_id + "&date=" + requestDTO.getDate() + "&adult=" + requestDTO.getNumberOfPassengers() + "&children=0&bikes=0&currency=EUR")
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "flixbus2.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        JSONObject responseJson = new JSONObject(response.body().string());
        ArrayList<ResponseDTO> responseDTOS = new ArrayList<ResponseDTO>();
        for (int i = 0; i < responseJson.getJSONArray("journeys").length(); i++) {
            responseDTOS.add(ResponseMapper.mapToResponseDTO(responseJson.getJSONArray("journeys").getJSONObject(i)));
        }
        return responseDTOS;
    }


    @Override
    public ArrayList<ResponseDTO> doInBackground(RequestDTO request) throws IOException, JSONException {
        return getResponses(request);
    }

    @Override
    public void onPreExecute() {
        mainLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(Activity activity, ArrayList<ResponseDTO> response) {
        Intent intent = new Intent(activity, FlixBusResultsActivity.class);
        intent.putParcelableArrayListExtra("responses", response);
        activity.startActivity(intent);
    }

    @Override
    public void onException() {
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
