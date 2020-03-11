package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    private int userId;
    private int productId;
    @SerializedName("name")
    private String userName;
    @SerializedName("date")
    private String reviewDate;
    @SerializedName("rate")
    private int reviewRate;
    @SerializedName("feedback")
    private String feedback;
    @SerializedName("averageRate")
    private int averageRate;

    public Review(int userId, int productId, int reviewRate, String feedback) {
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

    public int getReviewRate() {
        return reviewRate;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getAverageRate() {
        return averageRate;
    }
}
