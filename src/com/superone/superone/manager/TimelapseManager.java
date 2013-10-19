package com.superone.superone.manager;

import com.superone.superone.models.TimelapseModel;

import java.util.ArrayList;

/**
 * Created by BadMojo on 22/09/13.
 */
public class TimelapseManager {

    private ArrayList<TimelapseModel> timelapses;
    private static TimelapseManager mInstance = null;

    public static TimelapseManager getInstance()
    {
        if (mInstance == null)
            mInstance = new TimelapseManager();
        return mInstance;
    }
}
