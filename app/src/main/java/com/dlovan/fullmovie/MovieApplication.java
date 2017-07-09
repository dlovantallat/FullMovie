package com.dlovan.fullmovie;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by dlovan on 7/9/17.
 */

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }
    }
}
