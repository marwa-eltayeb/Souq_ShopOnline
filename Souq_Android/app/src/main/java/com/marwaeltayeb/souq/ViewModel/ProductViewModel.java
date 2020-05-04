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

    // Get PagedList configuration
    private final static PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadMobiles(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        productLiveDataSource = productDataSourceFactory.getProductLiveDataSource();

        // Build the paged list
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, pagedListConfig)).build();
    }

    public void loadLaptops(String category, int userId){
        // Get our database source factory
        LaptopDataSourceFactory laptopDataSourceFactory = new LaptopDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        productLiveDataSource = laptopDataSourceFactory.getLaptopLiveDataSource();

        // Build the paged list
        laptopPagedList = (new LivePagedListBuilder(laptopDataSourceFactory, pagedListConfig)).build();
    }
}
