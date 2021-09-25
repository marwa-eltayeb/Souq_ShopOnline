package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.ReviewApiResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {

    private static final String TAG = ReviewRepository.class.getSimpleName();
    
    public LiveData<ReviewApiResponse> getReviews(int productId) {
        final MutableLiveData<ReviewApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAllReviews(productId).enqueue(new Callback<ReviewApiResponse>() {
            @Override
            public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                Log.d("onResponse",response.code() + "");

                ReviewApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}

