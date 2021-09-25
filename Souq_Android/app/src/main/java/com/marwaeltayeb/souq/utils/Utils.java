package com.marwaeltayeb.souq.utils;

import android.content.Context;
import android.content.Intent;

public class Utils {

    private Utils(){}

    public static void shareProduct(Context context,String productName ,String url) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, Check out this amazing item '" + productName +"' with its photo at "+ url);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
