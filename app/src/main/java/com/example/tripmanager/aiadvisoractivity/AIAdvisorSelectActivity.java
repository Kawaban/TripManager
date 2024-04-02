package com.example.tripmanager.aiadvisoractivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.aiadvisoractivity.domain.AIAdvisorAPIController;
import com.example.tripmanager.aiadvisoractivity.domain.RequestDTO;

import java.util.concurrent.ExecutionException;

public class AIAdvisorSelectActivity extends AppCompatActivity {
    private AIAdvisorAPIController aiAdvisorAPIController;
    private Button buttonAdvisor;
    private TextView textViewOutput;
    private EditText editTextCurrentLocation;
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_advisor_select);

        View mainLayout = findViewById(R.id.main_ai_advisor_layout);
        View loadingLayout = findViewById(R.id.loading_layout);
        textViewOutput = findViewById(R.id.textViewOutput);
        editTextCurrentLocation = findViewById(R.id.editTextCurrentLocation);

        aiAdvisorAPIController = new AIAdvisorAPIController(getApplicationContext(), this, mainLayout,loadingLayout, textViewOutput);
        buttonAdvisor = findViewById(R.id.buttonAdvisor);


        buttonAdvisor.setOnClickListener(v -> {
            if(editTextCurrentLocation.getText().toString().isEmpty()){
                textViewOutput.setText("Please enter a location");
                return;
            }
            RequestDTO requestDTO = new RequestDTO(editTextCurrentLocation.getText().toString());
            aiAdvisorAPIController.execute(requestDTO);
        });

    }
}
