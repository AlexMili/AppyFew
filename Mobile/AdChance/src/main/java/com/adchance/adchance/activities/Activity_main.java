package com.adchance.adchance.activities;

import android.annotation.TargetApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;

import com.adchance.adchance.R;
import com.adchance.adchance.async.GetRequest;
import com.adchance.adchance.async.PostRequest;
import com.adchance.adchance.json.UserData;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class Activity_main extends Activity {

    private String jsonFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AdChance/user.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GetRequest get = new GetRequest();
        get.execute(new String[] { "http://www.google.com/" });

        PostRequest post = new PostRequest();
        post.execute(new String[] { "http://www.htmlcodetutorial.com/cgi-bin/mycgi.pl" });


        /*JsonFactory jsonFactory = new JsonFactory();
        JsonParser jp=null;

        try {
            jp = jsonFactory.createJsonParser(new File(jsonFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

        mapper.writeValue(new File(jsonFilePath), new UserData());

        UserData user = null;

        user = mapper.readValue(new File(jsonFilePath), UserData.class);

        Log.d("ADCHANCE", "INFO : "+user.name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
