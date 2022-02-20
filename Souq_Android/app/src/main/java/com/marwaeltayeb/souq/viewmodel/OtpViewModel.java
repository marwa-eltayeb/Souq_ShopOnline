package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.Otp;
import com.marwaeltayeb.souq.repository.OtpRepository;

public class OtpViewModel extends ViewModel {

    private final OtpRepository otpRepository;

    public OtpViewModel() {
        otpRepository = new OtpRepository();
    }

    public LiveData<Otp> getOtpCode(String token,String email) {
        return otpRepository.getOtpCode(token,email);
    }
}
