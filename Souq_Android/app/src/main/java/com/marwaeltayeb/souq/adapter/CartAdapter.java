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
import com.marwaeltayeb.souq.databinding.CartListItemBinding;
import com.marwaeltayeb.souq.model.Product;

import java.util.List;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context mContext;
    // Declare an arrayList for favorite products
    private List<Product> productInCart;

    private Product currentProduct;

    // Create a final private SearchAdapterOnClickHandler called mClickHandler
    private CartAdapter.CartAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface CartAdapterOnClickHandler {
        void onClick(Product product);
    }

    public CartAdapter(Context mContext, List<Product> productInCart, CartAdapter.CartAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.productInCart = productInCart;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CartListItemBinding cartListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item,parent,false);
        return new CartViewHolder(cartListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        currentProduct = productInCart.get(position);
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
        if (productInCart == null) {
            return 0;
        }
        return productInCart.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final CartListItemBinding binding;

        private CartViewHolder(CartListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentProduct = productInCart.get(position);
            // Send product through click
            clickHandler.onClick(currentProduct);
        }
    }
}
