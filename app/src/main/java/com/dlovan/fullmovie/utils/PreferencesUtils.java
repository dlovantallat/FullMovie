package com.dlovan.fullmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dlovan on 7/12/17.
 */

public class PreferencesUtils {

    private static volatile PreferencesUtils instance;
    private SharedPreferences mPref;

    private PreferencesUtils(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (PreferencesUtils.class) {
                if (instance == null) {
                    instance = new PreferencesUtils(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    synchronized public String setLanguage(String key, String defaultValue) {
        return mPref.getString(key, defaultValue);
    }
}
