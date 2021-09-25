package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.RegisterApiResponse;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {

    private static final String TAG = RegisterRepository.class.getSimpleName();

    public LiveData<RegisterApiResponse> getRegisterResponseData(User user) {
        final MutableLiveData<RegisterApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().createUser(user).enqueue(new Callback<RegisterApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterApiResponse> call, Response<RegisterApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                RegisterApiResponse registerApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(registerApiResponse);
                    Log.d(TAG, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<RegisterApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
