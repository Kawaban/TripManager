package com.example.tripmanager.locationactivity.domain;

import com.example.tripmanager.R;

import org.json.JSONObject;

public class ResponseMapper {
    public static ResponseDTO mapJSONAttractionsToResponseDTO(JSONObject jsonObject) {
        return new ResponseDTO(
                jsonObject.optString("latitude"),
                jsonObject.optString("longitude"),
                jsonObject.optString("name"),
                jsonObject.optString("description"),
                jsonObject.optString("rating"),
                R.integer.TYPE_ATTRACTION
        );
    }

    public static ResponseDTO mapJSONRestaurantsToResponseDTO(JSONObject jsonObject) {
        if(jsonObject.optString("latitude").equals("") || jsonObject.optString("longitude").equals(""))
            return null;
        return new ResponseDTO(
                jsonObject.optString("latitude"),
                jsonObject.optString("longitude"),
                jsonObject.optString("name"),
                jsonObject.optString("description"),
                jsonObject.optString("rating"),
                R.integer.TYPE_RESTAURANT
        );
    }
}
