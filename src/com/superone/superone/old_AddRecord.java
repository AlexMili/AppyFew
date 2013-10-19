/**
 * @author
 * @since
 */
package com.superone.superone;

/* ANDROID IMPORT */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.activities.Activity_timelapse;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */

public class old_AddRecord extends Activity implements OnClickListener, AppConfiguration {
	
	EditText txtname;
	EditText txtdescription;
	EditText txtduration;
	EditText txtdelay;

    CheckBox chckalarm;
    CheckBox chckdate;
    CheckBox chckhour;
    CheckBox chckpublish;

    Button btn_addrecord;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_new);
		getActionBar().hide();
		
		txtname = (EditText) findViewById(R.id.txtname);
		txtdescription = (EditText) findViewById(R.id.txtdescription);
		txtdelay = (EditText) findViewById(R.id.txtdelay);
		
//		chckalarm = (CheckBox) findViewById(R.id.chckalarm);
//		chckdate = (CheckBox) findViewById(R.id.chckdate);
//		chckhour = (CheckBox) findViewById(R.id.chckhour);
//		chckpublish = (CheckBox) findViewById(R.id.chckpublish);
		
		btn_addrecord = (Button) findViewById(R.id.btn_addrecord);
		btn_addrecord.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void onClick(View v) {
		switch (v.getId()) {	
			case R.id.btn_addrecord:
				checkFields();
				break;
			default:
				break;
		}
	}
	
	public void checkFields() {
		boolean flag = false;
		
		if(txtname.getText().toString() == null | txtname.getText().toString().length() == 0) {
			flag=true;
		}
		else {
			if(txtdescription.getText().toString() == null | txtdescription.getText().toString().length() == 0) {
				flag=true;
			}
			else {
				if(txtduration.getText().toString() == null | txtduration.getText().toString().length() == 0) {
					flag=true;
				}
				else {
					if(txtdelay.getText().toString() == null | txtdelay.getText().toString().length() == 0) {
						flag=true;
					}
					else {
						TimelapseModel tm = new TimelapseModel();
						
//						tm.setName(txtname.getText().toString());
//						tm.setDescription(txtdescription.getText().toString());
//						tm.setDuration(txtduration.getText().toString());
//						tm.setDelay(txtdelay.getText().toString());
//						tm.setDate(DateFormat.getDateFormat(this).format(new Date()));
//						tm.setAlarm(chckalarm.getText().toString());
//						tm.setDisplayDate(chckdate.getText().toString());
//						tm.setDisplayTime(chckhour.getText().toString());
//						tm.setPublish(chckpublish.getText().toString());
						
						DatabaseHelperTimelapse db = new DatabaseHelperTimelapse(getApplicationContext());
						
						db.getWritableDatabase();
						
						try {
							int position = db.addTimelapse(tm);
							Toast.makeText(this, "New timelapse created.", Toast.LENGTH_LONG).show();
							
							Intent viewintent = new Intent(old_AddRecord.this, Activity_timelapse.class);
							viewintent.putExtra("ModelPosition", position);
							startActivity(viewintent);
							this.finish();
						}
						catch(Exception e){ Toast.makeText(this, "An error occured.", Toast.LENGTH_LONG).show(); }
					}
				}
			}
		}
		
		if(flag) Toast.makeText(this, "You must fill all the fields.", Toast.LENGTH_LONG).show();
	}
}