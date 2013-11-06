package com.happyfew.happyfew.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created by alexandre on 19/10/13.
 */
public class IdUtils {

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getImei(TelephonyManager telephonyManager) {
        return telephonyManager.getDeviceId();
    }

    public static String getImsi(TelephonyManager telephonyManager) {
        return telephonyManager.getSubscriberId();
    }

    public static String getSimSerial(TelephonyManager telephonyManager) {
        return telephonyManager.getSimSerialNumber();
    }

    public static String getSerialNumber() throws Exception{
        Class<?> c = Class.forName("android.os.SystemProperties");
        Method get = c.getMethod("get", String.class, String.class );
        return (String)(get.invoke(c, "ro.serialno", "unknown" )  );
    }

    public static String getMacAdress(WifiManager wifiManager) {
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }
}
