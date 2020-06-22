package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.repository.DeleteUserRepository;

import okhttp3.ResponseBody;

public class DeleteUserViewModel extends AndroidViewModel {

    private DeleteUserRepository deleteUserRepository;

    public DeleteUserViewModel(@NonNull Application application) {
        super(application);
        deleteUserRepository = new DeleteUserRepository(application);
    }

    public LiveData<ResponseBody> deleteUser(int userId) {
        return deleteUserRepository.deleteUser(userId);
    }
}

