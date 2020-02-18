package com.marwaeltayeb.souq.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.FavoriteViewModel;
import com.marwaeltayeb.souq.adapter.WishListAdapter;
import com.marwaeltayeb.souq.databinding.ActivityWishlistBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.SharedPrefManager;

import java.util.List;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class WishListActivity extends AppCompatActivity {

    private ActivityWishlistBinding binding;
    private WishListAdapter wishListAdapter;
    private List<Product> favoriteList;
    private FavoriteViewModel favoriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist);

        setUpRecyclerView();

        getFavorites();
    }

    private void setUpRecyclerView(){
        binding.favoriteList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.favoriteList.setHasFixedSize(true);
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    private void getFavorites() {
        favoriteViewModel.getFavorites(SharedPrefManager.getInstance(this).getUserInfo().getId()).observe(this, favoriteApiResponse -> {
            if (favoriteApiResponse != null) {
                favoriteList = favoriteApiResponse.getFavorites();
                wishListAdapter = new WishListAdapter(getApplicationContext(), favoriteList, product -> {
                    Intent intent = new Intent(WishListActivity.this, DetailsActivity.class);
                    // Pass an object of product class
                    intent.putExtra(PRODUCT, (product));
                    startActivity(intent);
                });
            }

            binding.favoriteList.setAdapter(wishListAdapter);
            wishListAdapter.notifyDataSetChanged();
        });
    }
}
