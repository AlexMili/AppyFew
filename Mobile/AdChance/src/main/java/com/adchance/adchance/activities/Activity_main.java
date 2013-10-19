package com.adchance.adchance.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.provider.Settings.Secure;

import com.adchance.adchance.R;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Activity_main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String android_id = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);

        Log.d("ADCHANCE", "ANDROID ID : " + android_id);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);

        Log.d("ADCHANCE","IMEI : "+telephonyManager.getDeviceId());
        Log.d("ADCHANCE","IMSI : "+telephonyManager.getSubscriberId());
        Log.d("ADCHANCE","SIM SERIAL : "+telephonyManager.getSimSerialNumber());
        Log.d("ADCHANCE", "PHONE TYPE : " + telephonyManager.getPhoneType());

        String serialnum = null;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            serialnum = (String)(   get.invoke(c, "ro.serialno", "unknown" )  );
        }
        catch (Exception ignored) { }

        Log.d("ADCHANCE","SERIAL NUMBER : "+serialnum);

        String macAddress=null;

        WifiManager wifiManager =
                ( WifiManager ) getSystemService(getApplicationContext().WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();

        if (macAddress != null)
            macAddress = macAddress;

        Log.d("ADCHANCE","MAC : "+macAddress);

        UUID deviceUuid = new UUID(android_id.hashCode(), ((long)telephonyManager.getDeviceId().hashCode() << 32) | telephonyManager.getSimSerialNumber().hashCode());
        String deviceId = deviceUuid.toString();

        Log.d("ADCHANCE","UUID : "+deviceId);

        PackageManager packageManager = getPackageManager();
        Collection<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        Iterator it = list.iterator();

        while(it.hasNext()) {
            ApplicationInfo info = (ApplicationInfo) it.next();

            if(info != null)
                Log.d("ADCHANCE", ""+info.packageName);
            else
                Log.d("ADCHANCE", "NO APP");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
