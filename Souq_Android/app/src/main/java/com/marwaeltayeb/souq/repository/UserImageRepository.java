package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.Image;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImageRepository {

    private static final String TAG = UserImageRepository.class.getSimpleName();

    public LiveData<Image> getUserImage(int userId) {
        final MutableLiveData<Image> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getUserImage(userId).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d("onResponse", "" + response.code());

                Image responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
        return mutableLiveData;
    }


}
