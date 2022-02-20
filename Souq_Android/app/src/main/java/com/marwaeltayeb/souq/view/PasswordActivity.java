package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityPasswordBinding;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.FlagsManager;
import com.marwaeltayeb.souq.utils.Validation;
import com.marwaeltayeb.souq.viewmodel.PasswordViewModel;

import java.io.IOException;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{


    private ActivityPasswordBinding binding;
    private PasswordViewModel passwordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.change_password));

        passwordViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);

        binding.saveChanges.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);

        if(FlagsManager.getInstance().isActivityRunning()){
            binding.currentPassword.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveChanges:
                updatePassword();
                break;
            case R.id.cancel:
                finish();
                break;
            default: // Should not get here
        }
    }

    private void updatePassword() {
        int userId = LoginUtils.getInstance(this).getUserInfo().getId();
        String token = LoginUtils.getInstance(this).getUserToken();
        String oldPassword = LoginUtils.getInstance(this).getUserInfo().getPassword();
        String currentPassword = binding.currentPassword.getText().toString();
        String newPassword = binding.newPassword.getText().toString();
        String retypePassword =binding.retypePassword.getText().toString();

        if(!currentPassword.equals(oldPassword)){
            binding.currentPassword.setError(getString(R.string.enter_current_password));
            binding.currentPassword.requestFocus();
            return;
        }

        if (!Validation.isValidPassword(newPassword)) {
            binding.newPassword.setError(getString(R.string.password__at_least_8_characters));
            binding.newPassword.requestFocus();
            return;
        }

        if (!(retypePassword.equals(newPassword))) {
            binding.retypePassword.setError(getString(R.string.password_not_match));
            binding.retypePassword.requestFocus();
            return;
        }

        passwordViewModel.updatePassword(token,newPassword, userId).observe(this, responseBody -> {
            try {
                Toast.makeText(PasswordActivity.this, responseBody.string(), Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
