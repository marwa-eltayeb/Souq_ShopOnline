package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.History;
import com.marwaeltayeb.souq.repository.ToHistoryRepository;

import okhttp3.ResponseBody;

public class ToHistoryViewModel extends AndroidViewModel {

    private ToHistoryRepository toHistoryRepository;

    public ToHistoryViewModel(@NonNull Application application) {
        super(application);
        toHistoryRepository = new ToHistoryRepository(application);
    }

    public LiveData<ResponseBody> addToHistory(History history) {
        return toHistoryRepository.addToHistory(history);
    }
}
