package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityAuthenticationBinding;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAuthenticationBinding binding;
    private String correctOtpCode;
    static boolean isActivityRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        binding.proceed.setOnClickListener(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra(EMAIL);
        correctOtpCode = intent.getStringExtra(OTP);
        String formatted = getString(R.string.description2, email);

        TextView authentication = findViewById(R.id.authentication);
        authentication.setText(formatted);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkOtpCode();
        }
    }

    private void checkOtpCode() {
        String otpEntered = binding.otpCode.getText().toString();

        if(!otpEntered.equals(correctOtpCode)){
            binding.otpCode.setError(getString(R.string.incorrect_code));
            binding.otpCode.requestFocus();
        }else {
            Intent passwordIntent = new Intent(this, PasswordActivity.class);
            startActivity(passwordIntent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActivityRunning = false;
    }
}
