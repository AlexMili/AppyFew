/**
 * @author
 * @since
 */
package com.superone.superone;

/* ANDROID IMPORT */
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

/* LOCAL IMPORT */

/* STANDARD IMPORT */
import java.io.File;
import java.io.IOException;

/**
 * Handle the full recording process.
 */
public class old_Activity_recorder extends Activity implements AppConfiguration {

    private String videoPath = "";

    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(debugTag, "RECORDER STARTED");
        Log.d(debugTag, "Creating recorder activity ... - " +videoPath);
        // Show the Up button in the action bar.

        //Crash the superone
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //TODO bug on some device (Galaxy Tab 1 for example), where getActionBar is null, and the action bar show
        if(getActionBar() != null)
            getActionBar().hide(); //.setDisplayHomeAsUpEnabled(true); //enable back button on the action bar

        videoPath = rootDirectory + getIntent().getExtras().getString("Filename");
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Log.d(debugTag, "Saving to : " + videoPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoPath);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("android.intent.extra.durationLimit", 2);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // old_Video_DONT_DELETE captured and saved to fileUri specified in the Intent
                String recordingPath = data.getData().getPath();
                if(videoPath != recordingPath) {
                    Log.d(debugTag, "moving file to : " + videoPath + ", recordedTo : " + recordingPath);
                    try {
                        File origin = new File(getPath(data.getData()));
                        File destination = new File(videoPath);
                        destination.createNewFile();
                        destination.setWritable(true, false);
                        AppUtils.copy(origin, destination);
                        origin.delete();
                    }
                    catch (IOException e) { e.printStackTrace(); }
                }
                Toast.makeText(this, "old_Video_DONT_DELETE saved to:\n" +videoPath, Toast.LENGTH_LONG).show();
                Log.d(debugTag, "Really saved to : " + data.getData());
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "old_Video_DONT_DELETE recording cancelled :\n", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "old_Video_DONT_DELETE recording failed :\n" +data.getData(), Toast.LENGTH_LONG).show();
            }
            this.finish();
        }
    }
    public String getPath(Uri uri) {
        /*String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;*/
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)  {
        super.onConfigurationChanged(newConfig);
        Log.d(debugTag, "AppConfiguration changed in old_Activity_recorder");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_recorder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
