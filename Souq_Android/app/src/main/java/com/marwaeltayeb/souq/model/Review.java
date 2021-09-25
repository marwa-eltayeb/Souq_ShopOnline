package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;
    @SerializedName("name")
    private String userName;
    @SerializedName("date")
    private String reviewDate;
    @SerializedName("rate")
    private float reviewRate;
    @SerializedName("feedback")
    private String feedback;

    public Review(int userId, int productId, float reviewRate, String feedback) {
        this.userId = userId;
        this.productId = productId;
        this.reviewRate = reviewRate;
        this.feedback = feedback;
    }

    public String getUserName() {
        return userName;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public float getReviewRate() {
        return reviewRate;
    }

    public String getFeedback() {
        return feedback;
    }

}
