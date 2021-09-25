package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.repository.AddFavoriteRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class AddFavoriteViewModel extends ViewModel {

    private final AddFavoriteRepository addFavoriteRepository;

    public AddFavoriteViewModel() {
        addFavoriteRepository = new AddFavoriteRepository();
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        return addFavoriteRepository.addFavorite(favorite,callback);
    }
}
