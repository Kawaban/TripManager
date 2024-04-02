package com.example.tripmanager.locationactivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.locationactivity.domain.ResponseDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.AdvancedMarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PinConfig;

import java.util.ArrayList;

public class LocationResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ArrayList<ResponseDTO> result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_holder);
        // Get a handle to the fragment and register the callback.
        result = getIntent().getExtras().getParcelableArrayList("responses");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(this);
        googleMap.setInfoWindowAdapter(customInfoWindowAdapter);

        for(ResponseDTO responseDTO : result){
            if (responseDTO.getType() == R.integer.TYPE_RESTAURANT) {
                AdvancedMarkerOptions advancedMarkerOptions = new AdvancedMarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .position(new LatLng(Double.parseDouble(responseDTO.getLatitude()), Double.parseDouble(responseDTO.getLongitude())))
                        .snippet("Description: " + responseDTO.getInfo() + "\nRating: " + responseDTO.getRating())
                        .title(responseDTO.getName());
                googleMap.addMarker(advancedMarkerOptions);
            }

            else if (responseDTO.getType() == R.integer.TYPE_ATTRACTION){
                AdvancedMarkerOptions advancedMarkerOptions = new AdvancedMarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(new LatLng(Double.parseDouble(responseDTO.getLatitude()), Double.parseDouble(responseDTO.getLongitude())))
                        .snippet("Description: " + responseDTO.getInfo() + "\nRating: " + responseDTO.getRating())
                        .title(responseDTO.getName());
                googleMap.addMarker(advancedMarkerOptions);
            }


        }

    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mWindow;
        private Context mContext;

        public CustomInfoWindowAdapter(Context context) {
            mContext = context;
            mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
        }

        private void renderWindowText(Marker marker, View view) {
            String title = marker.getTitle();
            TextView tvTitle = view.findViewById(R.id.title);

            if (!title.equals("")) {
                tvTitle.setText(title);
            }

            String snippet = marker.getSnippet();
            TextView tvSnippet = view.findViewById(R.id.snippet);

            if (!snippet.equals("")) {
                tvSnippet.setText(snippet);
            }
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            renderWindowText(marker, mWindow);
            return mWindow;
        }
    }
}
