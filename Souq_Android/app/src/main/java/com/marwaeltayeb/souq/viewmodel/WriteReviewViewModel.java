package com.marwaeltayeb.souq.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.souq.model.Review;
import com.marwaeltayeb.souq.repository.WriteReviewRepository;

import okhttp3.ResponseBody;

public class WriteReviewViewModel extends ViewModel {

    private final WriteReviewRepository writeReviewRepository;

    public WriteReviewViewModel() {
        writeReviewRepository = new WriteReviewRepository();
    }

    public LiveData<ResponseBody> writeReview(Review review) {
        return writeReviewRepository.writeReview(review);
    }
}
