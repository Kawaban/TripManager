package com.example.tripmanager.flixbusactivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class FlixBusSearchActivity extends AppCompatActivity {
    private final FlixBusAPIController flixBusAPIController=new FlixBusAPIController();
    private Button searchButton;
    private EditText fromText;
    private EditText toText;
    private EditText numberText;
    private EditText departureDateText;
    private EditText returnDateText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flixbus_search);
        searchButton = findViewById(R.id.buttonSearch);
        fromText = findViewById(R.id.editTextFrom);
        toText = findViewById(R.id.editTextTo);
        numberText = findViewById(R.id.editTextNumber);
        departureDateText = findViewById(R.id.editTextDateDeparture);
        returnDateText = findViewById(R.id.editTextDateReturn);
        searchButton.setOnClickListener(v -> {
            flixBusAPIController.execute(fromText.getText().toString(), toText.getText().toString(), numberText.getText().toString(), departureDateText.getText().toString(), returnDateText.getText().toString());
        });

    }


}
