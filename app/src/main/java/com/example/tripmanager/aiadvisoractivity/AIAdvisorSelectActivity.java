package com.example.tripmanager.aiadvisoractivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripmanager.R;
import com.example.tripmanager.aiadvisoractivity.domain.AIAdvisorAPIController;

import java.util.concurrent.ExecutionException;

public class AIAdvisorSelectActivity extends AppCompatActivity {
    private AIAdvisorAPIController aiAdvisorAPIController;
    private Button buttonAdvisor;
    private TextView textViewOutput;
    private String output;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_advisor_select);

        aiAdvisorAPIController = new AIAdvisorAPIController(getApplicationContext());
        buttonAdvisor = findViewById(R.id.buttonAdvisor);
        textViewOutput = findViewById(R.id.textViewOutput);
        buttonAdvisor.setOnClickListener(v -> {
            try {
                output = aiAdvisorAPIController.execute("").get();
                System.out.println(output);
                textViewOutput.setText(output);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
