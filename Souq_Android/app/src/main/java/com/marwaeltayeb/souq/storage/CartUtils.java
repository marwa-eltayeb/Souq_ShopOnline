package com.marwaeltayeb.souq.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class CartUtils {
    public static void setCartState(Context context, String userId,int productId , boolean isAddToCart){
        SharedPreferences sharedpreferences = context.getSharedPreferences("cart_data_"+ userId, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(String.valueOf(productId), isAddToCart);
        editor.apply();
    }

    public static boolean getCartState(Context context, String userId, int productId){
        SharedPreferences sharedpreferences = context.getSharedPreferences("cart_data_"+ userId, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(String.valueOf(productId), false);
    }

    public static void clearSharedPreferences(Context context){
        context.getSharedPreferences("cart_data", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
