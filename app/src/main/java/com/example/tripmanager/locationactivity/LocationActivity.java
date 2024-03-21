package com.example.tripmanager.locationactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
    private LocationAPIController locationAPIController;
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        planButton = findViewById(R.id.buttonPlan);
        cityText = findViewById(R.id.editTextCity);
        attractionsCheckBox = findViewById(R.id.checkBoxAttractions);
        restaurantsCheckBox = findViewById(R.id.checkBoxRestaraunts);

        locationAPIController = new LocationAPIController();

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


                RequestDTO requestDTO = new RequestDTO(city, attractions, restaurants);
                try {
                    ArrayList<ResponseDTO> responseDTOS= locationAPIController.execute(requestDTO).get();
                    Intent intent = new Intent(this, LocationResultActivity.class);
                    intent.putParcelableArrayListExtra("responses", responseDTOS);
                    startActivity(intent);
                } catch (ExecutionException | InterruptedException  e) {
                    throw new RuntimeException(e);
                }


        });
    }

}
