/**
 * @author BadMojo
 * @since 26/09/13
 */
package com.happyfew.happyfew;

import android.app.Application;
import android.content.Context;

/**
 * Main Class App
 */
public class HappyFew extends Application {

    public static Context CONTEXT = null;

    @Override
    public void onCreate() {
        CONTEXT = getApplicationContext();
    }

//    @Override
//    public void onTerminate()
//    {
//        WHEN APP SHUTDOWN
//    }
}
