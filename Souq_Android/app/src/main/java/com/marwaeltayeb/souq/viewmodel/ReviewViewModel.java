package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.ReviewApiResponse;
import com.marwaeltayeb.souq.repository.ReviewRepository;

public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;

    public ReviewViewModel(  ) {
        reviewRepository = new ReviewRepository();
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        return reviewRepository.getReviews(productId);
    }
}
