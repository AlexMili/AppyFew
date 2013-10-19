/**
 * @author BadMojo, alexandre
 * @since 21/09/13
 */
package com.superone.superone.activities.fragments;

/* ANDROID IMPORT */
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.R;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */
import java.util.ArrayList;

/**
 * Sound manager fragment.
 */
public class Fragment_sound extends Fragment implements AppConfiguration{

    private int indexCurrentTimelapse;

    private DatabaseHelperTimelapse db;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;
    private ArrayAdapter adapterSpinner;

    private Spinner sampleList;
    private Switch soundOnOff;

    public Fragment_sound(DatabaseHelperTimelapse database) {
        super();
        db = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sound, container, false);

        sampleList = (Spinner) v.findViewById(R.id.sample_list);
        adapterSpinner = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, samplesNames);
        sampleList.setAdapter(adapterSpinner);

        soundOnOff = (Switch) v.findViewById(R.id.sw_sound_on_off);

        return v;
    }

    @Override
    public void onResume()	{
        super.onResume();
        timelapseList = db.getTimelapse();
        indexCurrentTimelapse = getActivity().getIntent().getExtras().getInt("ModelPosition");
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);

        Log.d(debugTag, "Sample : " +samplesNames[Integer.parseInt(currentTimelapse.getSample())] + "-id:" +currentTimelapse.getSample());

        sampleList.setSelection(Integer.parseInt(currentTimelapse.getSample()));

        if(currentTimelapse.getSoundStatus().equals("yes"))
            soundOnOff.setChecked(true);
        else
            soundOnOff.setChecked(false);

        sampleList.setOnItemSelectedListener(selectedListener);
        soundOnOff.setOnCheckedChangeListener(checkedListener);
    }

    private AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            if(position == 0) {
                soundOnOff.setChecked(true);
                currentTimelapse.setSoundStatus("yes");
            }
            else {
                soundOnOff.setChecked(false);
                currentTimelapse.setSoundStatus("no");
            }

            currentTimelapse.setSample(String.valueOf(position));
            db.updateTimelapse(currentTimelapse);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) { }
    };

    private CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                sampleList.setSelection(0);
                currentTimelapse.setSample(String.valueOf(0));
                db.updateTimelapse(currentTimelapse);
            }
        }

    };
}
