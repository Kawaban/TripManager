package com.example.tripmanager.flixbusactivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.evrencoskun.tableview.TableView;
import com.example.tripmanager.R;

public class FlixBusResultsActivity extends AppCompatActivity{
    private TableView tableView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flixbus_results);
        tableView = findViewById(R.id.tableView);
    }

}
