/**
 * @author BadMojo, alexandre
 * @since 21/09/13
 */
package com.superone.superone.activities.fragments;

/* ANDROID IMPORT */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.activities.Activity_addRecord;
import com.superone.superone.activities.Activity_player;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.R;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */
import java.util.ArrayList;

/**
 * Main fragment.
 */
public class Fragment_info extends Fragment implements AppConfiguration{

    private int indexCurrentTimelapse;

    private Button btnPlay;
    private ImageButton btnRecord;

    private DatabaseHelperTimelapse db;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;

    public Fragment_info(DatabaseHelperTimelapse database) {
        super();
        db = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        btnPlay = (Button) v.findViewById(R.id.btn_play);
        btnRecord = (ImageButton) v.findViewById(R.id.btn_record);

        return v;
    }

    @Override
    public void onResume()	{
        super.onResume();

        btnRecord.setOnClickListener(recordListener);
        btnPlay.setOnClickListener(playListener);
        //View v = inflater.inflate(R.layout.fragment_info, container, false);
        //super.onResume();
        //DatabaseHelperTimelapse db = new DatabaseHelperTimelapse(getActivity().getApplicationContext());
        //db.getWritableDatabase();
        timelapseList = db.getTimelapse();
        indexCurrentTimelapse = getActivity().getIntent().getExtras().getInt("ModelPosition");
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);
        Log.d(debugTag, "Timelapse position : " + indexCurrentTimelapse);
        //Log.d("GrowUp", "Timelapse position : " + position + ", name : " + model.getName());
        //getView().findViewById(R.id.btn_play).setVisibility(model.getMainUrl().equals("") ? View.INVISIBLE : View.VISIBLE);
        //db.close();

//        VideoView video = (VideoView) getView().findViewById(R.id.videoView);
//        Uri chemin = Uri.parse(AppConfiguration.rootDirectory+currentTimelapse.getMainUrl());
//        video.setVideoURI(chemin);
//        video.start();

        getActivity().findViewById(R.id.btn_play).setVisibility(currentTimelapse.getMainUrl().equals("") ? View.INVISIBLE : View.VISIBLE);
    }

    private View.OnClickListener playListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timelapseList = db.getTimelapse();
            currentTimelapse = timelapseList.get(indexCurrentTimelapse);
            Log.d(debugTag, "Starting playback activity from Timelapse...");
            Log.d(debugTag, "slave : " + currentTimelapse.getSlaveUrl());
            Log.d(debugTag, "Playing : "+currentTimelapse.getMainUrl());

            if(currentTimelapse.getSoundStatus().equals("1"))
                Log.d(debugTag,"SOUND MUST BE ON - "+currentTimelapse.getSoundStatus());
            else
                Log.d(debugTag,"SOUND MUST BE OFF - "+currentTimelapse.getSoundStatus());

            if(currentTimelapse.getSlaveUrl().equals("")) {
                Intent intent = new Intent(getActivity(), Activity_player.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Filename", currentTimelapse.getMainUrl());

                if(currentTimelapse.getSample().equals("0"))
                    intent.putExtra("Sample", 0);
                else
                    intent.putExtra("Sample", Integer.parseInt(currentTimelapse.getSample()));

                intent.putExtra("Preview", false);
                startActivity(intent);
            }
            else
                Toast.makeText(getActivity(), "Encoding, please wait ...", Toast.LENGTH_LONG).show();

            Log.d(debugTag, "Playback running from Timelapse");
        }
    };

    private View.OnClickListener recordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            timelapseList = db.getTimelapse();
            currentTimelapse = timelapseList.get(indexCurrentTimelapse);

            btnRecord.setOnClickListener(null);
            Log.d(debugTag, "CLICK RECORDER");
            Log.d(debugTag, "NAME : " +currentTimelapse.getName()+ " - MAIN : "+currentTimelapse.getMainUrl());

            if(currentTimelapse.getSlaveUrl().equals("")) {
                Intent intent = new Intent(getActivity(), Activity_addRecord.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Filename", currentTimelapse.getName()+suffixVideoRecorded);
                intent.putExtra("FullVideo", currentTimelapse.getMainUrl().equals("") ? currentTimelapse.getName() + suffixVideoFinal : currentTimelapse.getMainUrl());
                intent.putExtra("Empty", currentTimelapse.getMainUrl().equals(""));
                intent.putExtra("ModelPosition", indexCurrentTimelapse);
                startActivity(intent);
            }
            else
                Toast.makeText(getActivity(), "Encoding, please wait ...", Toast.LENGTH_LONG).show();
        }
    };
}
