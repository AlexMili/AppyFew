/**
 * @author
 * @since
 */
package com.superone.superone;

/* ANDROID IMPORT */
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.superone.superone.activities.Activity_main;

/* STANDARD IMPORT */


public class old_MainActivity extends Activity implements OnClickListener, AppConfiguration {

	private Button btn_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_activity_main);
		
		btn_view = (Button) findViewById(R.id.btn_view);
		btn_view.setOnClickListener(this);
		
		((Button) findViewById(R.id.button1)).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {	
			case R.id.btn_view:
				Intent viewintent = new Intent(old_MainActivity.this, Activity_main.class);
				startActivity(viewintent);
				break;
			case R.id.button1:
				Toast.makeText(this, "Not available yet.", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
		}

	}

	public void myMethod(){
		Log.d(debugTag, "END THREAD");
	}
}
