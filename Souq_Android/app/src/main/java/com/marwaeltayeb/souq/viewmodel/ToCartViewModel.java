package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.repository.ToCartRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends ViewModel {

    private final ToCartRepository toCartRepository;

    public ToCartViewModel() {
        toCartRepository = new ToCartRepository();
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
