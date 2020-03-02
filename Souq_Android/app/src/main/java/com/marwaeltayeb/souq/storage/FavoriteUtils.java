package com.marwaeltayeb.souq.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class FavoriteUtils {

    public static void setFavoriteState(Context context, int productId , boolean isFavourite){
        SharedPreferences sharedpreferences = context.getSharedPreferences("favorite_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(String.valueOf(productId), isFavourite);
        editor.apply();
    }

    public static boolean getFavoriteState(Context context, int productId){
        SharedPreferences sharedpreferences = context.getSharedPreferences("favorite_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(String.valueOf(productId), false);
    }

    public static void clearSharedPreferences(Context context){
        context.getSharedPreferences("favorite_data", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
