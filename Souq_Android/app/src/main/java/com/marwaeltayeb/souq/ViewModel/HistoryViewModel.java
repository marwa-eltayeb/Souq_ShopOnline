package com.marwaeltayeb.souq.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.HistoryDataSource;
import com.marwaeltayeb.souq.net.HistoryDataSourceFactory;

public class HistoryViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> historyPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> liveDataSource;

    public HistoryViewModel() {

        HistoryDataSourceFactory historyDataSourceFactory = new HistoryDataSourceFactory();

        liveDataSource = historyDataSourceFactory.getProductsInHistory();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(HistoryDataSource.PAGE_SIZE).build();

        // Build the paged list
        historyPagedList = (new LivePagedListBuilder(historyDataSourceFactory, pagedListConfig)).build();
    }


}
