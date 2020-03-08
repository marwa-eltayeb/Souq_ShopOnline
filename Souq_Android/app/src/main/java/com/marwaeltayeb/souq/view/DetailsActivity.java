package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ActivityDetailsBinding;
import com.marwaeltayeb.souq.model.Product;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        binding.txtSeeAllReviews.setOnClickListener(this);
        binding.writeReview.setOnClickListener(this);

        getProductDetails();
    }

    private void getProductDetails(){
        // Receive the product object
        Product product = getIntent().getParcelableExtra(PRODUCT);

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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSeeAllReviews) {
            Intent allReviewIntent = new Intent(DetailsActivity.this, AllReviewsActivity.class);
            startActivity(allReviewIntent);
        }else if(view.getId() == R.id.writeReview){
            Intent allReviewIntent = new Intent(DetailsActivity.this, WriteReviewActivity.class);
            startActivity(allReviewIntent);
        }
    }


}
