package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.History;
import com.marwaeltayeb.souq.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToHistoryRepository {

    private static final String TAG = ToHistoryRepository.class.getSimpleName();

    public LiveData<ResponseBody> addToHistory(History history) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addToHistory(history).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.code());

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure" + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
