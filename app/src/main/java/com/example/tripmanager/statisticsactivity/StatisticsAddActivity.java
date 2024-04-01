package com.example.tripmanager.statisticsactivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.infrastructure.database.TripEntity;
import com.example.tripmanager.infrastructure.util.DateValidator;
import com.example.tripmanager.infrastructure.util.DateValidatorUsingDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class StatisticsAddActivity extends AppCompatActivity {

    private Button addImagesButton;
    private Button saveButton;
    private EditText locationEditText;
    private EditText expensesEditText;
    private static EditText startDateEditText;
    private static EditText endDateEditText;
    private RatingBar ratingBar;

    private Spinner spinner;

    private  ImageButton startDateButton;
    private  ImageButton endDateButton;



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

            if (Objects.equals(getTag(), "startDateEditText")) {
                startDateEditText.setText(dayString + "." + monthString + "." + year);
            } else if (Objects.equals(getTag(), "endDateEditText")) {
                endDateEditText.setText(dayString + "." + monthString + "." + year);
            }



        }
    }








    ArrayList<Uri> images = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_add);



        addImagesButton = findViewById(R.id.buttonAddImages);
        saveButton = findViewById(R.id.buttonSave);
        locationEditText = findViewById(R.id.editTextLocation);
        expensesEditText = findViewById(R.id.editTextNumberExpenses);
        startDateEditText = findViewById(R.id.editTextStartDate);
        endDateEditText = findViewById(R.id.editTextEndDate);
        ratingBar = findViewById(R.id.ratingBar);
        startDateButton = findViewById(R.id.imageButtonStartDate);
        endDateButton = findViewById(R.id.imageButtonEndDate);
        spinner = findViewById(R.id.spinner);

        DateValidator validator = new DateValidatorUsingDateFormat("dd.mm.yyyy");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.currency_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent();

            // setting type to select to be image
            intent.setType("image/*");

            // allowing multiple image to be selected
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), R.integer.PICK_IMAGE_MULTIPLE);

        });

        saveButton.setOnClickListener(v -> {
            // get the data from the edit text
            String location = locationEditText.getText().toString();
            String expenses = expensesEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();

            // check if the data is empty
            if (location.isEmpty() || expenses.isEmpty() || startDate.isEmpty() ||
                    endDate.isEmpty() || images.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // check if the date format is valid
            if (!validator.isValid(startDate) || !validator.isValid(endDate)){
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }

            //check if the start date is before the end date
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(new Date(startDate));
            cal2.setTime(new Date(endDate));
            if (cal1.after(cal2)){
                Toast.makeText(this, "Start date cannot be after end date", Toast.LENGTH_SHORT).show();
                return;
            }



            //check if the total expenses is a valid number
            try {
                Double.parseDouble(expenses);
            } catch (NumberFormatException e){
                Toast.makeText(this, "Invalid expenses", Toast.LENGTH_SHORT).show();
                return;
            }



            // create a new trip entity
            TripEntity tripEntity = new TripEntity();
            tripEntity.location = location;
            tripEntity.expenses = expenses + " " + spinner.getSelectedItem().toString();
            tripEntity.startDate = startDate;
            tripEntity.endDate = endDate;
            tripEntity.images = images;
            tripEntity.rating = ratingBar.getRating();
            AppDatabase.getInstance(this).tripDao().insert(tripEntity);
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();

            finish();
        });

        startDateButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "startDateEditText");
        });

        endDateButton.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "endDateEditText");
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == R.integer.PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                        Uri imageurl = data.getClipData().getItemAt(i).getUri();
                        getContentResolver().takePersistableUriPermission(imageurl,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        images.add(imageurl);
                }
            } else {
                    Uri imageurl = data.getData();
                if (imageurl != null) {
                    getContentResolver().takePersistableUriPermission(imageurl,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    images.add(imageurl);
                }
            }
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }




}
