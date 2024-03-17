package com.example.tripmanager.flixbusactivity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.flixbusactivity.domain.ResponseDTO;
import com.example.tripmanager.flixbusactivity.textview.ResponceTableDataAdapter;
import com.example.tripmanager.flixbusactivity.textview.ResponceTableHeaderAdapter;


import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class FlixBusResultsActivity extends AppCompatActivity{
    private TableView<ResponseDTO> tableView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flixbus_results);
        tableView = findViewById(R.id.tableView);
        tableView.setColumnCount(5);
        ArrayList<ResponseDTO> result = savedInstanceState.getParcelableArrayList("responses");
        tableView.setHeaderAdapter(new ResponceTableHeaderAdapter(this));
        tableView.setDataAdapter(new ResponceTableDataAdapter(this, result));
    }

}
