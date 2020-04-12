package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.marwaeltayeb.souq.R;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Intent intent = getIntent();
        String email = intent.getStringExtra(EMAIL);
        String correctOtpCode = intent.getStringExtra(OTP);
        String formatted = getString(R.string.description2, email);

        TextView authentication = findViewById(R.id.authentication);
        authentication.setText(formatted);

    }


}
