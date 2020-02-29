package com.marwaeltayeb.souq.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.marwaeltayeb.souq.model.Image;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImageRepository {

    private static final String TAG = UserImageRepository.class.getSimpleName();
    private Application application;

    public UserImageRepository(Application application) {
        this.application = application;
    }

    public LiveData<Image> getUserImage(int userId) {
        final MutableLiveData<Image> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getUserImage(SharedPrefManager.getInstance(application).getUserInfo().getId()).enqueue(new Callback<Image>() {
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
