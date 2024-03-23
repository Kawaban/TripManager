package com.example.tripmanager.statisticsactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;


public class StatisticsMenuActivity extends AppCompatActivity {

      private Button statisticsButton;
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);
        statisticsButton = findViewById(R.id.buttonAddTrip);
        statisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatisticsAddActivity.class);
            startActivity(intent);
        });
      }


}
