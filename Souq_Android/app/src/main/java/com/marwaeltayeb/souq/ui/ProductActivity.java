package com.marwaeltayeb.souq.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Build;
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
import com.marwaeltayeb.souq.receiver.NetworkChangeReceiver;
import com.marwaeltayeb.souq.utils.OnNetworkListener;
import com.marwaeltayeb.souq.utils.Slide;

import java.util.ArrayList;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler {

    private ActivityProductBinding binding;

    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    private Snackbar snack;

    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.txtSeeAllMobiles.setOnClickListener(this);
        binding.txtSeeAllLaptops.setOnClickListener(this);

        setUpViews();

        getProducts();

        flipImages(Slide.getSlides());

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);

    }

    private void setUpViews() {
        binding.listOfMobiles.setHasFixedSize(true);
        binding.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.listOfLaptops.setHasFixedSize(true);
        binding.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        productAdapter = new ProductAdapter(this,this);
    }

    private void getProducts() {
        if (isNetworkConnected(this)) {
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

    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        getProducts();
        binding.textViewMobiles.setVisibility(View.VISIBLE);
        binding.txtSeeAllMobiles.setVisibility(View.VISIBLE);
        binding.textViewLaptops.setVisibility(View.VISIBLE);
        binding.txtSeeAllLaptops.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
