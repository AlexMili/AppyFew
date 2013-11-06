/**
 * @author alexandre
 * @since 23/09/13
 */
package com.happyfew.happyfew;

/* ANDROID IMPORT */
import android.os.Environment;

import java.util.ArrayList;
import java.util.HashMap;

import static android.graphics.Color.parseColor;

/* LOCAL IMPORT */


/* STANDARD IMPORT*/


/**
 * Define globals of the superoe.
 */
public interface AppConfiguration {
    public final static String rootDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/HappyFew/";
    public final static String jsonFilePath = rootDirectory+"user.json";
    public final static String debugTag = "HAPPYFEW";
    public final static int SPLASHSCREEN_DURATION = 2;
    public final static String test = Environment.getExternalStorageDirectory().getAbsolutePath()+"/GrowUpvideos/output.mp4";
    public final static String[] endingTime = {"Fin dans 5h28m", "Fin dans 8h45m", "Fin dans 1 jour", "Fin dans 3h", "Fin dans 7h8m"};
    public final static String[] cat = {"High Tech", "Maison et Jardin", "Voyage", "Spectacle", "Mode et Accessoires"};
    public final static int[] bans = {R.raw.highteck, R.raw.maison, R.raw.voyage, R.raw.spectacle, R.raw.mode};
    public final static int[] colors = {parseColor("#ffff7b74"), parseColor("#51a351"), parseColor("#ffefaa3c"), parseColor("#ffce6aac"), parseColor("#2bc2bb")};
    public final static int COUNT = 5;
    public final static String serverURL = "http://192.168.230.33:9000/adchance/user";
    public final static ArrayList tags = new ArrayList<String>() {{
        add("mode");
        add("garden");
        add("tech");
        add("travel");
        add("show");
    }};
}










































