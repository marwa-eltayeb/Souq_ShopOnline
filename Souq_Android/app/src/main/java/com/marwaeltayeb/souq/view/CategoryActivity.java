package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.CategoryViewModel;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityCategoryBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.ProductDataSourceFactory;
import com.marwaeltayeb.souq.utils.Constant;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class CategoryActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private static final String TAG = "CategoryActivity";
    private ActivityCategoryBinding binding;
    private ProductAdapter productAdapter;
    private CategoryViewModel categoryViewModel;
    private String category;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_category);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            // This line shows Up button
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get Category from ProductActivity Intent
        Intent intent = getIntent();
        category = intent.getStringExtra(Constant.CATEGORY);

        // Update Toolbar
        getSupportActionBar().setTitle(category);

        categoryViewModel = ViewModelProviders.of(this, new ProductDataSourceFactory(category.toLowerCase())).get(CategoryViewModel.class);

        setupRecyclerViews();

        getProductsByCategory();
    }

    private void setupRecyclerViews() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.recyclerView.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }


    public void getProductsByCategory() {
        categoryViewModel.categoryPagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                productAdapter.notifyDataSetChanged();
                productAdapter.submitList(products);
            }
        });

        binding.recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(CategoryActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
