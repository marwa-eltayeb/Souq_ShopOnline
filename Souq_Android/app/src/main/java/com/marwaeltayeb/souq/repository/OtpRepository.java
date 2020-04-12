package com.marwaeltayeb.souq.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.marwaeltayeb.souq.model.Otp;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {

    private static final String TAG = OtpRepository.class.getSimpleName();
    private Application application;

    public OtpRepository(Application application) {
        this.application = application;
    }

    public LiveData<Otp> getOtpCode(String email) {
        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getOtp(email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

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
            public void onFailure(Call<Otp> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
