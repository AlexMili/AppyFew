package com.superone.superone.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.superone.superone.models.TimelapseModel;

public class DatabaseHelperTimelapse extends SQLiteOpenHelper {

	public static String DATABASENAME = "growup_database";
	public static String TIMELAPSETABLE = "timelapses";
	public static String colTimelapseId = "id";
	public static String _colTimelapseid = "timelapseidno";
	public static String colTimelapseName = "timelapsename";
	public static String colTimelapseDescription = "timelapsedescription";
	public static String colTimelapseDate = "timelapsedate";
	public static String colTimelapseDuration = "timelapseduration";
	public static String colTimelapseDelay = "timelapsedelay";
	public static String colTimelapseURLmain = "timelapseurlmain";
	public static String colTimelapseURLslave = "timelapseurlslave";
	public static String colTimelapseURLthumbnail = "timelapseurlthumbnail";
	public static String colTimelapseAlarm = "timelapsealarm";
	public static String colTimelapseDisplayDate = "timelapsedisplaydate";
	public static String colTimelapseDisplayTime = "timelapsedisplaytime";
	public static String colTimelapsePublish = "timelapsepublish";
    public static String colTimelapseSoundStatus = "timelapsesoundstatus";
    public static String colTimelapseSample = "timelapsesample";
	private ArrayList<TimelapseModel> cartList = new ArrayList<TimelapseModel>();
	Context c;

	public DatabaseHelperTimelapse(Context context) {
		super(context, DATABASENAME, null, 33);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE if not exists timelapsetable(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "timelapseidno"
				+ " TEXT ,"
				+ "timelapsename"
				+ " TEXT,"
				+ "timelapsedescription" 
				+ " TEXT,"
				+ "timelapsedate" 
				+ " TEXT,"
				+ "timelapseduration" 
				+ " TEXT,"
				+ "timelapsedelay" 
				+ " TEXT,"
				+ "timelapseurlmain" 
				+ " TEXT,"
				+ "timelapseurlslave" 
				+ " TEXT,"
				+ "timelapseurlthumbnail" 
				+ " TEXT,"
				+ "timelapsealarm" 
				+ " TEXT,"
				+ "timelapsedisplaydate" 
				+ " TEXT,"
                + "timelapsedisplaytime"
                + " TEXT,"
                + "timelapsesoundstatus"
                + " TEXT,"
                + "timelapsesample"
                + " TEXT,"
				+ "timelapsepublish" 
				+ " TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TIMELAPSETABLE);
		onCreate(db);
	}

	public int addTimelapse(TimelapseModel timelapseitem) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("timelapseidno", timelapseitem.idno);
		contentValues.put("timelapsename", timelapseitem.timelapsename);
		contentValues.put("timelapsedescription", timelapseitem.timelapsedescription);
		contentValues.put("timelapsedate", timelapseitem.timelapsedate);
		contentValues.put("timelapseduration", timelapseitem.timelapseduration);
		contentValues.put("timelapsedelay", timelapseitem.timelapsedelay);
		contentValues.put("timelapseurlmain", timelapseitem.timelapseurlmain);
		contentValues.put("timelapseurlslave", timelapseitem.timelapseurlslave);
		contentValues.put("timelapseurlthumbnail", timelapseitem.timelapseurlthumbnail);
		contentValues.put("timelapsealarm", timelapseitem.timelapsealarm);
		contentValues.put("timelapsedisplaydate", timelapseitem.timelapsedisplaydate);
		contentValues.put("timelapsedisplaytime", timelapseitem.timelapsedisplaytime);
        contentValues.put("timelapsepublish", timelapseitem.timelapsepublish);
        contentValues.put("timelapsesample", timelapseitem.timelapsesample);
        contentValues.put("timelapsesoundstatus", timelapseitem.timelapsesoundstatus);
		long id = db.insert("timelapsetable", null, contentValues);
		db.close();
		return (int)id - 1;
	}

	// update

	public void updateTimelapse(TimelapseModel timelapseList) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("timelapseidno", timelapseList.idno);
		contentValues.put("timelapsename", timelapseList.timelapsename);
		contentValues.put("timelapsedescription", timelapseList.timelapsedescription);
		contentValues.put("timelapsedate", timelapseList.timelapsedate);
		contentValues.put("timelapseduration", timelapseList.timelapseduration);
		contentValues.put("timelapsedelay", timelapseList.timelapsedelay);
		contentValues.put("timelapseurlmain", timelapseList.timelapseurlmain);
		contentValues.put("timelapseurlslave", timelapseList.timelapseurlslave);
		contentValues.put("timelapseurlthumbnail", timelapseList.timelapseurlthumbnail);
		contentValues.put("timelapsealarm", timelapseList.timelapsealarm);
		contentValues.put("timelapsedisplaydate", timelapseList.timelapsedisplaydate);
		contentValues.put("timelapsedisplaytime", timelapseList.timelapsedisplaytime);
		contentValues.put("timelapsepublish", timelapseList.timelapsepublish);
        contentValues.put("timelapsesample", timelapseList.timelapsesample);
        contentValues.put("timelapsesoundstatus", timelapseList.timelapsesoundstatus);
		db.update("timelapsetable", contentValues, "id=" + timelapseList.id, null);

		db.close();
	}

	public void emptyTimelapse() {
        SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("delete from timelapsetable");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void removeTimelapse(String timelapseid) {
        SQLiteDatabase db = this.getWritableDatabase();
		try {

			String[] args = { timelapseid };
            db.delete("timelapsetable", "id="+timelapseid, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            db.close();
        }
	}

	public ArrayList<TimelapseModel> getTimelapse() {

		cartList.clear();

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from timelapsetable", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					TimelapseModel item = new TimelapseModel();

					item.id = cursor.getInt(cursor
							.getColumnIndex("id"));
					item.idno = cursor.getString(cursor
							.getColumnIndex("timelapseidno"));

					item.timelapsename = cursor.getString(cursor
							.getColumnIndex("timelapsename"));

					item.timelapsedescription = cursor.getString(cursor
							.getColumnIndex("timelapsedescription"));

					item.timelapsedate = cursor.getString(cursor
							.getColumnIndex("timelapsedate"));

					item.timelapseduration = cursor.getString(cursor
							.getColumnIndex("timelapseduration"));
					
					item.timelapsedelay = cursor.getString(cursor
							.getColumnIndex("timelapsedelay"));
					
					item.timelapseurlmain = cursor.getString(cursor
							.getColumnIndex("timelapseurlmain"));
					
					item.timelapseurlslave = cursor.getString(cursor
							.getColumnIndex("timelapseurlslave"));
					
					item.timelapseurlthumbnail = cursor.getString(cursor
							.getColumnIndex("timelapseurlthumbnail"));
					
					item.timelapsealarm = cursor.getString(cursor
							.getColumnIndex("timelapsedescription"));
					
					item.timelapsedescription = cursor.getString(cursor
							.getColumnIndex("timelapsealarm"));
					
					item.timelapsedisplaydate = cursor.getString(cursor
							.getColumnIndex("timelapsedisplaydate"));
					
					item.timelapsedisplaytime = cursor.getString(cursor
							.getColumnIndex("timelapsedisplaytime"));
					
					item.timelapsepublish = cursor.getString(cursor
							.getColumnIndex("timelapsepublish"));

                    item.timelapsesoundstatus = cursor.getString(cursor
                            .getColumnIndex("timelapsesoundstatus"));

                    item.timelapsesample = cursor.getString(cursor
                            .getColumnIndex("timelapsesample"));
					
					cartList.add(item);

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		db.close();
		return cartList;
	}
}
