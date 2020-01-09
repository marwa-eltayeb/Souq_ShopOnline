package com.marwaeltayeb.souq.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.marwaeltayeb.souq.model.ProductModel;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;

import static com.marwaeltayeb.souq.net.ProductDataSourceFactory.productDataSource;


public class ProductViewModel {


    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<ProductModel>> productPagedList;
    private LiveData<PageKeyedDataSource<Integer, ProductModel>> liveDataSource;

    // Constructor
    public ProductViewModel() {

        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory();

        // Get the live database source from database source factory
        liveDataSource = productDataSourceFactory.getProductLiveDataSource();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        //.setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();

        // Build the paged list
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, pagedListConfig)).build();
    }

    public void clear() {
        productDataSource.invalidate();
    }

}
