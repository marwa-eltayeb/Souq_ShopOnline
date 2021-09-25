package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.storage.LoginUtils;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, ProductActivity.class);
            startActivity(i);

            // Close this activity
            finish();
            // If user does not log in before, go to LoginActivity
            if(!LoginUtils.getInstance(SplashActivity.this).isLoggedIn()) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        }, SPLASH_TIME_OUT);
    }
}
