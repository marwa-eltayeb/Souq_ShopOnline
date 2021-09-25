package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.NewsFeedResponse;
import com.marwaeltayeb.souq.repository.NewsFeedRepository;

public class NewsFeedViewModel extends ViewModel {

    private final NewsFeedRepository newsFeedRepository;

    public NewsFeedViewModel() {
        newsFeedRepository = new NewsFeedRepository();
    }

    public LiveData<NewsFeedResponse> getPosters() {
        return newsFeedRepository.getPosters();
    }
}
