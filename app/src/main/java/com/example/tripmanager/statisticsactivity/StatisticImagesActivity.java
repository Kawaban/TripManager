package com.example.tripmanager.statisticsactivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripmanager.R;
import com.example.tripmanager.statisticsactivity.recycleview.RecyclerAdapter;

import java.util.ArrayList;

public class StatisticImagesActivity extends AppCompatActivity{

    private Button returnButton;
    private RecyclerView recyclerView;

        @SuppressLint("MissingInflatedId")
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_statistics_images);
            returnButton = findViewById(R.id.buttonReturn);
            recyclerView = findViewById(R.id.recyclerView);

            ArrayList<Uri> images = getIntent().getParcelableArrayListExtra("images");

            RecyclerAdapter adapter = new RecyclerAdapter(images);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));

            returnButton.setOnClickListener(v -> {
                 finish();
            });


        }

}
