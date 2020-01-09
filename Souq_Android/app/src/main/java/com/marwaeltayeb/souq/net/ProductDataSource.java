package com.marwaeltayeb.souq.net;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.marwaeltayeb.souq.model.ProductApiResponse;
import com.marwaeltayeb.souq.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDataSource extends PageKeyedDataSource<Integer, Product> {

    private static final int FIRST_PAGE = 1;

    static final int PAGE_SIZE = 20;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductByCategory()
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductByCategory()
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback) {
        RetrofitClient.getInstance()
                .getApi().getProductByCategory()
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {

                    }
                });
    }
}
