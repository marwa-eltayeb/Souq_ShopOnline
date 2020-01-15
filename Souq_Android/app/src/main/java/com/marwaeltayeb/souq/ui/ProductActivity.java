package com.marwaeltayeb.souq.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.ProductViewModel;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.databinding.ActivityProductBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.utils.Slide;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityProductBinding binding;

    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        binding.txtSeeAllMobiles.setOnClickListener(this);
        binding.txtSeeAllLaptops.setOnClickListener(this);

        setUpViews();

        getProducts();

        flipImages(Slide.getSlides());
    }

    private void setUpViews() {
        binding.listOfMobiles.setHasFixedSize(true);
        binding.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.listOfLaptops.setHasFixedSize(true);
        binding.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        productAdapter = new ProductAdapter(this);
    }

    private void getProducts() {
        if (isNetworkConnected()) {
            // Observe the productPagedList from ViewModel
            productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    productAdapter.submitList(products);
                }
            });

            binding.listOfMobiles.setAdapter(productAdapter);
            binding.listOfLaptops.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }else {
            binding.textViewMobiles.setVisibility(View.INVISIBLE);
            binding.txtSeeAllMobiles.setVisibility(View.INVISIBLE);
            binding.textViewLaptops.setVisibility(View.INVISIBLE);
            binding.txtSeeAllLaptops.setVisibility(View.INVISIBLE);
            showSnackBar();
        }
    }


    private void flipImages(ArrayList<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.imageSlider.addView(imageView);
        }

        binding.imageSlider.setFlipInterval(2000);
        binding.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllMobiles:
                goToSeeAllMobiles();
                break;
            case R.id.txtSeeAllLaptops:
                goToSeeAllLaptops();
                break;
        }
    }

    private void goToSeeAllMobiles() {
        Intent intent = new Intent(this, AllMobilesActivity.class);
        startActivity(intent);
    }

    private void goToSeeAllLaptops() {
        Intent intent = new Intent(this, AllLaptopsActivity.class);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void showSnackBar() {
        final Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }
}
