package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private static final String TAG = SearchRepository.class.getSimpleName();

    public LiveData<ProductApiResponse> getResponseDataBySearch(String keyword, int userId) {
        final MutableLiveData<ProductApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance()
                .getApi().searchForProduct(keyword, userId)
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {
                        Log.d(TAG, "onResponse: Succeeded");

                        ProductApiResponse productApiResponse = response.body();

                        if (response.body() != null) {
                            mutableLiveData.setValue(productApiResponse);
                            Log.d(TAG, String.valueOf(response.body().getProducts()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                        Log.v("onFailure", " Failed to get products");
                    }
                });
        return mutableLiveData;
    }
}
