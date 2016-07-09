package com.mobile.bcahlic.zhln.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bcahlic on 16-7-4.
 */
public class Sputils {
    public static final String PREF_NAME="config";
    public static boolean getBoolean(Context ctx,String key,boolean defaut){
        SharedPreferences preferences = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        return preferences.getBoolean(key,defaut);
    }
    public static void setBoolean(Context ctx,String key,boolean defaut){
        SharedPreferences preferences = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        preferences.edit().putBoolean(key,defaut).commit();
    }
}
