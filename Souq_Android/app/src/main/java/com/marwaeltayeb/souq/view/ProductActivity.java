package com.marwaeltayeb.souq.view;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.ProductViewModel;
import com.marwaeltayeb.souq.ViewModel.SearchViewModel;
import com.marwaeltayeb.souq.adapter.ProductAdapter;
import com.marwaeltayeb.souq.adapter.SearchAdapter;
import com.marwaeltayeb.souq.databinding.ActivityProductBinding;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.receiver.NetworkChangeReceiver;
import com.marwaeltayeb.souq.storage.SharedPrefManager;
import com.marwaeltayeb.souq.utils.OnNetworkListener;
import com.marwaeltayeb.souq.utils.Slide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.marwaeltayeb.souq.utils.Constant.PRODUCT;
import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private ActivityProductBinding binding;

    private ProductAdapter mobileAdapter;
    private ProductAdapter laptopAdapter;
    private SearchAdapter searchAdapter;
    private List<Product> searchedMovieList;

    private ProductViewModel productViewModel;
    private SearchViewModel searchViewModel;

    private Snackbar snack;

    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllMobiles.setOnClickListener(this);
        binding.included.content.txtSeeAllLaptops.setOnClickListener(this);

        setUpViews();

        getMobiles();
        getLaptops();

        flipImages(Slide.getSlides());

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }

    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer =  binding.navView.getHeaderView(0);
        CircleImageView circleImageView = headerContainer.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(SharedPrefManager.getInstance(this).getUserInfo().getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(SharedPrefManager.getInstance(this).getUserInfo().getEmail());

        binding.included.content.listOfMobiles.setHasFixedSize(true);
        binding.included.content.listOfMobiles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.included.content.listOfLaptops.setHasFixedSize(true);
        binding.included.content.listOfLaptops.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mobileAdapter = new ProductAdapter(this, this);
        laptopAdapter = new ProductAdapter(this, this);
    }

    private void getMobiles() {
        if (isNetworkConnected(this)) {
            // Observe the productPagedList from ViewModel
            productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    mobileAdapter.submitList(products);
                }
            });

            binding.included.content.listOfMobiles.setAdapter(mobileAdapter);
            mobileAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getLaptops() {
        if (isNetworkConnected(this)) {
            // Observe the productPagedList from ViewModel
            productViewModel.laptopPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    laptopAdapter.submitList(products);
                }
            });

            binding.included.content.listOfLaptops.setAdapter(laptopAdapter);
            laptopAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void flipImages(ArrayList<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.included.content.imageSlider.addView(imageView);
        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
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
            case R.id.profile_image:
                Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
                showCustomAlertDialog();
                break;
        }
    }

    public void showCustomAlertDialog(){
        final Dialog MyDialog = new Dialog(ProductActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        Button takePicture = MyDialog.findViewById(R.id.takePicture);
        Button useGallery = MyDialog.findViewById(R.id.useGallery);

        takePicture.setEnabled(true);
        useGallery.setEnabled(true);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, I'm Custom Alert Dialog", Toast.LENGTH_LONG).show();
            }
        });
        useGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });

        MyDialog.show();
    }

    private void goToSeeAllMobiles() {
        Intent intent = new Intent(this, AllMobilesActivity.class);
        startActivity(intent);
    }

    private void goToSeeAllLaptops() {
        Intent intent = new Intent(this, AllLaptopsActivity.class);
        startActivity(intent);
    }

    private void goToCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void goToAddProductActivity() {
        Intent intent = new Intent(this, AddProductActivity.class);
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
        showOrHideViews(View.VISIBLE);
        getMobiles();
        getLaptops();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchViewItem = menu.findItem
                (R.id.action_search);
        SearchView searchView = (SearchView)
                searchViewItem.getActionView();

        MenuItem addMenu = menu.findItem(R.id.action_addProduct);
        if (SharedPrefManager.getInstance(this).getUserInfo().isAdmin()) {
            addMenu.setVisible(true);
        } else {
            addMenu.setVisible(false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isNetworkConnected(getApplicationContext())) {
                    Search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isNetworkConnected(getApplicationContext())) {
                    Search(newText);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchedMovieList != null) {
                    searchAdapter.clear();
                    setUpViews();
                    showOrHideViews(View.VISIBLE);
                    hideViews(View.VISIBLE);
                    getMobiles();
                    getLaptops();
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                goToCartActivity();
                return true;
            case R.id.action_addProduct:
                goToAddProductActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setViewsAfterSearch() {
        binding.included.content.listOfMobiles.setHasFixedSize(true);
        binding.included.content.listOfMobiles.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        showOrHideViews(View.GONE);
        hideViews(View.GONE);
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewMobiles.setVisibility(view);
        binding.included.content.txtSeeAllMobiles.setVisibility(view);
        binding.included.content.textViewLaptops.setVisibility(view);
        binding.included.content.txtSeeAllLaptops.setVisibility(view);
    }

    private void hideViews(int view) {
        binding.included.content.listOfLaptops.setVisibility(view);
        binding.included.content.imageSlider.setVisibility(view);
    }

    private void Search(String query) {
        setViewsAfterSearch();

        searchViewModel.getProductsBySearch(query).observe(this, productApiResponse -> {
            if (productApiResponse != null) {
                searchedMovieList = productApiResponse.getProducts();
                if (searchedMovieList.isEmpty()) {
                    Toast.makeText(ProductActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }

                searchAdapter = new SearchAdapter(getApplicationContext(), searchedMovieList, new SearchAdapter.SearchAdapterOnClickHandler() {
                    @Override
                    public void onClick(Product product) {
                        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
                        // Pass an object of product class
                        intent.putExtra(PRODUCT, (product));
                        startActivity(intent);
                    }
                });
            }
            binding.included.content.listOfMobiles.setAdapter(searchAdapter);
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_mobiles) {

        } else if (id == R.id.nav_laptops) {

        } else if (id == R.id.nav_babies) {

        } else if (id == R.id.nav_toys) {

        } else if (id == R.id.nav_trackOrder) {

        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this,AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_newsFeed) {

        } else if (id == R.id.nav_wishList) {
            Intent wishListIntent = new Intent(this,WishListActivity.class);
            startActivity(wishListIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
