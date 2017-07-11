package com.dlovan.fullmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.dlovan.fullmovie.R;

import java.util.Locale;

/**
 * utils class
 * Created by dlovan on 7/10/17.
 */
public class Utils {

    /**
     * for checking internet
     *
     * @param context is context for any activity
     * @return true if internet is available or false is not
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * for known is device in landscape mode or portrait mode
     * and is device is tablet or phone
     *
     * @param context is context for any activity
     * @return 3 landscape and tablet or 2 portrait
     */
    public static int getNumOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        int numOfColumns;
        if (dpWidth < dpHeight) {
            // portrait mode
            numOfColumns = 2;
            if (dpWidth >= 600) { // for tablet sw600
                numOfColumns = 3;
            }
        } else {
            // landscape mode
            numOfColumns = 3;
        }
        return numOfColumns;
    }


    /**
     * Helper method to set app language manually.
     *
     * @param context Context used to get app resources.
     */
    public static void setLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = preferences.getString(
                context.getString(R.string.settings_language_key),
                context.getString(R.string.settings_english_value));

        Locale locale;
        if (language.equals(context.getString(R.string.settings_kurdish_value))) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                locale = new Locale("ar", "EG");
            } else {
                locale = new Locale("ckb");
            }

        } else {
            locale = new Locale(language);
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
