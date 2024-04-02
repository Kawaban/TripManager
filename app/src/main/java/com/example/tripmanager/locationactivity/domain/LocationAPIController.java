package com.example.tripmanager.locationactivity.domain;

import android.app.Activity;
import android.content.Intent;
import android.view.View;


import com.example.tripmanager.infrastructure.model.BackgroundTask;
import com.example.tripmanager.locationactivity.LocationResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationAPIController extends BackgroundTask<RequestDTO, ArrayList<ResponseDTO>> {
    private final OkHttpClient client;
    private final View mainLayout;
    private final View loadingLayout;

    public LocationAPIController(Activity activity, View mainLayout, View loadingLayout) {
        super(activity);
        client = new OkHttpClient();
        this.mainLayout = mainLayout;
        this.loadingLayout = loadingLayout;
    }

    public String getAttractionId(String city) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "q=" + city + "&language=en_US");
        Request request = new Request.Builder()
                .url("https://tourist-attraction.p.rapidapi.com/typeahead")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "tourist-attraction.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.getJSONObject("results").getJSONArray("data").getJSONObject(0).getJSONObject("result_object").optString("location_id");
    }

    public ArrayList<ResponseDTO> getAttractions(RequestDTO requestDTO) throws IOException, JSONException {
        String locationId = getAttractionId(requestDTO.getCity());
        System.out.println(locationId);
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "location_id=" + locationId + "&language=en_US&currency=USD&offset=0");
        Request request = new Request.Builder()
                .url("https://tourist-attraction.p.rapidapi.com/search")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "tourist-attraction.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = new JSONObject(responseString);
        ArrayList<ResponseDTO> responses = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJSONObject("results").getJSONArray("data").length(); i++) {
            responses.add(ResponseMapper.mapJSONAttractionsToResponseDTO(jsonObject.getJSONObject("results").getJSONArray("data").getJSONObject(i)));
        }
        return responses;

    }

    public String getRestaurantID(String city) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url("https://travel-advisor.p.rapidapi.com/locations/search?query=" + city + "&limit=30&offset=0&units=km&location_id=1&currency=USD&sort=relevance&lang=en_US")
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.getJSONArray("data").getJSONObject(0).getJSONObject("result_object").optString("location_id");
    }

    public ArrayList<ResponseDTO> getRestaurants(RequestDTO requestDTO) throws JSONException, IOException {
        String locationId = getRestaurantID(requestDTO.getCity());
        Request request = new Request.Builder()
                .url("https://travel-advisor.p.rapidapi.com/restaurants/list?location_id=" + locationId + "&restaurant_tagcategory=10591&restaurant_tagcategory_standalone=10591&currency=USD&lunit=km&limit=30&open_now=false&lang=en_US")
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "travel-advisor.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        JSONObject jsonObject = new JSONObject(responseString);
        ArrayList<ResponseDTO> responses = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
            ResponseDTO responseDTO = ResponseMapper.mapJSONRestaurantsToResponseDTO(jsonObject.getJSONArray("data").getJSONObject(i));
            if (responseDTO != null)
                responses.add(responseDTO);
        }
        return responses;

    }

    public ArrayList<ResponseDTO> getResponses(RequestDTO requestDTO) throws IOException, JSONException {
        ArrayList<ResponseDTO> responses = new ArrayList<>();
        if (requestDTO.getAttractions()) {
            responses.addAll(getAttractions(requestDTO));
        }
        if (requestDTO.getRestaurants()) {
            responses.addAll(getRestaurants(requestDTO));
        }
        return responses;
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
        Intent intent = new Intent(activity, LocationResultActivity.class);
        intent.putParcelableArrayListExtra("responses", response);
        activity.startActivity(intent);
    }

    @Override
    public void onException() {
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
