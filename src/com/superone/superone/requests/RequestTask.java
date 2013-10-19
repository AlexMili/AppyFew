package com.superone.superone.requests;


import android.os.AsyncTask;
import android.util.Log;

import com.superone.superone.listeners.RequestTaskListener;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by remirocaries on 02/06/13.
 */

public class RequestTask extends AsyncTask<String, String, String> {


    private static int requestNumber = 123;

    private RequestTaskListener listener;
    private String url;
    private String postData;
    private byte[] img;
    private int typeRequest = -1;
    public int requestId;

    public RequestTask(String url, String postData, RequestTaskListener l)
    {
        super();
        this.typeRequest = 1;
        if (postData != null)
            this.postData = postData;
        if (url != null)
            this.url = url;
        if (l != null)
            this.listener = l;
        requestId = requestNumber++;
    }

    public RequestTask(String url, String postData, RequestTaskListener l, byte[] img)
    {
        super();
        this.typeRequest = 2;
        if (postData != null)
            this.postData = postData;
        if (url != null)
            this.url = url;
        if (l != null)
            this.listener = l;
        if (img != null)
            this.img = img;
        requestId = requestNumber++;
    }

    public void startRequest()
    {
        this.execute(this.url, this.postData);
    }

    @Override
    protected String doInBackground(String... uri)
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = null;

        HttpResponse response;

        String responseString = null;

        try
        {
            if (postData != null)
            {
                httpPost = new HttpPost(url);
                StringEntity se = new StringEntity(postData);
                se.setContentType("application/x-www-form-urlencoded; charset=utf-8");
                httpPost.setEntity(se);
                response = httpclient.execute(httpPost);
            }
            else
            {
                response = httpclient.execute(new HttpGet(uri[0]));
            }

            StatusLine statusLine = response.getStatusLine();

            if(statusLine.getStatusCode() == HttpStatus.SC_OK)
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                response.getEntity().writeTo(out);

                out.close();

                responseString = out.toString();
            }
            else
            {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
            return null;
        }
        return responseString;
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        System.out.print(result);
        listener.onRequestComplete(result, this);
    }
}
