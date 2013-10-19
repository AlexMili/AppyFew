/**
 * @author alexandre
 * @since 23/09/13
 */
package com.superone.superone;

/* ANDROID IMPORT */
import android.os.Environment;

/* LOCAL IMPORT */


/* STANDARD IMPORT*/


/**
 * Define globals of the superone.
 */
public interface AppConfiguration {
    public final static String rootDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/GrowUpvideos/";
    public final static String tempFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp";
    public final static String exportDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Super1/";
    public final static String debugTag = "SUPER1";
    public final static String suffixVideoRecorded = "_record.mp4";
    public final static String suffixVideoFinal = "_full.mp4";
    public final static String[] samplesNames = {"None", "Disco Guitar", "Peaceful Piano", "Violin", "Rhythmic Piano", "Wake Me Up"};
    public final static String[] samplesFiles = {"", "disco.wav", "calm_piano.wav", "strings100.wav", "dynamic_piano.wav", "wakemeup.wav"};
    public final static int[] samplesIds = {0, R.raw.disco, R.raw.calm_piano, R.raw.strings100, R.raw.dynamic_piano, R.raw.wakemeup};
    public final static int VIDEO_DURATION = 1;
    public final static int SPLASHSCREEN_DURATION = 2;
}
