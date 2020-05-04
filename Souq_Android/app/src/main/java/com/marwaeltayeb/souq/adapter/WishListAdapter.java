package com.marwaeltayeb.souq.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.FromCartViewModel;
import com.marwaeltayeb.souq.ViewModel.RemoveFavoriteViewModel;
import com.marwaeltayeb.souq.ViewModel.ToCartViewModel;
import com.marwaeltayeb.souq.databinding.WishlistItemBinding;
import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.text.DecimalFormat;
import java.util.List;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private Context mContext;
    // Declare an arrayList for favorite products
    private List<Product> favoriteList;

    private Product currentProduct;

    private RemoveFavoriteViewModel removeFavoriteViewModel;
    private ToCartViewModel toCartViewModel;
    private FromCartViewModel fromCartViewModel;

    // Create a final private SearchAdapterOnClickHandler called mClickHandler
    private WishListAdapter.WishListAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface WishListAdapterOnClickHandler {
        void onClick(Product product);
    }

    public WishListAdapter(Context mContext, List<Product> favoriteList, WishListAdapter.WishListAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.favoriteList = favoriteList;
        this.clickHandler = clickHandler;
        removeFavoriteViewModel = ViewModelProviders.of(activity).get(RemoveFavoriteViewModel.class);
        toCartViewModel = ViewModelProviders.of(activity).get(ToCartViewModel.class);
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        WishlistItemBinding wishlistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wishlist_item, parent, false);
        return new WishListViewHolder(wishlistItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        currentProduct = favoriteList.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText(formattedPrice + " EGP");

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

        // If product is added to cart
        if (currentProduct.isInCart()==1) {
            holder.binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
        }

    }

    @Override
    public int getItemCount() {
        if (favoriteList == null) {
            return 0;
        }
        return favoriteList.size();
    }

    class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Create view instances
        private final WishlistItemBinding binding;

        private WishListViewHolder(WishlistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
            binding.imgFavourite.setOnClickListener(this);
            binding.imgCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentProduct = favoriteList.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
                    break;
                case R.id.imgFavourite:
                    deleteFavorite();
                    break;
                case R.id.imgCart:
                    toggleProductsInCart();
                    break;
            }
        }

        private void deleteFavorite() {
            deleteFavoriteProduct();
            favoriteList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), favoriteList.size());
            showSnackBar("Bookmark Removed");
        }

        private void toggleProductsInCart() {
            // If Product is not added to cart
            if (currentProduct.isInCart()!=1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart();
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart();
                showSnackBar("Removed From Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void deleteFavoriteProduct() {
            removeFavoriteViewModel.removeFavorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
        }

        private void insertToCart() {
            Cart cart = new Cart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            toCartViewModel.addToCart(cart);
        }

        private void deleteFromCart() {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
        }
    }

}
