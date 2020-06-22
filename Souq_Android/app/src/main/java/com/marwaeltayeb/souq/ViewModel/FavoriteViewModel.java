package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.FavoriteApiResponse;
import com.marwaeltayeb.souq.repository.FavoriteRepository;

public class FavoriteViewModel extends AndroidViewModel {

    private FavoriteRepository favoriteRepository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
    }

    public LiveData<FavoriteApiResponse> getFavorites(int userId) {
        return favoriteRepository.getFavorites(userId);
    }

}
