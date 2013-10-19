package com.adchance.adchance.activities;

import android.annotation.TargetApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.provider.Settings.Secure;

import com.adchance.adchance.R;
import com.adchance.adchance.async.GetRequest;
import com.adchance.adchance.utils.RequestUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        /*HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://www.vogella.com");
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

// Get the response
        BufferedReader rd = null;
        try {
            rd = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                Log.d("ADCHANCE", line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream test = getInputStreamFromUrl("http://www.google.fr/");
        StringBuilder tras = null;*/

        GetRequest get = new GetRequest();
        get.execute(new String[] { "http://www.google.com/" });

        /*try { tras = RequestUtils.inputStreamToString(test); }
        catch (IOException e) { e.printStackTrace(); }

        Log.d("ADCHANCE", "GET REQUEST : " +tras.toString());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
