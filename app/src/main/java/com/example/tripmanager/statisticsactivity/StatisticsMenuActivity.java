package com.example.tripmanager.statisticsactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.statisticsactivity.tableview.TripTableDataAdapter;
import com.example.tripmanager.statisticsactivity.tableview.TripTableHeaderAdapter;

import de.codecrafters.tableview.TableView;


public class StatisticsMenuActivity extends AppCompatActivity {

      private Button statisticsButton;
      private TableView tableView;
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);
        statisticsButton = findViewById(R.id.buttonAddTrip);
        tableView = findViewById(R.id.tableView);
        statisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatisticsAddActivity.class);
            startActivity(intent);
        });
        tableView.setColumnCount(5);
        tableView.setHeaderAdapter(new TripTableHeaderAdapter(this));
        tableView.setDataAdapter(new TripTableDataAdapter(this, AppDatabase.getInstance(this).tripDao().getAll()));

      }


}
