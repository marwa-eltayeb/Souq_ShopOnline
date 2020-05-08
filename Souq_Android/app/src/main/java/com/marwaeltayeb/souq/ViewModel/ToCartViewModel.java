package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.repository.ToCartRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends AndroidViewModel {

    private ToCartRepository toCartRepository;

    public ToCartViewModel(@NonNull Application application) {
        super(application);
        toCartRepository = new ToCartRepository(application);
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
