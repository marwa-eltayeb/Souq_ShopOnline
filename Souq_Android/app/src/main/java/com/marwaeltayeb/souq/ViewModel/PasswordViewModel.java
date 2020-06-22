package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.repository.PasswordRepository;

import okhttp3.ResponseBody;

public class PasswordViewModel extends AndroidViewModel {

    private PasswordRepository passwordRepository;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = new PasswordRepository(application);
    }

    public LiveData<ResponseBody> updatePassword(String newPassword, int userId) {
        return passwordRepository.updatePassword(newPassword,userId);
    }

}
