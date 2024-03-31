package com.example.tripmanager.statisticsactivity;

import android.annotation.SuppressLint;
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
      private Button deleteTripButton;
      private TableView tableView;
      private int type;
      @SuppressLint("MissingInflatedId")
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);
        type = R.integer.TYPE_SHOW;
        statisticsButton = findViewById(R.id.buttonAddTrip);
        deleteTripButton = findViewById(R.id.buttonDeleteTrip);
        tableView = findViewById(R.id.tableView);
        statisticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatisticsAddActivity.class);
            startActivity(intent);
        });

        deleteTripButton.setOnClickListener(v -> {
            if (type == R.integer.TYPE_SHOW) {
                type = R.integer.TYPE_DELETE;
                tableView.setDataAdapter(new TripTableDataAdapter(this, AppDatabase.getInstance(this).tripDao().getAll(), type, tableView));
            }
            else {
                type = R.integer.TYPE_SHOW;
                tableView.setDataAdapter(new TripTableDataAdapter(this, AppDatabase.getInstance(this).tripDao().getAll(), type, tableView));
            }
        });
        tableView.setColumnCount(5);
        tableView.setHeaderAdapter(new TripTableHeaderAdapter(this));
        tableView.setDataAdapter(new TripTableDataAdapter(this, AppDatabase.getInstance(this).tripDao().getAll(),type,tableView));

      }

      public void onRestart() {
        super.onRestart();
        tableView.setDataAdapter(new TripTableDataAdapter(this, AppDatabase.getInstance(this).tripDao().getAll(),type,tableView));
      }




}
