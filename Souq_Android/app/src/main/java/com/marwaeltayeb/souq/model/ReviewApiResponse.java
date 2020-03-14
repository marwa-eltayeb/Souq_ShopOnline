package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewApiResponse {

    @SerializedName("avrg_review")
    private float averageReview;

    @SerializedName("review")
    private List<Review> reviewList;

    public float getAverageReview() {
        return averageReview;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

}
