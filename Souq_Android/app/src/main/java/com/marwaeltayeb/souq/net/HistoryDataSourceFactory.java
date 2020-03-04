package com.marwaeltayeb.souq.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class HistoryDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> historyLiveDataSource = new MutableLiveData<>();

    private HistoryDataSource historyDataSource;

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        historyDataSource = new HistoryDataSource();

        // Posting the Data source to get the values
        historyLiveDataSource.postValue(historyDataSource);

        // Returning the Data source
        return historyDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getProductsInHistory() {
        return historyLiveDataSource;
    }

}
