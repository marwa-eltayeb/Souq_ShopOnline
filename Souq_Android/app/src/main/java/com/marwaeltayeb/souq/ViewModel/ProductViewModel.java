package com.marwaeltayeb.souq.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.LaptopDataSourceFactory;
import com.marwaeltayeb.souq.net.ProductDataSource;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;


public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource;

    public LiveData<PagedList<Product>> laptopPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> laptopLiveDataSource;

    private PagedList.Config pagedListConfig;

    // Constructor
    public ProductViewModel() {
        // Get PagedList configuration
        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();

        initProductDataSource();
        initLaptopDataSource();
    }

    private void initProductDataSource(){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();

        // Get the live database source from database source factory
        productLiveDataSource = productDataSourceFactory.getProductLiveDataSource();

        // Build the paged list
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, pagedListConfig)).build();
    }

    private void initLaptopDataSource(){
        // Get our database source factory
        LaptopDataSourceFactory laptopDataSourceFactory = new LaptopDataSourceFactory();

        // Get the live database source from database source factory
        laptopLiveDataSource = laptopDataSourceFactory.getLaptopLiveDataSource();


        // Build the paged list
        laptopPagedList = (new LivePagedListBuilder(laptopDataSourceFactory, pagedListConfig)).build();
    }

}
