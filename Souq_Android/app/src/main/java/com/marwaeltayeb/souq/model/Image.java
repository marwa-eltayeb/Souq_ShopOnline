package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Image {

    private boolean error;
    private String message;
    @SerializedName("image")
    private String imagePath;

    public String getMessage() {
        return message;
    }

    public String getImagePath() {
        return imagePath;
    }
}
