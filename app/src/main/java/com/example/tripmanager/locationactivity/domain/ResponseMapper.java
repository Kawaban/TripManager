package com.example.tripmanager.locationactivity.domain;

import org.json.JSONObject;

public class ResponseMapper {
    public static ResponseDTO mapJSONAttractionsToResponseDTO(JSONObject jsonObject) {
        return new ResponseDTO(
                jsonObject.optString("latitude"),
                jsonObject.optString("longitude"),
                jsonObject.optString("name"),
                jsonObject.optString("description"),
                jsonObject.optString("rating"),
                "attraction"
        );
    }

    public static ResponseDTO mapJSONRestaurantsToResponseDTO(JSONObject jsonObject) {
        return new ResponseDTO(
                jsonObject.optString("latitude"),
                jsonObject.optString("longitude"),
                jsonObject.optString("name"),
                jsonObject.optString("description"),
                jsonObject.optString("rating"),
                "restaurant"
        );
    }
}
