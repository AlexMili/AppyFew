/**
 * @author
 * @since
 */
package com.superone.superone.activities;

/* ANDROID IMPORT */
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.AppUtils;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.R;
import com.superone.superone.listeners.ListenerVideo;
import com.superone.superone.models.TimelapseModel;
import com.superone.superone.threads.ConcatVideo;
import com.superone.superone.threads.SplitVideo;
import com.superone.superone.threads.ContainerVideo;

/* STANDARD IMPORT */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.ffmpeg.android.FfmpegController;

public class Activity_addRecord extends Activity implements AppConfiguration {

	private String videoRecordName;
    private String videoRecordPath;
    private File videoRecordFile;
    private String videoFinalName;
    private String videoFinalPath;
	private int indexCurrentTimelapse;
	private boolean launchedRecord;
    private final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private TextView title;
    private TextView date;

    private DatabaseHelperTimelapse db;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(debugTag, "creating Activity_addRecord");

        //Database initialization
        db = new DatabaseHelperTimelapse(getApplicationContext());
        db.getWritableDatabase();

		launchedRecord = false;

		setContentView(R.layout.activity_sequence_options);
		getActionBar().hide();
		
		title = (TextView) findViewById(R.id.textTitle);
		date = (TextView) findViewById(R.id.textDate);

	    /*videoRecordName = getIntent().getExtras().getString("Filename");
	    File file = new File(rootDirectory + videoRecordName);

	    if(!file.exists()) {
	    	this.reShoot(null);
	    }
	    else {
	    	Toast.makeText(this, "The previous video was recovered", Toast.LENGTH_LONG).show();
	    }*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sequence_options, menu);
		return true;
	}	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
		Log.d(debugTag, "AppConfiguration changed in SequenceOptions");
    }
	
	@Override
	public void onResume() {
		super.onResume();

        timelapseList = db.getTimelapse();
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);
        indexCurrentTimelapse = getIntent().getIntExtra("ModelPosition", 0);

        title.setText(currentTimelapse.getName());
        date.setText(currentTimelapse.getDate());

        videoRecordName = getIntent().getExtras().getString("Filename");
        videoRecordPath = rootDirectory + videoRecordName;
        videoRecordFile = new File(videoRecordPath);

        videoFinalName = getIntent().getExtras().getString("FullVideo");
        videoFinalPath = rootDirectory + videoFinalName;

		if(launchedRecord && !videoRecordFile.exists()) {
			finish();
		}
        else if(!launchedRecord && !videoRecordFile.exists()){
            startRecorder();
        }
        else if(!launchedRecord && videoRecordFile.exists()) {
            Toast.makeText(this, "Your unsaved video has been recovered", Toast.LENGTH_LONG).show();
        }
	}	

	@Override
	public void onPause() {
		Log.d(debugTag, "OnPause SequenceOptions");
		launchedRecord = true;
		super.onPause();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            cancel(null);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
	
	public void replay(View view) {
		Log.d(debugTag, "Starting playback activity from Options...");
		Intent intent = new Intent(Activity_addRecord.this, Activity_player.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("Filename", videoRecordName);
        intent.putExtra("Preview", true);
        intent.putExtra("Mute", 0);
		startActivity(intent);
	}
	
	public void validate(View view) {
		final DatabaseHelperTimelapse db = this.db;
		//db.getWritableDatabase();
		//ArrayList<TimelapseModel> product_list = db.getTimelapse();
		final TimelapseModel currentTimelapse = this.currentTimelapse;

		Log.d(debugTag, "Validating sequence with id : " + currentTimelapse.id);

		final String videoRecordPath = this.videoRecordPath;
        final String videoFinalPath = this.videoFinalPath;

        final FfmpegController ffmpegCtrl = new FfmpegController(getApplicationContext(),null);

        try { Runtime.getRuntime().exec("chmod 777 " +videoRecordPath); }
        catch (IOException e) { e.printStackTrace(); }

        currentTimelapse.setSlaveUrl(videoRecordName);
        db.updateTimelapse(currentTimelapse);

        if(getIntent().getExtras().getBoolean("Empty")) {

            Thread splitVideo = new SplitVideo(ffmpegCtrl,videoRecordPath, new ListenerVideo() {

                public void onThreadEnd() {
                    Log.d(debugTag, "END SPLIT FIRST");

                    //H264
                    File recordTmpH264 = new File(videoRecordPath+".h264");
                    File destH264 = new File(videoFinalPath+".h264");

                    recordTmpH264.renameTo(destH264);

                    //AAC
                    File recordTmpAac = new File(videoRecordPath+".aac");
                    File destAac = new File(videoFinalPath+".aac");

                    recordTmpAac.renameTo(destAac);

                    /*currentTimelapse.setSlaveUrl("");
                    db.updateTimelapse(currentTimelapse);*/

                    //Recorded
                    new File(videoRecordPath).delete();

                    Thread containerVideo = new ContainerVideo(ffmpegCtrl,videoFinalPath, new ListenerVideo() {

                        public void onThreadEnd() {
                            currentTimelapse.setSlaveUrl("");
                            db.updateTimelapse(currentTimelapse);
                            Log.d(debugTag, "END CONTAINER with " + videoFinalPath + " and " + currentTimelapse.getSlaveUrl());
                        }
                    });
                    containerVideo.start();
                }
            });
            splitVideo.start();

            currentTimelapse.setMainUrl(videoFinalName);
			db.updateTimelapse(currentTimelapse);
		}
		else if(new File(videoRecordPath).exists()) {
            Log.d(debugTag, "STARTING ENCODING");

            Thread splitVideo = new SplitVideo(ffmpegCtrl,videoRecordPath, new ListenerVideo() {

                public void onThreadEnd() {
                    Log.d(debugTag, "END SPLIT SECOND");

                    //Recorded
                    new File(videoRecordPath).delete();

                    Thread concatVideo = new ConcatVideo(ffmpegCtrl,videoRecordPath,videoFinalPath,new ListenerVideo(){

                        @Override
                        public void onThreadEnd() {
                            Log.d(debugTag,"END CONCAT");

                            //H264
                            //new File(videoRecordPath+".h264").delete();

                            //AAC
                            //new File(videoRecordPath+".aac").delete();

                            Thread containerVideo = new ContainerVideo(ffmpegCtrl,videoFinalPath, new ListenerVideo() {

                                public void onThreadEnd() {
                                    currentTimelapse.setSlaveUrl("");
                                    db.updateTimelapse(currentTimelapse);
                                    Log.d(debugTag, "END CONTAINER with "+videoFinalPath+ " and "+ currentTimelapse.getSlaveUrl());
                                }
                            });
                            containerVideo.start();
                        }
                    });
                    concatVideo.start();
                }
            });
            splitVideo.start();
		}
		else {
			Log.e(debugTag, "Error with the recording, no current video");
		}
		finish();
	}
	
	public void startRecorder() {
        if(videoRecordFile.exists())
            videoRecordFile.delete();

		Log.d(debugTag, "Starting recorder with "+videoRecordPath);
		/*Intent intent = new Intent(Activity_addRecord.this, old_Activity_recorder.class);
        //FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
		//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("Filename", videoRecordName);
		//startActivity(intent);*/
        Log.d(debugTag, "Saving to : " + videoRecordPath);

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoRecordPath);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra("android.intent.extra.durationLimit", VIDEO_DURATION);
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(debugTag, "onActivityResult addRecord");
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // old_Video_DONT_DELETE captured and saved to fileUri specified in the Intent
                String recordingPath = data.getData().getPath();
                if(videoRecordPath != recordingPath) {
                    Log.d(debugTag, "moving file to : " + videoRecordPath + ", recordedTo : " + recordingPath);
                    try {
                        File origin = new File(AppUtils.getPath(getApplicationContext(), data.getData()));
                        //File destination = new File(videoRecordPath);
                        videoRecordFile.createNewFile();
                        videoRecordFile.setWritable(true, false);
                        AppUtils.copy(origin, videoRecordFile);
                        origin.delete();
                    }
                    catch (IOException e) { e.printStackTrace(); }
                }
                Toast.makeText(this, "Video saved.", Toast.LENGTH_LONG).show();
                Log.d(debugTag, "Really saved to : " + data.getData());
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Video recording failed :\n" +data.getData(), Toast.LENGTH_LONG).show();
            }
            //this.finish();
        }
    }

	public void cancel(View view) {
		videoRecordFile.delete();
	    finish();
	}
}