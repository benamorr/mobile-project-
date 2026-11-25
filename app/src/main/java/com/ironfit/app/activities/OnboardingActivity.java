package com.ironfit.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.ironfit.app.R;
import com.ironfit.app.database.UserDao;
import com.ironfit.app.database.WeightDao;
import com.ironfit.app.models.User;
import com.ironfit.app.models.WeightEntry;
import com.ironfit.app.utils.AnimationUtilsHelper;
import com.ironfit.app.utils.PreferencesManager;

public class OnboardingActivity extends AppCompatActivity {

    private TextInputEditText etName, etAge, etHeight, etWeight;
    private RadioGroup rgExperience, rgGoal;
    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        rgExperience = findViewById(R.id.rg_experience);
        rgGoal = findViewById(R.id.rg_goal);
        btnGetStarted = findViewById(R.id.btn_get_started);

        // Pre-select defaults
        rgExperience.check(R.id.rb_intermediate);
        rgGoal.check(R.id.rb_muscle_gain);
    }

    private void setupListeners() {
        AnimationUtilsHelper.setupTouchBounce(btnGetStarted);
        btnGetStarted.setOnClickListener(v -> submitProfile());
    }

    private void submitProfile() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String ageStr = etAge.getText() != null ? etAge.getText().toString().trim() : "";
        String heightStr = etHeight.getText() != null ? etHeight.getText().toString().trim() : "";
        String weightStr = etWeight.getText() != null ? etWeight.getText().toString().trim() : "";

        if (name.isEmpty()) {
            etName.setError("Please enter your name");
            etName.requestFocus();
            return;
        }

        if (ageStr.isEmpty()) {
            etAge.setError("Please enter your age");
            etAge.requestFocus();
            return;
        }

        if (heightStr.isEmpty()) {
            etHeight.setError("Please enter height");
            etHeight.requestFocus();
            return;
        }

        if (weightStr.isEmpty()) {
            etWeight.setError("Please enter weight");
            etWeight.requestFocus();
            return;
        }

        int age;
        float height, weight;
        try {
            age = Integer.parseInt(ageStr);
            height = Float.parseFloat(heightStr);
            weight = Float.parseFloat(weightStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
            return;
        }

        String experience = "Intermediate";
        int checkedExp = rgExperience.getCheckedRadioButtonId();
        if (checkedExp == R.id.rb_beginner) experience = "Beginner";
        else if (checkedExp == R.id.rb_advanced) experience = "Advanced";

        String goal = "Muscle Gain";
        int checkedGoal = rgGoal.getCheckedRadioButtonId();
        if (checkedGoal == R.id.rb_fat_loss) goal = "Fat Loss";
        else if (checkedGoal == R.id.rb_strength) goal = "Strength";
        else if (checkedGoal == R.id.rb_general_fitness) goal = "General Fitness";

        // Save User
        User user = new User(name, age, height, weight, experience, goal);
        UserDao userDao = new UserDao(this);
        userDao.saveUser(user);

        // Record Initial Weight
        WeightDao weightDao = new WeightDao(this);
        weightDao.addWeightEntry(new WeightEntry(weight, System.currentTimeMillis(), "Initial onboarding weigh-in"));

        // Save Preferences
        PreferencesManager prefs = new PreferencesManager(this);
        prefs.setOnboardingCompleted(true);

        // Navigate to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
