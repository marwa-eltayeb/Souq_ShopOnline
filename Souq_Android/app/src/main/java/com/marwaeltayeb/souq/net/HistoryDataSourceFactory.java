package com.marwaeltayeb.souq.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.ViewModel.HistoryViewModel;
import com.marwaeltayeb.souq.model.Product;

public class HistoryDataSourceFactory extends DataSource.Factory implements ViewModelProvider.Factory{

    private int userId;

    public HistoryDataSourceFactory(int userId) {
        this.userId = userId;
    }

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> historyLiveDataSource = new MutableLiveData<>();

    private HistoryDataSource historyDataSource;

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        historyDataSource = new HistoryDataSource(userId);

        // Posting the Data source to get the values
        historyLiveDataSource.postValue(historyDataSource);

        // Returning the Data source
        return historyDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getProductsInHistory() {
        return historyLiveDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HistoryViewModel(userId);
    }

}
