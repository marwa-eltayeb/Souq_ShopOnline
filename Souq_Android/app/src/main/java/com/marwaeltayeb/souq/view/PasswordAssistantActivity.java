package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityPasswordAssistantBinding;
import com.marwaeltayeb.souq.model.Otp;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.souq.utils.Constant.EMAIL;
import static com.marwaeltayeb.souq.utils.Constant.OTP;

public class PasswordAssistantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PasswordAssistantActivi";
    private ActivityPasswordAssistantBinding binding;
    private String userEmail;
    private String otpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_assistant);

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

        RetrofitClient.getInstance().getApi().getOtp(emailEntered).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {
                if (response.body() != null) {
                    userEmail = response.body().getEmail();
                    otpCode = response.body().getOtp();
                    goToAuthenticationActivity();
                } else {
                    binding.emailAddress.setError(getString(R.string.enter_your_email));
                }
            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void goToAuthenticationActivity(){
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra(EMAIL, userEmail);
        intent.putExtra(OTP, otpCode);
        startActivity(intent);
    }
}
