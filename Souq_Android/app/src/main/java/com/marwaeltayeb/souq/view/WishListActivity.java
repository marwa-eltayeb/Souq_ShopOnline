package com.marwaeltayeb.souq.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.WishListAdapter;
import com.marwaeltayeb.souq.model.FavoriteApiResponse;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.net.RetrofitClient;
import com.marwaeltayeb.souq.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;

public class WishListActivity extends AppCompatActivity {

    private static final String TAG = "WishListActivity";
    private RecyclerView recyclerView;
    private WishListAdapter wishListAdapter;
    List<Product> favoriteList;
    private TextView noBookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Toast.makeText(this, SharedPrefManager.getInstance(this).getUserInfo().getId() + "", Toast.LENGTH_SHORT).show();

        // Set up recyclerView
        recyclerView = findViewById(R.id.favorite_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        noBookmarks = findViewById(R.id.noBookmarks);

        getFavorites();
    }

    private void getFavorites() {
        RetrofitClient.getInstance().getApi().getFavorites(SharedPrefManager.getInstance(this).getUserInfo().getId()).enqueue(new Callback<FavoriteApiResponse>() {
            @Override
            public void onResponse(Call<FavoriteApiResponse> call, Response<FavoriteApiResponse> response) {
                if (response.body() != null) {
                    favoriteList = response.body().getFavorites();
                    Toast.makeText(WishListActivity.this, response.body().getFavorites().size() + "", Toast.LENGTH_SHORT).show();
                    wishListAdapter = new WishListAdapter(getApplicationContext(), favoriteList, new WishListAdapter.WishListAdapterOnClickHandler() {
                        @Override
                        public void onClick(Product product) {
                            Intent intent = new Intent(WishListActivity.this, DetailsActivity.class);
                            // Pass an object of product class
                            intent.putExtra(PRODUCT, (product));
                            startActivity(intent);
                        }
                    });
                }
                // Set the adapter
                recyclerView.setAdapter(wishListAdapter);
                wishListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FavoriteApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
