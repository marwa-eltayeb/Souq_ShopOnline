package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.repository.FromHistoryRepository;

import okhttp3.ResponseBody;

public class FromHistoryViewModel extends ViewModel {

    private final FromHistoryRepository fromHistoryRepository;

    public FromHistoryViewModel() {
        fromHistoryRepository = new FromHistoryRepository();
    }

    public LiveData<ResponseBody> removeAllFromHistory() {
        return fromHistoryRepository.removeAllFromHistory();
    }
}
