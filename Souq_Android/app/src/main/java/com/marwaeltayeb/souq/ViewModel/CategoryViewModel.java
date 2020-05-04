package com.marwaeltayeb.souq.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.ProductDataSource;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;

public class CategoryViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> categoryPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> categoryLiveDataSource;

    public void loadProductsByCategory(String category, int userId) {
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        categoryLiveDataSource = productDataSourceFactory.getProductLiveDataSource();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ProductDataSource.PAGE_SIZE)
                        .build();

        // Build the paged list
        categoryPagedList = (new LivePagedListBuilder(productDataSourceFactory, pagedListConfig)).build();
    }

}
