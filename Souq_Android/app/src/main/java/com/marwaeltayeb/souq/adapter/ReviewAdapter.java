package com.marwaeltayeb.souq.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ReviewListItemBinding;
import com.marwaeltayeb.souq.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context mContext;
    private List<Review> reviewList;

    public ReviewAdapter(Context mContext, List<Review> reviewList) {
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ReviewListItemBinding reviewListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.review_list_item,parent,false);
        return new ReviewViewHolder(reviewListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = reviewList.get(position);
        holder.binding.userName.setText(currentReview.getUserName());
        holder.binding.dateOfReview.setText(currentReview.getReviewDate());
        holder.binding.rateProduct.setRating(currentReview.getReviewRate());
        holder.binding.userFeedback.setText(currentReview.getFeedback());
    }

    @Override
    public int getItemCount() {
        if (reviewList == null) {
            return 0;
        }
        return reviewList.size();
    }

    public void notifyOnInsertedItem(){
        notifyItemInserted(reviewList.size() - 1);
        notifyItemRangeInserted(reviewList.size() - 1,reviewList.size());
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        // Create view instances
        private final ReviewListItemBinding binding;

        private ReviewViewHolder(ReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
