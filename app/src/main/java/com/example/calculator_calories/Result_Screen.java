package com.example.calculator_calories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result_Screen extends AppCompatActivity {

    Button btn;
    TextView txtRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        txtRes=findViewById(R.id.txtRes);
        btn=findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        double calories = intent.getDoubleExtra("result", 0.0);
        String res=""+calories;
        txtRes.setText(res);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}