package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsFeedResponse {

    @SerializedName("posters")
    private List<NewsFeed> posters;

    public List<NewsFeed> getPosters() {
        return posters;
    }
}
