package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.RegisterApiResponse;
import com.marwaeltayeb.souq.model.User;
import com.marwaeltayeb.souq.repository.RegisterRepository;

public class RegisterViewModel extends ViewModel {

    private final RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository = new RegisterRepository();
    }

    public LiveData<RegisterApiResponse> getRegisterResponseLiveData(User user) {
        return registerRepository.getRegisterResponseData(user);
    }
}
