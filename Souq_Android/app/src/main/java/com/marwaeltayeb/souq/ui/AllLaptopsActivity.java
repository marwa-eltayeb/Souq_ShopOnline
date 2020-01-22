package com.marwaeltayeb.souq.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.ProductViewModel;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityAllLaptopsBinding;
import com.marwaeltayeb.souq.model.Product;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class AllLaptopsActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllLaptopsBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_laptops);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        setupRecyclerViews();

        getAllLaptops();
    }

    private void setupRecyclerViews() {
        // Laptops
        binding.allLaptopsRecyclerView.setHasFixedSize(true);
        binding.allLaptopsRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        productAdapter = new ProductAdapter(this,this);
    }

    public void getAllLaptops() {
        // Observe the productPagedList from ViewModel
        productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                productAdapter.submitList(products);
            }
        });

        binding.allLaptopsRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllLaptopsActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
