/**
 * @author BadMojo, alexandre
 * @since 25/09/13
 */
package com.superone.superone.activities.fragments;

/* ANDROID IMPORT */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.R;
import com.superone.superone.activities.Activity_main;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */
import java.io.File;
import java.util.ArrayList;

public class Fragment_settings extends Fragment implements AppConfiguration{

    private int indexCurrentTimelapse;

    private DatabaseHelperTimelapse db;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;

    Button btnDelete;

    public Fragment_settings(DatabaseHelperTimelapse database) {
        super();
        db = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        btnDelete = (Button) v.findViewById(R.id.btn_delete);

        return v;
    }

    @Override
    public void onResume()	{
        super.onResume();

        timelapseList = db.getTimelapse();
        indexCurrentTimelapse = getActivity().getIntent().getExtras().getInt("ModelPosition");
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);

        btnDelete.setOnClickListener(deleteListener);
    }

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            db.removeTimelapse(String.valueOf(currentTimelapse.getId()));

            String[] listFilesToDelete = {
                    rootDirectory+currentTimelapse.getName()+suffixVideoRecorded,
                    rootDirectory+currentTimelapse.getName()+suffixVideoRecorded+".wav",
                    rootDirectory+currentTimelapse.getName()+suffixVideoRecorded+".h264",
                    rootDirectory+currentTimelapse.getName()+suffixVideoRecorded+".aac",
                    rootDirectory+currentTimelapse.getName()+suffixVideoFinal,
                    rootDirectory+currentTimelapse.getName()+suffixVideoFinal+".wav",
                    rootDirectory+currentTimelapse.getName()+suffixVideoFinal+".h264",
                    rootDirectory+currentTimelapse.getName()+suffixVideoFinal+".aac",
            };

            for(int i=0;i<listFilesToDelete.length;i++) {
                File fileToDelete = new File(listFilesToDelete[i]);

                if(fileToDelete.exists())
                    fileToDelete.delete();
            }

            Intent intent = new Intent(getActivity(), Activity_main.class);
            startActivity(intent);
        }
    };
}