package com.superone.superone;

import android.app.Application;
import android.content.Context;

/**
 * Created by BadMojo on 26/09/13.
 */
public class Super1 extends Application {

    public static Context CONTEXT = null;

    @Override
    public void onCreate()
    {
        CONTEXT = getApplicationContext();
    }

//    @Override
//    public void onTerminate()
//    {
//        WHEN APP SHUTDOWN
//    }
}
