package com.example.tripmanager.flixbusactivity.domain;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.tripmanager.infrastructure.database.CityDatabase;
import com.example.tripmanager.infrastructure.database.CityEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlixBusAPIController extends AsyncTask<RequestDTO, Void, ArrayList<ResponseDTO>> {
    private CityDatabase db;
    private OkHttpClient client;
    public FlixBusAPIController(Context applicationContext) {
       client = new OkHttpClient();
        db = Room.databaseBuilder(applicationContext,
                CityDatabase.class, "database-name").build();
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
        JSONObject jsonObject = new JSONObject(stringJson);;
        String flixBusId= jsonObject.getJSONArray("").getJSONObject(0).getString("id");
        insertCity(cityName, flixBusId);
        return db.cityDao().findByName(cityName);
    }

    public void insertCity(String cityName, String flixBusId) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.city_name = cityName;
        cityEntity.flixbus_id = flixBusId;
        db.cityDao().insertAll(cityEntity);
    }


    public ArrayList<ResponseDTO> getResponses(RequestDTO requestDTO) throws IOException, JSONException {

        CityEntity origin =db.cityDao().findByName(requestDTO.getOrigin());
        if(origin == null){
            origin = requestFlixBusIdOfCity(requestDTO.getOrigin());
        }
        CityEntity destination =db.cityDao().findByName(requestDTO.getDestination());
        if(destination == null){
            destination = requestFlixBusIdOfCity(requestDTO.getDestination());
        }



        Request request = new Request.Builder()
                .url("https://flixbus2.p.rapidapi.com/trips?from_id="+origin.flixbus_id+"&to_id="+destination.flixbus_id+"&date="+requestDTO.getDate()+"&adult="+requestDTO.getNumberOfPassengers()+"&children=0&bikes=0&currency=EUR")
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "flixbus2.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        JSONObject responseJson = new JSONObject(response.body().string());
        ArrayList<ResponseDTO> responseDTOS = new ArrayList<>();
        for(int i = 0; i < responseJson.getJSONArray("journeys").length(); i++){
            responseDTOS.add(ResponseMapper.mapToResponseDTO(responseJson.getJSONArray("journeys").getJSONObject(i)));
        }
        return responseDTOS;
    }




    @Override
    protected ArrayList<ResponseDTO> doInBackground(RequestDTO... request) {
        try {
            return getResponses(request[0]);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
