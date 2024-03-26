package com.example.tripmanager.locationactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.locationactivity.domain.ResponseDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class LocationResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<ResponseDTO> result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_result);
        // Get a handle to the fragment and register the callback.
        result = getIntent().getExtras().getParcelableArrayList("responses");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        for(ResponseDTO responseDTO : result){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(responseDTO.getLatitude()), Double.parseDouble(responseDTO.getLongitude())))
                    .title(responseDTO.getName()));

        }

    }
}
