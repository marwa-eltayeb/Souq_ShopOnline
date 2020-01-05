package com.marwaeltayeb.souq.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivitySignupBinding;
import com.marwaeltayeb.souq.model.UserModel;
import com.marwaeltayeb.souq.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding binding;

    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.buttonSignUp.setOnClickListener(this);
        binding.textViewLogin.setOnClickListener(this);


        setBoldStyle();

    }


    private void SignUpUser() {

        String name = binding.userName.getText().toString();
        String email = binding.userEmail.getText().toString();
        String password = binding.userPassword.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            binding.userName.setError(getString(R.string.enter_at_least_3_characters));
            binding.userName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            binding.userEmail.setError(getString(R.string.email_required));
            binding.userEmail.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.userEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.userEmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            binding.userPassword.setError(getString(R.string.password_required));
            binding.userPassword.requestFocus();
        }

        if (password.length() < 8) {
            binding.userPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.userPassword.requestFocus();
        }

        RetrofitClient.getInstance()
                .getUserApi().insertUser(new UserModel(name, email, password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(SignupActivity.this, response.body() + "", Toast.LENGTH_SHORT).show();
                goToMainActivity();
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                SignUpUser();
                break;
            case R.id.textViewLogin:
                goToLoginActivity();
                break;
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void setBoldStyle() {
        String boldText = getString(R.string.boldText);
        String normalText = getString(R.string.normalText);
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewLogin.setText(str);
    }


}
