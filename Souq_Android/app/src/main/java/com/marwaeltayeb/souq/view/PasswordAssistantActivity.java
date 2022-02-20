package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityPasswordAssistantBinding;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.viewmodel.OtpViewModel;

public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

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
        String token = LoginUtils.getInstance(this).getUserToken();

        otpViewModel.getOtpCode(token,emailEntered).observe(this, responseBody -> {
            if (!responseBody.isError()) {
                userEmail = responseBody.getEmail();
                otpCode = responseBody.getOptCode();
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
