package com.marwaeltayeb.souq.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.marwaeltayeb.souq.model.LoginApiResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getSimpleName();
    private Application application;

    public LoginRepository(Application application) {
        this.application = application;
    }

    public LiveData<LoginApiResponse> getLoginResponseData(final Context context, String email, String password) {
        final MutableLiveData<LoginApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().logInUser(email, password).enqueue(new Callback<LoginApiResponse>() {
            @Override
            public void onResponse(Call<LoginApiResponse> call, Response<LoginApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                LoginApiResponse loginResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(loginResponse);
                }
            }

            @Override
            public void onFailure(Call<LoginApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
