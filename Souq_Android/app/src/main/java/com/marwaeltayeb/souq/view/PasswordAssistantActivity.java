package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.OtpViewModel;
import com.marwaeltayeb.souq.databinding.ActivityPasswordAssistantBinding;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PasswordAssistantActivi";
    private ActivityPasswordAssistantBinding binding;
    private OtpViewModel otpViewModel;
    private String userEmail;
    private String otpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_assistant);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);

        binding.proceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            checkUserEmail();
        }
    }

    private void checkUserEmail() {
        String emailEntered = binding.emailAddress.getText().toString();

        otpViewModel.getOtpCode(emailEntered).observe(this, responseBody -> {
            if (!responseBody.isError()) {
                userEmail = responseBody.getEmail();
                otpCode = responseBody.getOtp();
                goToAuthenticationActivity();
            } else {
                binding.emailAddress.setError(responseBody.getMessage());
            }
        });
    }

    private void goToAuthenticationActivity() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EMAIL, userEmail);
        intent.putExtra(OTP, otpCode);
        startActivity(intent);
    }
}
