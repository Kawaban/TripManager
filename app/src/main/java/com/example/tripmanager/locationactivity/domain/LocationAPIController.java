package com.example.tripmanager.locationactivity.domain;

import android.os.AsyncTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationAPIController extends AsyncTask<RequestDTO, Void, ArrayList<ResponseDTO>> {
    private final OkHttpClient client;
    public LocationAPIController() {
        client = new OkHttpClient();
    }

    public String getAttractionId(String city) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "q="+city+"&language=en_US");
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

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "location_id="+locationId+"&language=en_US&currency=USD&offset=0");
        Request request = new Request.Builder()
                .url("https://tourist-attraction.p.rapidapi.com/search")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "tourist-attraction.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("results");
        ArrayList<ResponseDTO> responses = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
            responses.add(ResponseMapper.mapJSONAttractionsToResponseDTO(jsonObject.getJSONArray("data").getJSONObject(i)));
        }
        return responses;

    }

    public String getRestaurantID(String city) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url("https://tripadvisor16.p.rapidapi.com/api/v1/restaurant/searchLocation?query="+city)
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.getJSONArray("data").getJSONObject(0).optString("documentId");
    }

    public ArrayList<ResponseDTO> getRestaurants(RequestDTO requestDTO) throws JSONException, IOException {
        String locationId = getRestaurantID(requestDTO.getCity());

        Request request = new Request.Builder()
                .url("https://tripadvisor16.p.rapidapi.com/api/v1/restaurant/searchRestaurants?locationId="+locationId)
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("data");
        ArrayList<ResponseDTO> responses = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJSONArray("data").length(); i++) {
            responses.add(ResponseMapper.mapJSONRestaurantsToResponseDTO(jsonObject.getJSONArray("data").getJSONObject(i)));
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
    protected ArrayList<ResponseDTO> doInBackground(RequestDTO... request) {
        try {
            return getResponses(request[0]);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
