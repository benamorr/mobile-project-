package com.ironfit.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.ironfit.app.R;
import com.ironfit.app.database.DatabaseHelper;
import com.ironfit.app.utils.PreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        android.view.View ivLogo = findViewById(R.id.iv_logo);
        if (ivLogo != null) {
            ivLogo.setScaleX(0.7f);
            ivLogo.setScaleY(0.7f);
            ivLogo.setAlpha(0f);
            ivLogo.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(900)
                    .setInterpolator(new android.view.animation.OvershootInterpolator(2.0f))
                    .start();
        }

        // Pre-initialize database in background
        new Thread(() -> {
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            helper.getWritableDatabase(); // triggers onCreate and populateSampleData if needed
        }).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            PreferencesManager prefs = new PreferencesManager(SplashActivity.this);
            Intent intent;
            if (prefs.isOnboardingCompleted()) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1800);
    }
}
