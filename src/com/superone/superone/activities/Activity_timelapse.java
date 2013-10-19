/**
 * @author
 * @since
 */
package com.superone.superone.activities;

/* ANDROID IMPORT */
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.activities.fragments.Fragment_settings;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.R;
import com.superone.superone.activities.fragments.Fragment_export;
import com.superone.superone.activities.fragments.Fragment_info;
import com.superone.superone.activities.fragments.Fragment_sound;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */
import java.util.ArrayList;

/**
 * Manage a project.
 */
public class Activity_timelapse extends Activity implements AppConfiguration,GestureDetector.OnGestureListener {

	private int indexCurrentTimelapse;
    private int mute=0;
    private ArrayList<TimelapseModel> timelapseList;
    private TimelapseModel currentTimelapse;
    private int currentFragment = 1;
    private int nextFragment = 1;
    private GestureDetector detector;

    private Fragment_info mFragment_info;
    private Fragment_sound mFragment_sound;
    private Fragment_export mFragment_export;
    private Fragment_settings mFragment_settings;

    private ImageButton btnInfo;
    private ImageButton btnSound;
    private ImageButton btnExport;
    private ImageButton btnSettings;

    private DatabaseHelperTimelapse db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        detector = new GestureDetector(this,this);

        //Database initialization
        db = new DatabaseHelperTimelapse(getApplicationContext());
        db.getWritableDatabase();
        timelapseList = db.getTimelapse();

		setContentView(R.layout.activity_timelapse);
        setupFragments();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        indexCurrentTimelapse = this.getIntent().getExtras().getInt("ModelPosition");
        currentTimelapse = timelapseList.get(indexCurrentTimelapse);
        actionBar.setTitle("Project: " + currentTimelapse.getName());

        btnInfo = (ImageButton) findViewById(R.id.btn_info);
        btnSound = (ImageButton) findViewById(R.id.btn_sound);
        btnExport = (ImageButton) findViewById(R.id.btn_export);
        btnSettings = (ImageButton) findViewById(R.id.btn_settings);

        btnInfo.setImageResource(R.drawable.super1_icon_menu_a);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragmentInfo();
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragmentSound();
            }
        });
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragmentExport();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFragmentSettings();
            }
        });

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.listFragment, this.mFragment_info);
        ft.commit();
	}

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                Intent intent = new Intent(Activity_timelapse.this, Activity_main.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    private void setupFragments() {
        this.mFragment_info = (Fragment_info) getFragmentManager().findFragmentById(R.id.fragment_info);
        if (this.mFragment_info == null) {
            this.mFragment_info = new Fragment_info(db);
        }
        this.mFragment_sound = (Fragment_sound) getFragmentManager().findFragmentById(R.id.fragment_sound);
        if (this.mFragment_sound == null) {
            this.mFragment_sound = new Fragment_sound(db);
        }
        this.mFragment_export = (Fragment_export)  getFragmentManager().findFragmentById(R.id.fragment_export);
        if (this.mFragment_export == null) {
            this.mFragment_export = new Fragment_export(db);
        }
        this.mFragment_settings = (Fragment_settings)  getFragmentManager().findFragmentById(R.id.fragment_settings);
        if (this.mFragment_settings == null) {
            this.mFragment_settings = new Fragment_settings(db);
        }
    }

    private void showFragment(final Fragment fragment) {
        if (fragment == null)
            return;

        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        if(currentFragment < nextFragment)
            ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
        else
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        ft.replace(R.id.listFragment, fragment);
        //ft.addToBackStack(null);
        ft.commit();
    }

    public void goToFragmentInfo() {
        nextFragment = 1;
        showFragment(this.mFragment_info);
        currentFragment = 1;
        btnInfo.setImageResource(R.drawable.super1_icon_menu_a);
        btnSound.setImageResource(R.drawable.super1_icon_music);
        btnExport.setImageResource(R.drawable.super1_icon_export);
        btnSettings.setImageResource(R.drawable.super1_icon_retour);
    }
    public void goToFragmentSound() {
        nextFragment = 2;
        showFragment(this.mFragment_sound);
        currentFragment = 2;
        btnInfo.setImageResource(R.drawable.super1_icon_menu);
        btnSound.setImageResource(R.drawable.super1_icon_music_a);
        btnExport.setImageResource(R.drawable.super1_icon_export);
        btnSettings.setImageResource(R.drawable.super1_icon_retour);
    }
    public void goToFragmentExport() {
        nextFragment = 3;
        showFragment(this.mFragment_export);
        currentFragment = 3;
        btnInfo.setImageResource(R.drawable.super1_icon_menu);
        btnSound.setImageResource(R.drawable.super1_icon_music);
        btnExport.setImageResource(R.drawable.super1_icon_export_a);
        btnSettings.setImageResource(R.drawable.super1_icon_retour);
    }
    public void goToFragmentSettings() {
        nextFragment = 4;
        showFragment(this.mFragment_settings);
        currentFragment = 4;
        btnInfo.setImageResource(R.drawable.super1_icon_menu);
        btnSound.setImageResource(R.drawable.super1_icon_music);
        btnExport.setImageResource(R.drawable.super1_icon_export);
        btnSettings.setImageResource(R.drawable.super1_icon_retour_a);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me){
        this.detector.onTouchEvent(me);
        return super.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX() > e2.getX())
        {
            switch (currentFragment)
            {
                case 1:
                    goToFragmentSound();
                    break;
                case 2:
                    goToFragmentExport();
                    break;
                case 3:
                    goToFragmentSettings();
            }
        }
        else
        {
            switch (currentFragment)
            {
                case 2:
                    goToFragmentInfo();
                    break;
                case 3:
                    goToFragmentSound();
                    break;
                case 4:
                    goToFragmentExport();
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
