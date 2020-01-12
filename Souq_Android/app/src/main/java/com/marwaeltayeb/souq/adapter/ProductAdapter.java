package com.marwaeltayeb.souq.adapter;

import android.annotation.SuppressLint;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ProductListItemBinding;
import com.marwaeltayeb.souq.model.Product;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;

public class ProductAdapter extends PagedListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private Context mContext;

    public ProductAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductListItemBinding productListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.product_list_item,parent,false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);

        if (product != null) {
            holder.binding.txtProductName.setText(product.getProductName());
            holder.binding.txtProductPrice.setText(String.valueOf(product.getProductPrice()));

            // Load the Product image into ImageView
            String imageUrl = LOCALHOST + product.getProductImage().replaceAll("\\\\", "/");
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.binding.imgProductImage);

            Log.d("test",imageUrl);

        } else {
            Toast.makeText(mContext, "Product is null", Toast.LENGTH_LONG).show();
        }
    }

    public Product getProductAt(int position) {
        return getItem(position);
    }

    @Override
    public PagedList<Product> getCurrentList() {
        return super.getCurrentList();
    }

    // It determine if two list objects are the same or not
    private static DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {
            return oldProduct.getProductName().equals(newProduct.getProductName());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    static class ProductViewHolder extends RecyclerView.ViewHolder{
        // Create view instances
        private final ProductListItemBinding binding;

        private ProductViewHolder(ProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
