package com.marwaeltayeb.souq.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.Product;

public class LaptopDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> laptopLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource laptopDataSource;

    private final String category;
    private final int userId;

    public LaptopDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        laptopDataSource = new ProductDataSource(category,userId);

        // Posting the Data source to get the values
        laptopLiveDataSource.postValue(laptopDataSource);

        // Returning the Data source
        return laptopDataSource;
    }
}