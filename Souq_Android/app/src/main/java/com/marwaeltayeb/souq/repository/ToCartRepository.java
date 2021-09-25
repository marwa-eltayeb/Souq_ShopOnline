package com.marwaeltayeb.souq.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToCartRepository {

    private static final String TAG = ToCartRepository.class.getSimpleName();

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addToCart(cart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG , "onResponse" + response.code());

                if(response.code() == 200){
                    callback.onCallBack();
                }

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG,"onFailure"  + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
