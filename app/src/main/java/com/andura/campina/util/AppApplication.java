package com.andura.campina.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by amaro on 29/04/17.
 */

public class AppApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

    public static Context getContext(){
        return mContext;
    }

}
