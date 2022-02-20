package com.marwaeltayeb.souq.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.databinding.ReviewListItemBinding;
import com.marwaeltayeb.souq.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
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

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        // Create view instances
        private final ReviewListItemBinding binding;

        private ReviewViewHolder(ReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
