package com.dlovan.fullmovie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

/**
 * Created by dlovan on 7/10/17.
 */

public class Utils {

    private Utils() {
    }

    public static boolean isInternetAvailabe(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

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
}
