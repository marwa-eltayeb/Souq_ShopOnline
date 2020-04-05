package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.CartViewModel;
import com.marwaeltayeb.souq.adapter.CartAdapter;
import com.marwaeltayeb.souq.databinding.ActivityCartBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.util.List;

import static com.marwaeltayeb.souq.storage.LanguageUtils.loadLocale;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Product> favoriteList;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.cart));

        setUpRecyclerView();

        getProductsInCart();
    }

    private void setUpRecyclerView() {
        binding.productsInCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productsInCart.setHasFixedSize(true);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    private void getProductsInCart() {
        if (isNetworkConnected(this)) {
            cartViewModel.getProductsInCart(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, cartApiResponse -> {
                if (cartApiResponse != null) {
                    favoriteList = cartApiResponse.getProductsInCart();
                    if (favoriteList.size() == 0) {
                        binding.noBookmarks.setVisibility(View.VISIBLE);
                        binding.emptyCart.setVisibility(View.VISIBLE);
                    } else {
                        binding.productsInCart.setVisibility(View.VISIBLE);
                    }
                    cartAdapter = new CartAdapter(getApplicationContext(), favoriteList, product -> {
                        Intent intent = new Intent(CartActivity.this, DetailsActivity.class);
                        // Pass an object of product class
                        intent.putExtra(PRODUCT, (product));
                        startActivity(intent);
                    }, this);
                }

                binding.loadingIndicator.setVisibility(View.GONE);
                binding.productsInCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            });
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.loadingIndicator.setVisibility(View.GONE);
            binding.emptyCart.setText(getString(R.string.no_internet_connection));
        }
    }
}
