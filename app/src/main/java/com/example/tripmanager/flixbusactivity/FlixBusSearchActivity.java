package com.example.tripmanager.flixbusactivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.tripmanager.R;
import com.example.tripmanager.flixbusactivity.domain.FlixBusAPIController;
import com.example.tripmanager.flixbusactivity.domain.RequestDTO;
import com.example.tripmanager.flixbusactivity.domain.ResponseDTO;
import com.example.tripmanager.infrastructure.util.DateValidator;
import com.example.tripmanager.infrastructure.util.DateValidatorUsingDateFormat;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FlixBusSearchActivity extends AppCompatActivity {
    private FlixBusAPIController flixBusAPIController;
    private Button searchButton;
    private EditText fromText;
    private EditText toText;
    private EditText numberText;
    private static EditText departureDateText;
    private View mainLayout;
    private View loadingLayout;
    private ImageButton dateButton;


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker.
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it.
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String monthString = Integer.toString(month + 1);
            if (month < 10) {
                monthString = "0" + monthString;
            }

            String dayString = Integer.toString(day);
            if (day < 10) {
                dayString = "0" + dayString;
            }

            departureDateText.setText(dayString + "." + monthString + "." + year);

        }
    }


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
        dateButton = findViewById(R.id.imageButtonDate);

        DateValidator validator = new DateValidatorUsingDateFormat("dd.mm.yyyy");

        mainLayout = findViewById(R.id.main_flixbus_layout);
        loadingLayout = findViewById(R.id.loading_layout);

        flixBusAPIController = new FlixBusAPIController(getApplicationContext(), this, mainLayout, loadingLayout);

        searchButton.setOnClickListener(v -> {

            if (fromText.getText().toString().isEmpty() || toText.getText().toString().isEmpty() || numberText.getText().toString().isEmpty() || departureDateText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            //check date format
            if (!validator.isValid(departureDateText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            //check if the number of passengers is valid
            if (Integer.parseInt(numberText.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "Invalid number of passengers", Toast.LENGTH_SHORT).show();
                return;
            }

            //check if the departure and destination cities are the same
            if (fromText.getText().toString().equals(toText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Departure and destination cities cannot be the same", Toast.LENGTH_SHORT).show();
                return;
            }

            //check if the departure date is in the past
            String[] date = departureDateText.getText().toString().split("\\.");
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            if (Integer.parseInt(date[2]) < year || (Integer.parseInt(date[2]) == year && Integer.parseInt(date[1]) < month + 1) || (Integer.parseInt(date[2]) == year && Integer.parseInt(date[1]) == month + 1 && Integer.parseInt(date[0]) < day)) {
                Toast.makeText(getApplicationContext(), "Departure date cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }


            mainLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);

            RequestDTO requestDTO = new RequestDTO(fromText.getText().toString(), toText.getText().toString(), departureDateText.getText().toString(), Integer.parseInt(numberText.getText().toString()));
            flixBusAPIController.execute(requestDTO);

        });

        dateButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });

    }

    public void onRestart() {
        super.onRestart();
        mainLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }


}
