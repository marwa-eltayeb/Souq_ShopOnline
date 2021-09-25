package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.FavoriteApiResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRepository {

    private static final String TAG = FavoriteRepository.class.getSimpleName();

    public LiveData<FavoriteApiResponse> getFavorites(int userId) {
        final MutableLiveData<FavoriteApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getFavorites(userId).enqueue(new Callback<FavoriteApiResponse>() {
            @Override
            public void onResponse(Call<FavoriteApiResponse> call, Response<FavoriteApiResponse> response) {
                Log.d(TAG, "onResponse: Succeeded");

                FavoriteApiResponse favoriteApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(favoriteApiResponse);
                    Log.d(TAG, String.valueOf(response.body().getFavorites()));
                }
            }

            @Override
            public void onFailure(Call<FavoriteApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
