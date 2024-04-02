package com.example.tripmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tripmanager.aiadvisoractivity.AIAdvisorSelectActivity;
import com.example.tripmanager.flixbusactivity.FlixBusSearchActivity;
import com.example.tripmanager.locationactivity.LocationActivity;
import com.example.tripmanager.statisticsactivity.StatisticsMenuActivity;

public class MainActivity extends AppCompatActivity {
    private Button flixBusButton;
    private Button aiAdvisorButton;
    private Button statisticsButton;
    private Button locationButton;

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flixBusButton = findViewById(R.id.flixbusbutton);
        flixBusButton.setOnClickListener(v -> startActivity(new Intent(this, FlixBusSearchActivity.class)));

        aiAdvisorButton = findViewById(R.id.aiadvisorbutton);
        aiAdvisorButton.setOnClickListener(v -> startActivity(new Intent(this, AIAdvisorSelectActivity.class)));

        statisticsButton = findViewById(R.id.statisticsbutton);
        statisticsButton.setOnClickListener(v -> startActivity(new Intent(this, StatisticsMenuActivity.class)));

        locationButton = findViewById(R.id.locationbutton);
        locationButton.setOnClickListener(v -> startActivity(new Intent(this, LocationActivity.class)));

    }


}
