package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.Otp;
import com.marwaeltayeb.souq.repository.OtpRepository;

public class OtpViewModel extends AndroidViewModel {

    private OtpRepository otpRepository;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        otpRepository = new OtpRepository(application);
    }

    public LiveData<Otp> getOtpCode(String email) {
        return otpRepository.getOtpCode(email);
    }
}
