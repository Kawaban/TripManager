package com.example.tripmanager.locationactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.locationactivity.domain.LocationAPIController;
import com.example.tripmanager.locationactivity.domain.RequestDTO;
import com.example.tripmanager.locationactivity.domain.ResponseDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LocationActivity extends AppCompatActivity {
    private Button planButton;
    private EditText cityText;
    private CheckBox attractionsCheckBox;
    private CheckBox restaurantsCheckBox;
    private ProgressBar progressBar;
    private View loadingLayout;
    private View mainLayout;

    private LocationAPIController locationAPIController;
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        planButton = findViewById(R.id.buttonPlan);
        cityText = findViewById(R.id.editTextCity);
        attractionsCheckBox = findViewById(R.id.checkBoxAttractions);
        restaurantsCheckBox = findViewById(R.id.checkBoxRestaraunts);
        progressBar = findViewById(R.id.progressBar);
        loadingLayout = findViewById(R.id.loading_layout);
        mainLayout = findViewById(R.id.main_location_layout);
        mainLayout.setVisibility(View.VISIBLE);

        loadingLayout.setVisibility(View.GONE);

        locationAPIController = new LocationAPIController(this, mainLayout, loadingLayout);

        planButton.setOnClickListener(v -> {
            String city = cityText.getText().toString();
            boolean attractions = attractionsCheckBox.isChecked();
            boolean restaurants = restaurantsCheckBox.isChecked();

            if (city.isEmpty()) {
                cityText.setError("City is required");
                return;
            }

             if(!attractions && !restaurants){
                attractionsCheckBox.setError("At least one of the checkboxes must be checked");
                restaurantsCheckBox.setError("At least one of the checkboxes must be checked");
                return;
            }
            mainLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);


            RequestDTO requestDTO = new RequestDTO(city, attractions, restaurants);
            locationAPIController.execute(requestDTO);




        });
    }

    public void onRestart() {
        super.onRestart();
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }

}
