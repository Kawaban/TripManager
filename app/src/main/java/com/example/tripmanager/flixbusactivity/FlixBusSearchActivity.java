package com.example.tripmanager.flixbusactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.flixbusactivity.domain.FlixBusAPIController;
import com.example.tripmanager.flixbusactivity.domain.RequestDTO;
import com.example.tripmanager.flixbusactivity.domain.ResponseDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FlixBusSearchActivity extends AppCompatActivity {
    private FlixBusAPIController flixBusAPIController;
    private Button searchButton;
    private EditText fromText;
    private EditText toText;
    private EditText numberText;
    private EditText departureDateText;
    private View mainLayout;
    private View loadingLayout;
    @Override
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flixbus_search);
        searchButton = findViewById(R.id.buttonSearch);
        fromText = findViewById(R.id.editTextFrom);
        toText = findViewById(R.id.editTextTo);
        numberText = findViewById(R.id.editTextNumber);
        departureDateText = findViewById(R.id.editTextDateDeparture);

        mainLayout = findViewById(R.id.main_flixbus_layout);
        loadingLayout = findViewById(R.id.loading_layout);

        flixBusAPIController=new FlixBusAPIController(getApplicationContext(), this, mainLayout, loadingLayout);

        searchButton.setOnClickListener(v -> {

            if (fromText.getText().toString().isEmpty() || toText.getText().toString().isEmpty() || numberText.getText().toString().isEmpty() || departureDateText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestDTO requestDTO = new RequestDTO(fromText.getText().toString(), toText.getText().toString(), departureDateText.getText().toString(), Integer.parseInt(numberText.getText().toString()));
            flixBusAPIController.execute(requestDTO);

        });

    }

    public void onRestart() {
        super.onRestart();
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }


}
