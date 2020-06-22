package com.marwaeltayeb.souq.adapter;

import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
import com.marwaeltayeb.souq.ViewModel.ToHistoryViewModel;
import com.marwaeltayeb.souq.databinding.ProductListItemBinding;
import com.marwaeltayeb.souq.model.Cart;
import com.marwaeltayeb.souq.model.Favorite;
import com.marwaeltayeb.souq.model.History;
import com.marwaeltayeb.souq.model.Product;
import com.marwaeltayeb.souq.storage.LoginUtils;
import com.marwaeltayeb.souq.utils.RequestCallback;

import java.text.DecimalFormat;

import static com.marwaeltayeb.souq.utils.Constant.LOCALHOST;
import static com.marwaeltayeb.souq.utils.Utils.shareProduct;

public class ProductAdapter extends PagedListAdapter<Product, ProductAdapter.ProductViewHolder> {

    private Context mContext;
    public static Product product;
    private AddFavoriteViewModel addFavoriteViewModel;
    private RemoveFavoriteViewModel removeFavoriteViewModel;
    private ToCartViewModel toCartViewModel;
    private FromCartViewModel fromCartViewModel;
    private ToHistoryViewModel toHistoryViewModel;

    // Create a final private MovieAdapterOnClickHandler called mClickHandler
    private ProductAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface ProductAdapterOnClickHandler {
        void onClick(Product product);
    }

    public ProductAdapter(Context mContext, ProductAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
        addFavoriteViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(AddFavoriteViewModel.class);
        removeFavoriteViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(RemoveFavoriteViewModel.class);
        toCartViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(ToCartViewModel.class);
        fromCartViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(FromCartViewModel.class);
        toHistoryViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(ToHistoryViewModel.class);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductListItemBinding productListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_item, parent, false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        product = getItem(position);

        if (product != null) {
            String productName = product.getProductName();
            holder.binding.txtProductName.setText(productName);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(product.getProductPrice());
            holder.binding.txtProductPrice.setText(formattedPrice + " EGP");

            // Load the Product image into ImageView
            String imageUrl = LOCALHOST + product.getProductImage().replaceAll("\\\\", "/");
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.binding.imgProductImage);

            Log.d("imageUrl", imageUrl);

            holder.binding.imgShare.setOnClickListener(v -> shareProduct(mContext, productName, imageUrl));

            // If product is inserted
            if (product.isFavourite() == 1) {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
            }

            // If product is added to cart
            if (product.isInCart() == 1) {
                holder.binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
            }

        } else {
            Toast.makeText(mContext, "Product is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public PagedList<Product> getCurrentList() {
        return super.getCurrentList();
    }

    public Product getProductAt(int position) {
        return getItem(position);
    }

    public void notifyOnInsertedItem(int position) {
        notifyItemInserted(position);
        notifyItemRangeInserted(position, getCurrentList().size()-1);
        notifyDataSetChanged();
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

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Create view instances
        private final ProductListItemBinding binding;

        private ProductViewHolder(ProductListItemBinding binding) {
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
            product = getItem(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(product);
                    insertProductToHistory();
                    break;
                case R.id.imgFavourite:
                    toggleFavourite();
                    break;
                case R.id.imgCart:
                    toggleProductsInCart();
                    break;
            }
        }

        private void toggleFavourite() {
            // If favorite is not bookmarked
            if (product.isFavourite() != 1) {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_pink);
                insertFavoriteProduct(() -> {
                    product.setIsFavourite(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Added");
            } else {
                binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border);
                deleteFavoriteProduct(() -> {
                    product.setIsFavourite(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Bookmark Removed");
            }
        }

        private void toggleProductsInCart() {
            // If Product is not added to cart
            if (product.isInCart() != 1) {
                binding.imgCart.setImageResource(R.drawable.ic_shopping_cart_green);
                insertToCart(() -> {
                    product.setIsInCart(true);
                    notifyDataSetChanged();
                });
                showSnackBar("Added To Cart");
            } else {
                binding.imgCart.setImageResource(R.drawable.ic_add_shopping_cart);
                deleteFromCart(() -> {
                    product.setIsInCart(false);
                    notifyDataSetChanged();
                });
                showSnackBar("Removed From Cart");
            }
        }

        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }

        private void insertFavoriteProduct(RequestCallback callback) {
            Favorite favorite = new Favorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId());
            addFavoriteViewModel.addFavorite(favorite,callback);
        }

        private void deleteFavoriteProduct(RequestCallback callback) {
            removeFavoriteViewModel.removeFavorite(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId(),callback);
        }

        private void insertToCart(RequestCallback callback) {
            Cart cart = new Cart(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId());
            toCartViewModel.addToCart(cart, callback);
        }

        private void deleteFromCart(RequestCallback callback) {
            fromCartViewModel.removeFromCart(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId(),callback);
        }

        private void insertProductToHistory() {
            History history = new History(LoginUtils.getInstance(mContext).getUserInfo().getId(), product.getProductId());
            toHistoryViewModel.addToHistory(history);
        }
    }


}