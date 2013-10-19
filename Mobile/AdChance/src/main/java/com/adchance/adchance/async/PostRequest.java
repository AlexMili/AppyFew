package com.adchance.adchance.async;

import android.database.CursorJoiner;
import android.os.AsyncTask;
import android.util.Log;

import com.adchance.adchance.utils.RequestUtils;

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
public class PostRequest extends AsyncTask<String, String, String>{

    private InputStream content = null;

    @Override
    protected void onPreExecute() {
        Log.d("ADCHANCE", "BEGIN !!!!-----------------------------------");
    }

    @Override
    protected String doInBackground(String... urls) {
        InputStream content = null;
        HttpClient httpclient = null;
        HttpResponse response = null;


        for (String url : urls) {
            try { httpclient = new DefaultHttpClient(); }
            catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - DEFAULT", e); }

            try { response = httpclient.execute(new HttpGet(url)); }
            catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - EXECUTE", e); }

            try { content = response.getEntity().getContent(); }
            catch (Exception e) { Log.d("[GET REQUEST]", "Network exception - ENTITY", e); }
        }
        StringBuilder total = new StringBuilder();
        String line="";

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(content));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return total.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("ADCHANCE", "OVERRR !!!!-----------------------------------");

        Log.d("ADCHANCE", "HERE : " +result);

        /*StringBuilder tras = null;

        if(result != null) {
            try { tras = RequestUtils.inputStreamToString(result); }
            catch (IOException e) { e.printStackTrace(); }

            Log.d("ADCHANCE", "GET REQUEST : " +tras.toString());
        }*/
    }
}
