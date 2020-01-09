package com.marwaeltayeb.souq.adapter;

import android.annotation.SuppressLint;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marwaeltayeb.souq.databinding.ProductListItemBinding;
import com.marwaeltayeb.souq.model.Product;

public class ProductAdapter extends PagedListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private Context mContext;
    private Product product;


    public ProductAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductListItemBinding productListItemBinding = ProductListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        product = getItem(position);

        holder.binding.txtProductName.setText(product.getProductName());

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

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final ProductListItemBinding binding;

        private ProductViewHolder(ProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
