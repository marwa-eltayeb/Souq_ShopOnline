package com.marwaeltayeb.souq.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguageUtils {

    public static void setEnglishState(Context context, boolean isEnglishEnabled){
        SharedPreferences sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("language", isEnglishEnabled);
        editor.apply();
    }

    public static boolean getEnglishState(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("language", false);
    }

}
