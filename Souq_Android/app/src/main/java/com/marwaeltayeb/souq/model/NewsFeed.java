package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class NewsFeed {

    @SerializedName("poster_id")
    private int posterId;

    @SerializedName("image")
    private String image;

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
