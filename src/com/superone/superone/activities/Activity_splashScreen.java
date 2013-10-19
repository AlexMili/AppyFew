/**
 * @author BadMojo
 * @since
 */
package com.superone.superone.activities;

/* ANDROID IMPORT */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.R;

/* STANDARD IMPORT */
import java.io.File;

/**
 * Splash screen displayed on the launch of the application.
 * Launch Activity_main on finish.
 */
public class Activity_splashScreen extends Activity implements AppConfiguration {

	private static final int SPLASH_TIME = SPLASHSCREEN_DURATION * 1000;// 3 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

        getActionBar().hide();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				File videosDirectory = new File(rootDirectory);
				// have the object build the directory structure, if needed.
				videosDirectory.mkdirs();
				Intent intent = new Intent(Activity_splashScreen.this, Activity_main.class);
				startActivity(intent);

                Activity_splashScreen.this.finish();

				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			}
		}, SPLASH_TIME);
		
		new Handler().postDelayed(new Runnable() {
			  @Override
			  public void run() { }
		}, SPLASH_TIME);

	}

	
	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}
}
