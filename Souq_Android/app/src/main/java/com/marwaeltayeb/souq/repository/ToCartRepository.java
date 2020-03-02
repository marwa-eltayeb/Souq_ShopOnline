package com.marwaeltayeb.souq.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToCartRepository {

    private static final String TAG = ToCartRepository.class.getSimpleName();
    private Application application;

    public ToCartRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> addToCart(Cart cart) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addToCart(cart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponse", "" + response.code());

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("onFailure", "" + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
