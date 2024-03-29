package com.example.tripmanager.locationactivity;

import android.graphics.Color;
import android.os.Bundle;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PinConfig;

import java.util.ArrayList;

public class LocationResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<ResponseDTO> result;
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


        for(ResponseDTO responseDTO : result){
            if (responseDTO.getType() == R.integer.TYPE_RESTAURANT) {
                AdvancedMarkerOptions advancedMarkerOptions = new AdvancedMarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .position(new LatLng(Double.parseDouble(responseDTO.getLatitude()), Double.parseDouble(responseDTO.getLongitude())))
                        .title(responseDTO.getName());
                googleMap.addMarker(advancedMarkerOptions);
            }

            else if (responseDTO.getType() == R.integer.TYPE_ATTRACTION){
                AdvancedMarkerOptions advancedMarkerOptions = new AdvancedMarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(new LatLng(Double.parseDouble(responseDTO.getLatitude()), Double.parseDouble(responseDTO.getLongitude())))
                        .title(responseDTO.getName());
                googleMap.addMarker(advancedMarkerOptions);
            }


        }

    }
}
