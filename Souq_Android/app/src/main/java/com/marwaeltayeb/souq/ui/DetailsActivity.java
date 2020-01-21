package com.marwaeltayeb.souq.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityDetailsBinding;
import com.marwaeltayeb.souq.model.Product;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        getProductDetails();
    }

    private void getProductDetails(){
        // Receive the product object
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra(PRODUCT);

        // Set Custom ActionBar Layout
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_title_layout);
        ((TextView) findViewById(R.id.action_bar_title)).setText(product.getProductName());

        binding.nameOfProduct.setText(product.getProductName());
        binding.priceOfProduct.setText(String.valueOf(product.getProductPrice()));

        String imageUrl = LOCALHOST + product.getProductImage().replaceAll("\\\\", "/");
        Glide.with(this)
                .load(imageUrl)
                .into(binding.imageOfProduct);
    }
}
