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

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.model.ProductModel;

public class ProductAdapter extends PagedListAdapter<ProductModel, ProductAdapter.ProductViewHolder> {

    private Context mContext;
    private ProductModel product;

    public ProductAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        product = getItem(position);
    }

    public ProductModel getProductAt(int position) {
        return getItem(position);
    }

    @Override
    public PagedList<ProductModel> getCurrentList() {
        return super.getCurrentList();
    }

    // It determine if two list objects are the same or not
    private static DiffUtil.ItemCallback<ProductModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ProductModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductModel oldProduct, @NonNull ProductModel newProduct) {
            return oldProduct.getProductName().equals(newProduct.getProductName());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ProductModel oldProduct, @NonNull ProductModel newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances


        private ProductViewHolder(View itemView) {
            super(itemView);

            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
