package com.marwaeltayeb.souq.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.souq.model.Review;
import com.marwaeltayeb.souq.repository.WriteReviewRepository;

import okhttp3.ResponseBody;

public class WriteReviewViewModel extends AndroidViewModel {

    private WriteReviewRepository writeReviewRepository;

    public WriteReviewViewModel(@NonNull Application application) {
        super(application);
        writeReviewRepository = new WriteReviewRepository(application);
    }

    public LiveData<ResponseBody> writeReview(Review review) {
        return writeReviewRepository.writeReview(review);
    }
}
