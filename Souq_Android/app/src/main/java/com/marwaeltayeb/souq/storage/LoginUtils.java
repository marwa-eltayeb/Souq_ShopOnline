package com.marwaeltayeb.souq.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.marwaeltayeb.souq.model.LoginApiResponse;
import com.marwaeltayeb.souq.model.User;

public class LoginUtils {

    private static final String SHARED_PREF_NAME = "shared_preference";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String IS_ADMIN = "isAdmin";

    private static LoginUtils mInstance;
    private final Context mCtx;

    private LoginUtils(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized LoginUtils getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new LoginUtils(mCtx);
        }
        return mInstance;
    }

    public void saveUserInfo(LoginApiResponse response) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ID, response.getId());
        editor.putString(NAME, response.getName());
        editor.putString(EMAIL, response.getEmail());
        editor.putString(PASSWORD, response.getPassword());
        editor.putString(TOKEN, response.getToken());
        editor.putBoolean(IS_ADMIN, response.isAdmin());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public void saveUserInfo(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(ID, user.getId());
        editor.putString(NAME, user.getName());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(PASSWORD, user.getPassword());
        editor.putBoolean(IS_ADMIN, user.isAdmin());
        editor.apply();
    }

    public User getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(ID, -1),
                sharedPreferences.getString(NAME, null),
                sharedPreferences.getString(EMAIL, null),
                sharedPreferences.getString(PASSWORD, null),
                sharedPreferences.getBoolean(IS_ADMIN, false)
        );
    }


    public String getUserToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, "");
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        editor.apply();
    }

}
