package com.example.tripmanager.statisticsactivity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;

import java.util.ArrayList;

public class StatisticImagesActivity extends AppCompatActivity{

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_statistics_images);
            ArrayList<Uri> images = getIntent().getParcelableArrayListExtra("images");
        }

}
