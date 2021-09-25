package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.repository.RemoveFavoriteRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class RemoveFavoriteViewModel extends ViewModel {

    private final RemoveFavoriteRepository removeFavoriteRepository;

    public RemoveFavoriteViewModel() {
        removeFavoriteRepository = new RemoveFavoriteRepository();
    }

    public LiveData<ResponseBody> removeFavorite(int userId, int productId, RequestCallback callback) {
        return removeFavoriteRepository.removeFavorite(userId, productId, callback);
    }
}
