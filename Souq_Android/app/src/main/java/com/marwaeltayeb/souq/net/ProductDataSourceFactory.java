package com.marwaeltayeb.souq.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.marwaeltayeb.souq.model.ProductModel;

public class ProductDataSourceFactory extends DataSource.Factory {

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, ProductModel>> productLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource productDataSource;

    @Override
    public DataSource<Integer, ProductModel> create() {
        // Getting our Data source object
        productDataSource = new ProductDataSource();

        // Posting the Data source to get the values
        productLiveDataSource.postValue(productDataSource);

        // Returning the Data source
        return productDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, ProductModel>> getProductLiveDataSource() {
        return productLiveDataSource;
    }
}
