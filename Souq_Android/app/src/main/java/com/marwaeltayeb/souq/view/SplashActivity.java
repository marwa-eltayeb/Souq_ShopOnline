package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.storage.LoginUtils;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, ProductActivity.class);
                startActivity(i);

                // Close this activity
                finish();
                // If user does not log in before, go to LoginActivity
                if(!LoginUtils.getInstance(SplashActivity.this).isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
