package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.History;
import com.marwaeltayeb.souq.repository.ToHistoryRepository;

import okhttp3.ResponseBody;

public class ToHistoryViewModel extends ViewModel {

    private final ToHistoryRepository toHistoryRepository;

    public ToHistoryViewModel() {
        toHistoryRepository = new ToHistoryRepository();
    }

    public LiveData<ResponseBody> addToHistory(History history) {
        return toHistoryRepository.addToHistory(history);
    }
}
