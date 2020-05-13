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
import com.marwaeltayeb.souq.ViewModel.AddFavoriteViewModel;
import com.marwaeltayeb.souq.ViewModel.FromCartViewModel;
import com.marwaeltayeb.souq.ViewModel.RemoveFavoriteViewModel;
import com.marwaeltayeb.souq.databinding.CartListItemBinding;
import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.RequestCallback;

import java.text.DecimalFormat;
import java.util.List;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;

    private List<Product> productsInCart;

    private Product currentProduct;

    private AddFavoriteViewModel addFavoriteViewModel;
    private RemoveFavoriteViewModel removeFavoriteViewModel;
    private FromCartViewModel fromCartViewModel;

    private CartAdapter.CartAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface CartAdapterOnClickHandler {
        void onClick(Product product);
    }

    public CartAdapter(Context mContext, List<Product> productInCart, CartAdapter.CartAdapterOnClickHandler clickHandler, FragmentActivity activity) {
        this.mContext = mContext;
        this.productsInCart = productInCart;
        this.clickHandler = clickHandler;
        addFavoriteViewModel = ViewModelProviders.of(activity).get(AddFavoriteViewModel.class);
        removeFavoriteViewModel = ViewModelProviders.of(activity).get(RemoveFavoriteViewModel.class);
        fromCartViewModel = ViewModelProviders.of(activity).get(FromCartViewModel.class);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CartListItemBinding cartListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item, parent, false);
        return new CartViewHolder(cartListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        currentProduct = productsInCart.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText(formattedPrice + " EGP");

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

        // If product is inserted
        if (currentProduct.isFavourite() == 1) {
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
        }
    }

    @Override
    public int getItemCount() {
        if (productsInCart == null) {
            return 0;
        }
        return productsInCart.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Create view instances
        private final CartListItemBinding binding;

        private CartViewHolder(CartListItemBinding binding) {
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
            currentProduct = productsInCart.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
                    break;
                case R.id.imgFavourite:
                    toggleFavourite();
                    break;
                case R.id.imgCart:
                    deleteProductsInCart();
                    break;
            }
        }

        private void toggleFavourite() {
            // If favorite is not bookmarked
            if (currentProduct.isFavourite() != 1) {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
                insertFavoriteProduct(() -> {
                    currentProduct.setIsFavourite(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Added");
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border);
                deleteFavoriteProduct(() -> {
                    currentProduct.setIsFavourite(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Removed");
            }
        }

        private void deleteProductsInCart() {
            deleteFromCart(() -> {
                currentProduct.setIsInCart(false);
                notifyDataSetChanged();
            });
            productsInCart.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            notifyItemRangeChanged(getAdapterPosition(), productsInCart.size());
            showSnackBar("Removed From Cart");
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void insertFavoriteProduct(RequestCallback callback) {
            Favorite favorite = new Favorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId());
            addFavoriteViewModel.addFavorite(favorite, callback);
        }

        private void deleteFavoriteProduct(RequestCallback callback) {
            removeFavoriteViewModel.removeFavorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(), callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), currentProduct.getProductId(), callback);
        }
    }
}
