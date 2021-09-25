package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.Image;
import com.marwaeltayeb.souq.repository.UserImageRepository;

public class UserImageViewModel extends ViewModel {

    private final UserImageRepository userImageRepository;

    public UserImageViewModel() {
        userImageRepository = new UserImageRepository();
    }

    public LiveData<Image> getUserImage(int userId) {
        return userImageRepository.getUserImage(userId);
    }
}
