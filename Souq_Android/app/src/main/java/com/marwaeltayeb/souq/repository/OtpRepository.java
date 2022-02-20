package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.Otp;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {

    private static final String TAG = OtpRepository.class.getSimpleName();

    public LiveData<Otp> getOtpCode(String token , String email) {
        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getOtp(token,email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(@NonNull Call<Otp> call, @NonNull Response<Otp> response) {

                Log.d(TAG, "onResponse: Succeeded");

                Otp otp;
                if (response.code() == 200) {
                    otp = response.body();
                } else {
                    otp = new Otp("Incorrect Email");
                }
                mutableLiveData.setValue(otp);

            }

            @Override
            public void onFailure(@NonNull Call<Otp> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
