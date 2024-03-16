package com.example.tripmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.flixbusactivity.FlixBusSearchActivity;

public class MainActivity extends AppCompatActivity {
    private Button flixBusButton;
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flixBusButton = findViewById(R.id.flixbusbutton);
        flixBusButton.setOnClickListener(v -> startActivity(new Intent(this, FlixBusSearchActivity.class)));
    }
}
