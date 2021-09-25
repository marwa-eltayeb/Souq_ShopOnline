package com.marwaeltayeb.souq.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CommunicateUtils {

    private CommunicateUtils(){}

    public static void shareApp(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.android.chrome&fbclid=IwAR2OYOl_XZ9k7AP68xhNjEZnL1OXWiiWucNT1QsPTthr-IHr-5G1_0AH1AA");
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


    public static void rateAppOnGooglePlay(Context context) {
        Uri uri = Uri.parse("market://details?id=" + "com.android.chrome");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome")));
        }
    }
}
