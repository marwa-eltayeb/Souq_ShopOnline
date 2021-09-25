package com.marwaeltayeb.souq.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.CartApiResponse;
import com.marwaeltayeb.souq.repository.CartRepository;

public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;

    public CartViewModel() {
        cartRepository = new CartRepository();
    }

    public LiveData<CartApiResponse> getProductsInCart(int userId) {
        return cartRepository.getProductsInCart(userId);
    }
}
