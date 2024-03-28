package com.example.tripmanager.statisticsactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.AppDatabase;
import com.example.tripmanager.infrastructure.database.TripEntity;

import java.util.ArrayList;

public class StatisticsAddActivity extends AppCompatActivity {

    private Button addImagesButton;
    private Button saveButton;
    private EditText locationEditText;
    private EditText expensesEditText;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private RatingBar ratingBar;


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
                    endDate.isEmpty() || ratingBar.getRating() == 0 || images.isEmpty()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // create a new trip entity
            TripEntity tripEntity = new TripEntity();
            tripEntity.location = location;
            tripEntity.expenses = expenses;
            tripEntity.startDate = startDate;
            tripEntity.endDate = endDate;
            tripEntity.images = images;
            tripEntity.rating = ratingBar.getRating();
            AppDatabase.getInstance(this).tripDao().insert(tripEntity);
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
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
