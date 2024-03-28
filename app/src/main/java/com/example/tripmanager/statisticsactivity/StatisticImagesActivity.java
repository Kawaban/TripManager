package com.example.tripmanager.statisticsactivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanager.R;
import com.example.tripmanager.infrastructure.database.Converters;
import com.example.tripmanager.statisticsactivity.recycleview.RecyclerAdapter;

import java.util.ArrayList;

public class StatisticImagesActivity extends AppCompatActivity{

    private Button returnButton;
    private RecyclerView recyclerView;
    private ImageView imageView;


        @SuppressLint("MissingInflatedId")
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_statistics_images);
            returnButton = findViewById(R.id.buttonReturn);
            recyclerView = findViewById(R.id.recyclerView);
            Bundle extras = getIntent().getExtras();
            String value = extras.getString("images");
            ArrayList<Uri> formattedImages = Converters.fromStringToList(value);
            RecyclerAdapter adapter = new RecyclerAdapter(formattedImages);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));

            returnButton.setOnClickListener(v -> {
                 finish();
            });


        }

}
