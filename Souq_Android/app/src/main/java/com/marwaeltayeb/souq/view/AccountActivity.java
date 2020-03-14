package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.marwaeltayeb.souq.PasswordActivity;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.DeleteUserViewModel;
import com.marwaeltayeb.souq.ViewModel.FromHistoryViewModel;
import com.marwaeltayeb.souq.databinding.ActivityAccountBinding;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.io.IOException;

import static com.marwaeltayeb.souq.utils.CommunicateUtils.rateAppOnGooglePlay;
import static com.marwaeltayeb.souq.utils.CommunicateUtils.shareApp;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AccountActivity";
    private DeleteUserViewModel deleteUserViewModel;
    private FromHistoryViewModel fromHistoryViewModel;
    public static boolean historyIsDeleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        deleteUserViewModel = ViewModelProviders.of(this).get(DeleteUserViewModel.class);
        fromHistoryViewModel = ViewModelProviders.of(this).get(FromHistoryViewModel.class);

        binding.nameOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.emailOfUser.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.myOrders.setOnClickListener(this);
        binding.myWishList.setOnClickListener(this);
        binding.languages.setOnClickListener(this);
        binding.helpCenter.setOnClickListener(this);
        binding.shareWithFriends.setOnClickListener(this);
        binding.rateUs.setOnClickListener(this);
        binding.changePassword.setOnClickListener(this);
        binding.deleteAccount.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signOut) {
            signOut();
            deleteAllProductsInHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        LoginUtils.getInstance(this).clearAll();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteAllProductsInHistory() {
       fromHistoryViewModel.removeAllFromHistory().observe(this, responseBody -> {
           Log.d(TAG,getString(R.string.all_removed));
       });
       historyIsDeleted = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrders:
                // ToDo : orders
                break;
            case R.id.myWishList:
                Intent wishListIntent = new Intent(this, WishListActivity.class);
                startActivity(wishListIntent);
                break;
            case R.id.languages:
                // ToDo : language
                break;
            case R.id.helpCenter:
                Intent helpCenterIntent = new Intent(this, HelpActivity.class);
                startActivity(helpCenterIntent);
                break;
            case R.id.shareWithFriends:
                shareApp(this);
                break;
            case R.id.rateUs:
                rateAppOnGooglePlay(this);
                break;
            case R.id.changePassword:
                Intent passwordIntent = new Intent(this, PasswordActivity.class);
                startActivity(passwordIntent);
                break;
            case R.id.deleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount() {
        deleteUserViewModel.deleteUser(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, responseBody -> {
            if(responseBody!= null){
                LoginUtils.getInstance(getApplicationContext()).clearAll();
                try {
                    Toast.makeText(AccountActivity.this, responseBody.string() + "", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: delete account" + responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}