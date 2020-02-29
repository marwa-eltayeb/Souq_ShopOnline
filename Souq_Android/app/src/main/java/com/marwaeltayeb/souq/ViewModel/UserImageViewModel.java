package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.model.Image;
import com.marwaeltayeb.souq.repository.UserImageRepository;

public class UserImageViewModel extends AndroidViewModel {

    private UserImageRepository userImageRepository;

    public UserImageViewModel(@NonNull Application application) {
        super(application);
        userImageRepository = new UserImageRepository(application);
    }

    public LiveData<Image> getUserImage(int userId) {
        return userImageRepository.getUserImage(userId);
    }
}
