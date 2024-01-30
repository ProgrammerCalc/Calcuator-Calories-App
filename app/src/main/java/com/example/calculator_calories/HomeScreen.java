package com.example.calculator_calories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    Button btonCalc;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText ageEditText;
    private Spinner genderSpinner;
    private Spinner activityLevelSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        weightEditText = findViewById(R.id.txtWeight);
        heightEditText = findViewById(R.id.txtHeight);
        ageEditText = findViewById(R.id.txtAge);
        genderSpinner = findViewById(R.id.genderSpinner);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);



        btonCalc=findViewById(R.id.buttonCalc);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this, R.array.gender_options, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_level_array, android.R.layout.simple_spinner_item);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityLevelAdapter);









        btonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate_Calories();

            }
        });
    }












    private boolean validateInput() {
        if (weightEditText.getText().toString().isEmpty()) {
            weightEditText.setError("Please enter your weight.");
            return false;
        }

        if (heightEditText.getText().toString().isEmpty()) {
            heightEditText.setError("Please enter your height.");
            return false;
        }

        if (ageEditText.getText().toString().isEmpty()) {
            ageEditText.setError("Please enter your age.");
            return false;
        }

        return true;
    }

    private void calculate_Calories() {

        if(!validateInput())
        {
            return ;
        }
        try {

            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            int age = Integer.parseInt(ageEditText.getText().toString());
            String gender = genderSpinner.getSelectedItem().toString();
            String activityLevel = activityLevelSpinner.getSelectedItem().toString();

            double bmr = calculateBMRMifflinStJeor(weight, height, age, gender);
            double calories = calculateCalories(bmr, getActivityMultiplier(activityLevel));

            Intent intent=new Intent(HomeScreen.this,Result_Screen.class);
            intent.putExtra("result",calories);
            startActivity(intent);

        } catch (NumberFormatException e) {

        }
    }

    private double calculateBMRMifflinStJeor(double weight, double height, int age, String gender) {
        if (gender.equalsIgnoreCase("ذكر")) {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else if (gender.equalsIgnoreCase("أنثى")) {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        } else {
            throw new IllegalArgumentException("Invalid gender. Please enter Male or Female.");
        }
    }

    private double getActivityMultiplier(String activityLevel) {
        switch (activityLevel.toLowerCase()) {
            case "خامل او كثير الجلوس":
                return 1.2;
            case "نشاط خفيف 1-3 مرات في الأسبوع":
                return 1.375;
            case "نشاط متوسط الشدة 3-5 مرات في الأسبوع":
                return 1.55;
            case "نشاط عالي جداً 6-7 مرات في الأسبوع":
                return 1.725;
            default:
                return 1.2;
        }
    }
    public  double calculateCalories(double bmr, double activityMultiplier) {

        return bmr * activityMultiplier;
    }
}