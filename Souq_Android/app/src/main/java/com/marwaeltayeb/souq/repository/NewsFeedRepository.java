package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.NewsFeedResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedRepository {

    private static final String TAG = NewsFeedRepository.class.getSimpleName();

    public LiveData<NewsFeedResponse> getPosters() {
        final MutableLiveData<NewsFeedResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getPosters().enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {

                Log.d("onResponse", "" + response.code());

                NewsFeedResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
