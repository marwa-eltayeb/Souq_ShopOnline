package com.marwaeltayeb.souq.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageUtils {

    private LanguageUtils(){}

    public static void setEnglishState(Context context, boolean isEnglishEnabled){
        SharedPreferences sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("language", isEnglishEnabled);
        editor.apply();
    }

    public static boolean getEnglishState(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("language", true);
    }

    public static void setLocale(Context context,String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        // Save data to shared preferences
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang",language);
        editor.apply();
    }

    // Load language saved in shared preferences
    public static void loadLocale(Context context){
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang","");
        setLocale(context,language);
    }
}
