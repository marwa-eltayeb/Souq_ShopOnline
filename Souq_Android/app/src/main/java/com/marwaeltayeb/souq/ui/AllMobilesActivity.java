package com.marwaeltayeb.souq.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.ProductViewModel;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityAllMobilesBinding;
import com.marwaeltayeb.souq.model.Product;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class AllMobilesActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllMobilesBinding binding;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_mobiles);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        setupRecyclerViews();

        getAllMobiles();
    }

    private void setupRecyclerViews() {
        // Mobiles
        binding.allMobilesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.allMobilesRecyclerView.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }

    public void getAllMobiles() {
        // Observe the productPagedList from ViewModel
        productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                productAdapter.notifyDataSetChanged();
                productAdapter.submitList(products);
            }
        });

        binding.allMobilesRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllMobilesActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
