package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.repository.AddFavoriteRepository;
import com.marwaeltayeb.souq.utils.RequestCallback;

import okhttp3.ResponseBody;

public class AddFavoriteViewModel extends AndroidViewModel {

    private AddFavoriteRepository addFavoriteRepository;

    public AddFavoriteViewModel(@NonNull Application application) {
        super(application);
        addFavoriteRepository = new AddFavoriteRepository(application);
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        return addFavoriteRepository.addFavorite(favorite,callback);
    }
}
