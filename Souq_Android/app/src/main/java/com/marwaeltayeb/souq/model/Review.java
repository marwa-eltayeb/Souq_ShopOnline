package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("name")
    private String userName;
    @SerializedName("date")
    private String reviewDate;
    @SerializedName("rate")
    private int reviewRate;
    @SerializedName("feedback")
    private String feedback;

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
}
