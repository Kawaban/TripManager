package com.example.tripmanager.flixbusactivity.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseMapper {
    public static ResponseDTO mapToResponseDTO(JSONObject jsonObject) throws JSONException {
        return new ResponseDTO(
                jsonObject.optString("dep_name"),
                jsonObject.optString("arr_name"),
                jsonObject.optString("dep_offset"),
                jsonObject.optString("arr_offset"),
                jsonObject.getJSONArray("fares").getJSONObject(0).optString("price"),
                jsonObject.optString("duration"),
                jsonObject.optString("deeplink"),
                jsonObject.optInt("changeovers")
        );

    }
}
