/**
 * @author BadMojo
 * @since 21/09/13
 */
package com.superone.superone.activities;

/* ANDROID IMPORT */
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

/* LOCAL IMPORT */
import com.superone.superone.AppConfiguration;
import com.superone.superone.db.DatabaseHelperTimelapse;
import com.superone.superone.R;
import com.superone.superone.models.TimelapseModel;

/* STANDARD IMPORT */
import java.util.Date;

/**
 * Add a project in the database.
 */
public class Activity_new extends Activity implements AppConfiguration {

    private EditText txtname;
    private EditText txtdescription;
    private EditText txtdelay;

    private Switch switchAlert;
    private Switch switchDate;
    private Switch switchPublic;

    private Button btn_addrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtname = (EditText) findViewById(R.id.txtname);
        txtdescription = (EditText) findViewById(R.id.txtdescription);
        txtdelay = (EditText) findViewById(R.id.txtdelay);

        switchAlert = (Switch) findViewById(R.id.switchAlert);
        switchDate = (Switch) findViewById(R.id.switchDate);
        switchPublic = (Switch) findViewById(R.id.switchPublic);

        btn_addrecord = (Button) findViewById(R.id.btn_addrecord);
        btn_addrecord.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {
            checkFields(view);
        }

        public void checkFields(View view) {
            boolean flag = false;

            if(txtname.getText().toString() == null | txtname.getText().toString().length() == 0) {
                flag=true;
            }
            else {
                if(txtdescription.getText().toString() == null | txtdescription.getText().toString().length() == 0) {
                    flag=true;
                }
                else {
                    if(txtdelay.getText().toString() == null | txtdelay.getText().toString().length() == 0) {
                        flag=true;
                    }
                    else {
                        TimelapseModel newTimelapse = new TimelapseModel();

                        newTimelapse.setName(txtname.getText().toString());
                        newTimelapse.setDescription(txtdescription.getText().toString());

                        newTimelapse.setDelay(txtdelay.getText().toString());

                        newTimelapse.setDate(DateFormat.getDateFormat(view.getContext()).format(new Date()));
                        Log.d(debugTag, "Current date : " + newTimelapse.getDate());


                        newTimelapse.setAlarm(String.valueOf(switchAlert.isChecked()));
                        newTimelapse.setDisplayDate(String.valueOf(switchDate.isChecked()));
                        newTimelapse.setPublish(String.valueOf(switchPublic.isChecked()));

                        newTimelapse.setSample("0");
                        newTimelapse.setSoundStatus("yes");

                        DatabaseHelperTimelapse db = new DatabaseHelperTimelapse(view.getContext());

                        db.getWritableDatabase();

                        try {
                            int idNewRow = db.addTimelapse(newTimelapse);
                            int position = db.getTimelapse().size()-1;
                            Toast.makeText(view.getContext(), "New timelapse created.", Toast.LENGTH_LONG).show();


                            Intent viewintent = new Intent(Activity_new.this, Activity_timelapse.class);
                            viewintent.putExtra("ModelPosition", position);
                            startActivity(viewintent);
                            finish();
                        }
                        catch(Exception e){
                            Toast.makeText(view.getContext(), "An error occured.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            if(flag) Toast.makeText(view.getContext(), "You must fill all the fields.", Toast.LENGTH_LONG).show();
        }


    };

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case android.R.id.home:
                Intent intent = new Intent(this, Activity_main.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
