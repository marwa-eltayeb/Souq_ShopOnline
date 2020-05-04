package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.repository.SearchRepository;

public class SearchViewModel  extends AndroidViewModel {

    private SearchRepository searchRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository(application);
    }


    public LiveData<ProductApiResponse> getProductsBySearch(String keyword, int userId) {
        return searchRepository.getResponseDataBySearch(keyword, userId);
    }


}
