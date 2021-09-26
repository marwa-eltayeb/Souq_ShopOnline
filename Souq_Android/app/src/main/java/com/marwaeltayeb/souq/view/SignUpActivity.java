package com.marwaeltayeb.souq.view;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.ProgressDialog.createAlertDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivitySignupBinding;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.Validation;
import com.marwaeltayeb.souq.viewmodel.RegisterViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding binding;
    private RegisterViewModel registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        binding.buttonSignUp.setOnClickListener(this);
        binding.textViewLogin.setOnClickListener(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        setBoldStyle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (LoginUtils.getInstance(this).isLoggedIn()) {
            goToProductActivity();
        }
    }

    private void signUpUser() {
        String name = binding.userName.getText().toString();
        String email = binding.userEmail.getText().toString();
        String password = binding.userPassword.getText().toString();

        if (name.isEmpty()) {
            binding.userName.setError(getString(R.string.name_required));
            binding.userName.requestFocus();
            return;
        }

        if (!Validation.isValidName(name)) {
            binding.userName.setError(getString(R.string.enter_at_least_3_characters));
            binding.userName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            binding.userEmail.setError(getString(R.string.email_required));
            binding.userEmail.requestFocus();
        }

        if (Validation.isValidEmail(email)) {
            binding.userEmail.setError(getString(R.string.enter_a_valid_email_address));
            binding.userEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.userPassword.setError(getString(R.string.password_required));
            binding.userPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(password)) {
            binding.userPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.userPassword.requestFocus();
            return;
        }

        AlertDialog alert = createAlertDialog(this);

        registerViewModel.getRegisterResponseLiveData(new User(name, email, password)).observe(this, registerApiResponse -> {
            if (!registerApiResponse.isError()) {
                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_LONG).show();
                LoginUtils.getInstance(this).saveUserInfo(registerApiResponse.getUser());
                alert.dismiss();
                goToProductActivity();
            }else {
                alert.dismiss();
                Toast.makeText(this, registerApiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignUp:
                signUpUser();
                break;
            case R.id.textViewLogin:
                goToLoginActivity();
                break;
            default: // Should not get here
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToProductActivity() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setBoldStyle() {
        String boldText = getString(R.string.boldText);
        String normalText = getString(R.string.normalText);
        SpannableString str = new SpannableString(boldText + normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textViewLogin.setText(str);
    }
}
