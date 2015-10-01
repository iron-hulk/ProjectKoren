package com.mobileagent.app.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mobileagent.app.utilities.Constants;

/**
 * Created by ironhulk on 2014/07/29.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " 
        			+ Constants.TABLE_NAME 
        			+ " ( " + Constants.COL_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT, " 
        			+ Constants.COL_LATITUDE +" TEXT, " 
        			+ Constants.COL_LONGITUDE +" TEXT, "
        			+ Constants.COL_ADDRESS +" TEXT, "
        			+ Constants.COL_CODE +" TEXT, "
        			+ Constants.COL_SPEED +" TEXT,"
        			+ Constants.COL_ACCURACY +" TEXT, "
        			+ Constants.COL_TIME_SINCE_UPDATE +" TEXT, "
        			+ Constants.COL_DEVICE_ENCLOSED +" TEXT, "
        			+ Constants.COL_CALENDAR_EVENT_TITLE +" TEXT, "
        			+ Constants.COL_CALENDAR_EVENT_ORGANIZER +" TEXT, "
        			+ Constants.COL_CALENDAR_EVENT_LOCATION +" TEXT, "
        			+ Constants.COL_CALENDAR_EVENT_START +" TEXT, "
        			+ Constants.COL_CALENDAR_EVENT_END +" TEXT);");
        
        db.execSQL("CREATE TABLE CALL_EVENT " 
    			+ " ( " + Constants.COL_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT, " 
    			+ "attribute TEXT, " 
    			+ "value1 TEXT, "
    			+ "value2 TEXT, "
    			+ "action TEXT, "
    			+ "message TEXT);");
        
        db.execSQL("CREATE TABLE MESSAGE_EVENT " 
    			+ " ( " + Constants.COL_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT, " 
    			+ "attribute TEXT, " 
    			+ "value1 TEXT, "
    			+ "value2 TEXT, "
    			+ "action TEXT, "
    			+ "message TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS context");
        onCreate(db);
    }
}

