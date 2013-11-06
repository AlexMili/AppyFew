package com.happyfew.happyfew.utils;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alexandre on 19/10/13.
 */
public class RequestUtils {

    public static InputStream getInputStreamFromUrl(String url) {
        InputStream content = null;
        HttpClient httpclient = null;
        HttpResponse response = null;

        try { httpclient = new DefaultHttpClient(); }
        catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - DEFAULT", e); }

        try { response = httpclient.execute(new HttpGet(url)); }
        catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - EXECUTE", e); }

        try { content = response.getEntity().getContent(); }
        catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - ENTITY", e); }


        return content;
    }

    public static StringBuilder inputStreamToString(InputStream is) throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        while ((line = rd.readLine()) != null) {
            total.append(line);
        }

        // Return full string
        return total;
    }

}
