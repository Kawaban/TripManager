package com.example.tripmanager.flixbusactivity;

import android.os.AsyncTask;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlixBusAPIController extends AsyncTask<String, Void, String> {
    private final OkHttpClient client = new OkHttpClient();

    public String getResponse() throws IOException {

        Request request = new Request.Builder()
                .url("https://omgvamp-hearthstone-v1.p.rapidapi.com/info")
                .get()
                .addHeader("X-RapidAPI-Key", "1120050b5emshec695034c8d3f0cp15fd46jsn60f4b164ea9c")
                .addHeader("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            return getResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
