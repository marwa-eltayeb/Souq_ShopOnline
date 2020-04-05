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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.ViewModel.AddFavoriteViewModel;
import com.marwaeltayeb.souq.ViewModel.FromCartViewModel;
import com.marwaeltayeb.souq.ViewModel.RemoveFavoriteViewModel;
import com.marwaeltayeb.souq.ViewModel.ToCartViewModel;
import com.marwaeltayeb.souq.databinding.WishlistItemBinding;
import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;

import java.util.List;

import static com.marwaeltayeb.souq.storage.CartUtils.getCartState;
import static com.marwaeltayeb.souq.storage.CartUtils.setCartState;
import static com.marwaeltayeb.souq.storage.FavoriteUtils.getFavoriteState;
import static com.marwaeltayeb.souq.storage.FavoriteUtils.setFavoriteState;
import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    private Context mContext;
    // Declare an arrayList for favorite products
    private List<Product> favoriteList;

    private Product currentProduct;

    private AddFavoriteViewModel addFavoriteViewModel;
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
        addFavoriteViewModel = ViewModelProviders.of(activity).get(AddFavoriteViewModel.class);
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
        holder.binding.txtProductPrice.setText(String.valueOf(currentProduct.getProductPrice()));

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

        // If product is inserted
        if (getFavoriteState(mContext, String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()), currentProduct.getProductId())) {
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
        }

        // If product is added to cart
        if (getCartState(mContext,String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()) ,currentProduct.getProductId())) {
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
            binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border);
            deleteFavoriteProduct();
            favoriteList.remove(currentProduct);
            notifyItemRemoved(currentProduct.getProductId());
            notifyItemRangeChanged(currentProduct.getProductId(), favoriteList.size());
            setFavoriteState(mContext, String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()), currentProduct.getProductId(), false);
            showSnackBar("Bookmark Removed");
        }

        private void toggleProductsInCart() {
            // If Product is not added to cart
            if (!getCartState(mContext, String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()),currentProduct.getProductId())) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart();
                setCartState(mContext, String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()),currentProduct.getProductId(), true);
                Toast.makeText(mContext, currentProduct.getProductId() + "", Toast.LENGTH_SHORT).show();
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart();
                setCartState(mContext,String.valueOf(LoginUtils.getInstance(mContext).getUserInfo().getId()) ,currentProduct.getProductId(), false);
                showSnackBar("Removed From Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void insertFavoriteProduct() {
            Favorite favorite = new Favorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            addFavoriteViewModel.addFavorite(favorite);
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
