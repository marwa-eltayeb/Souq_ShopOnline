package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.model.History;
import com.marwaeltayeb.souq.repository.ToHistoryRepository;

import okhttp3.ResponseBody;

public class ToHistoryViewModel extends AndroidViewModel {

    private ToHistoryRepository toHistoryViewModel;

    public ToHistoryViewModel(@NonNull Application application) {
        super(application);
        toHistoryViewModel = new ToHistoryRepository(application);
    }

    public LiveData<ResponseBody> addToHistory(History history) {
        return toHistoryViewModel.addToHistory(history);
    }
}
