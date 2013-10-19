/**
 * @author
 * @since
 */
package com.superone.superone.activities;

/* ANDROID IMPORT */
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.VideoView;
import android.widget.MediaController;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.R;

import java.io.File;

/* STANDARD IMPORT */

/**
 * Play a video.
 */
public class Activity_player extends Activity implements MediaPlayer.OnCompletionListener, AppConfiguration {

    private String videoPath;
    private int sampleIndex=0;
    private String videoToPlay = "";
    private String url="";
    private boolean preview=false;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VideoView videoView;
        MediaController mediaCtrl;

        //TODO bug on some device (Galaxy Tab 1 for example), where getActionBar is null, and the action bar show
        if(getActionBar() != null)
            getActionBar().hide(); //.setDisplayHomeAsUpEnabled(true); //enable back button on the action bar

        setContentView(R.layout.old_activity_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoPath = getIntent().getExtras().getString("Filename");
        sampleIndex = getIntent().getExtras().getInt("Sample");
        preview = getIntent().getExtras().getBoolean("Preview");
        videoToPlay = rootDirectory+videoPath;

        Log.d(debugTag, "Playing file : " +videoToPlay);
        Log.d(debugTag, "Mute : "+sampleIndex);

        if(sampleIndex == 0)
            url = videoToPlay+".wav"; // your URL here
        /*else {
            url = samplesDirectory+samplesFiles[sampleIndex];
        }*/

        videoView = (VideoView) findViewById(R.id.VideoView);

        if(!preview) {

            if(sampleIndex != 0) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), samplesIds[sampleIndex]);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setLooping(true);
            }
            else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                Log.d(debugTag, "MUSIC:"+url);

                new File(url).setReadable(true, false);

                try { mediaPlayer.setDataSource(url); }
                catch (Exception e) { e.printStackTrace(); }
            }

            try { mediaPlayer.prepare(); } // might take long! (for buffering, etc)
            catch (Exception e) { e.printStackTrace(); }

            mediaCtrl = new MediaController(this);
            mediaCtrl.setAnchorView(videoView);
            mediaCtrl.setMediaPlayer(videoView);
            videoView.setMediaController(mediaCtrl);
            videoView.setOnPreparedListener(PreparedListener);
        }


        videoView.setOnCompletionListener(this);
        videoView.setVideoPath(videoToPlay);
        videoView.start();
        videoView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_player, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(debugTag, "AppConfiguration changed");
    }

    public void onCompletion(MediaPlayer mp) {
        this.finish();

        if(!preview) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!preview && mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener(){

        @Override
        public void onPrepared(MediaPlayer m) {
            try { mediaPlayer.start(); }
            catch (Exception e) { e.printStackTrace(); }
        }
    };
}
