package com.mobileagent.app.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobileagent.app.repository.DBHelper;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.utilities.Constants;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.factory.AppFactory;

/**
 * Created by ironhulk on 2014/07/29.
 * This class responsibility is to handled context persistent services
 */
public class ContextServiceImpl implements DBService<ContextObject> {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    public ContextServiceImpl(Context context){
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    private void openDatabase(){
        database = dbHelper.getWritableDatabase();
    }

    private void closeDatabase(){
        dbHelper.close();
    }

    private SQLiteDatabase getDatabase(){
        return database;
    }
    @Override
    public Long createContext(ContextObject contextObject) {
        ContentValues contextValues = new ContentValues();
        contextValues.put(Constants.COL_LATITUDE, contextObject.getLatitude());
        contextValues.put(Constants.COL_LONGITUDE, contextObject.getLongitude());
        contextValues.put(Constants.COL_ADDRESS, contextObject.getAddress());
        contextValues.put(Constants.COL_CODE, contextObject.getCode());
        contextValues.put(Constants.COL_SPEED, contextObject.getSpeed());
        contextValues.put(Constants.COL_ACCURACY, contextObject.getAccuracy());
        contextValues.put(Constants.COL_TIME_SINCE_UPDATE, contextObject.getTimeSinceUpdate());
        contextValues.put(Constants.COL_DEVICE_ENCLOSED, contextObject.getDeviceEnclosed());
        contextValues.put(Constants.COL_CALENDAR_EVENT_TITLE, contextObject.getCalendarEventTitle());
        contextValues.put(Constants.COL_CALENDAR_EVENT_ORGANIZER, contextObject.getCalendarEventOrganizer());
        contextValues.put(Constants.COL_CALENDAR_EVENT_LOCATION, contextObject.getCalendarEventLocation());
        contextValues.put(Constants.COL_CALENDAR_EVENT_START, contextObject.getCalendarEventStart());
        contextValues.put(Constants.COL_CALENDAR_EVENT_END, contextObject.getCalendarEventEnd());

        openDatabase();

        Long id = getDatabase().insertOrThrow(Constants.TABLE_NAME,null,contextValues);

        closeDatabase();
        
        return id;
    }

    private String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }

    @Override
    public List<ContextObject> getAllContexts() {
        List<ContextObject> list = new ArrayList<ContextObject>();
        openDatabase();
        Cursor cursor = getDatabase().rawQuery("Select * from "+Constants.TABLE_NAME,null);
        
		if(cursor!=null){
			while(cursor.moveToNext()){
				HashMap<String,String> contextObjectValues = new HashMap<String, String>();
				contextObjectValues.put("_id", ""+cursor.getLong(0));
				contextObjectValues.put("latitude", cursor.getString(1));
				contextObjectValues.put("longitude", cursor.getString(2));
				contextObjectValues.put("address", cursor.getString(3));
				contextObjectValues.put("code" , cursor.getString(4));
				contextObjectValues.put("speed", cursor.getString(5));
				contextObjectValues.put("accuracy", cursor.getString(6));
				contextObjectValues.put("time_since_update", cursor.getString(7));
				contextObjectValues.put("device_enclosed", cursor.getString(8));
				contextObjectValues.put("calendar_event_title", cursor.getString(9));
				contextObjectValues.put("calendar_event_location", cursor.getString(10));
				contextObjectValues.put("calendar_event_organizer", cursor.getString(11));
				contextObjectValues.put("calendar_event_start", cursor.getString(12));
				contextObjectValues.put("calendar_event_end", cursor.getString(13));
						
				ContextObject c = AppFactory.buildContextObject(contextObjectValues);
				list.add(c);
			}
        }
		cursor.close();
        closeDatabase();
        return list;
    }

    @Override
    public List<ContextObject> getByStorageDate(Date storageDate) {
        return null;
    }

    @Override
    public void deleteAllContexts() {
        openDatabase();
        getDatabase().delete(Constants.TABLE_NAME,null,null);
        closeDatabase();
    }

	@Override
	public ContextObject getLatestContextObject() {
		openDatabase();
		ContextObject c = null;
		Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY _id DESC LIMIT 1",null);
		if(cursor!=null){
			cursor.moveToNext();
			HashMap<String,String> contextObjectValues = new HashMap<String, String>();
			contextObjectValues.put("_id", ""+cursor.getLong(0));
			contextObjectValues.put("latitude", cursor.getString(1));
			contextObjectValues.put("longitude", cursor.getString(2));
			contextObjectValues.put("address", cursor.getString(3));
			contextObjectValues.put("code" , cursor.getString(4));
			contextObjectValues.put("speed", cursor.getString(5));
			contextObjectValues.put("accuracy", cursor.getString(6));
			contextObjectValues.put("time_since_update", cursor.getString(7));
			contextObjectValues.put("device_enclosed", cursor.getString(8));
			contextObjectValues.put("calendar_event_title", cursor.getString(9));
			contextObjectValues.put("calendar_event_location", cursor.getString(10));
			contextObjectValues.put("calendar_event_organizer", cursor.getString(11));
			contextObjectValues.put("calendar_event_start", cursor.getString(12));
			contextObjectValues.put("calendar_event_end", cursor.getString(13));
					
			c = AppFactory.buildContextObject(contextObjectValues);
        }
		cursor.close();
		closeDatabase();
		
		return c;
	}
}
