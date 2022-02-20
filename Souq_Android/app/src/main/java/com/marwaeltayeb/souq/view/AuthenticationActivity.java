package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityAuthenticationBinding;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.FlagsManager;
import com.marwaeltayeb.souq.viewmodel.OtpViewModel;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthenticationActivity";
    private ActivityAuthenticationBinding binding;
    private OtpViewModel otpViewModel;
    private String email;
    private String correctOtpCode;
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);

        binding.proceed.setOnClickListener(this);
        binding.reSend.setOnClickListener(this);

        Intent intent = getIntent();
        email = intent.getStringExtra(EMAIL);
        correctOtpCode = intent.getStringExtra(OTP);
        String formatted = getString(R.string.description2, email);

        TextView authentication = findViewById(R.id.authentication);
        authentication.setText(formatted);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkOtpCode();
        } else if (view.getId() == R.id.reSend) {
            clickCount = clickCount + 1;
            getAnotherOtpCode();
            if (clickCount >= 3) {
                binding.reSend.setClickable(false);
                binding.numberOfClicks.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getAnotherOtpCode() {
        otpViewModel.getOtpCode(LoginUtils.getInstance(this).getUserToken(), email).observe(this, responseBody -> {
            if (!responseBody.isError()) {
                correctOtpCode = responseBody.getOptCode();
                binding.reSend.setEnabled(false);
                binding.countDownTimer.setVisibility(View.VISIBLE);
                countDownTimer(binding.countDownTimer);
            }
        });
    }

    private void checkOtpCode() {
        String otpEntered = binding.otpCode.getText().toString();

        if (!otpEntered.equals(correctOtpCode)) {
            binding.otpCode.setError(getString(R.string.incorrect_code));
            binding.otpCode.requestFocus();
        } else {
            Intent passwordIntent = new Intent(this, PasswordActivity.class);
            startActivity(passwordIntent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FlagsManager.getInstance().setActivityRunning(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        FlagsManager.getInstance().setActivityRunning(false);
    }

    private void countDownTimer(TextView textView) {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Log.d(TAG, "onFinish: " + "done!");
                binding.reSend.setEnabled(true);
                binding.countDownTimer.setVisibility(View.INVISIBLE);
            }

        }.start();
    }
}
