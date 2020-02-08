package com.marwaeltayeb.souq.utils;

import android.content.Context;
import android.content.Intent;

public class CommunicationsUtils {

    public static void shareApp(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.android.chrome&fbclid=IwAR2OYOl_XZ9k7AP68xhNjEZnL1OXWiiWucNT1QsPTthr-IHr-5G1_0AH1AA");
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
