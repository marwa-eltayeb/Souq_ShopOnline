package com.marwaeltayeb.souq.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.WishlistItemBinding;
import com.marwaeltayeb.souq.model.Product;

import java.util.List;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder>{

    private Context mContext;
    // Declare an arrayList for favorite products
    private List<Product> favoriteList;

    private Product currentProduct;

    // Create a final private SearchAdapterOnClickHandler called mClickHandler
    private WishListAdapter.WishListAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface WishListAdapterOnClickHandler {
        void onClick(Product product);
    }

    public WishListAdapter(Context mContext, List<Product> favoriteList, WishListAdapter.WishListAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.favoriteList = favoriteList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        WishlistItemBinding wishlistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wishlist_item,parent,false);
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
    }

    @Override
    public int getItemCount() {
        if (favoriteList == null) {
            return 0;
        }
        return favoriteList.size();
    }

    class WishListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final WishlistItemBinding binding;

        private WishListViewHolder(WishlistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentProduct = favoriteList.get(position);
            // Send product through click
            clickHandler.onClick(currentProduct);
        }
    }

}
