package com.mobileagent.app.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobileagent.app.repository.DBHelper;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.Constants;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.factory.AppFactory;

public class CallEventImpl implements DBService<CallEvent> {

	private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;
    public CallEventImpl(Context context){
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
	public Long createContext(CallEvent e) {
		ContentValues contextValues = new ContentValues();
        contextValues.put("attribute", e.getAttribute());
        contextValues.put("value1", e.getValue1());
        contextValues.put("value2", e.getValue2());
        contextValues.put("action", e.getAction());
        contextValues.put("message", e.getMesage());

        openDatabase();

        Long id = getDatabase().insertOrThrow("CALL_EVENT",null,contextValues);

        closeDatabase();
        
        return id;
	}

	@Override
	public ContextObject getLatestContextObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallEvent> getAllContexts() {
		List<CallEvent> list = new ArrayList<CallEvent>();
        openDatabase();
        Cursor cursor = getDatabase().rawQuery("Select * from CALL_EVENT",null);
        
		if(cursor!=null){
			while(cursor.moveToNext()){
				HashMap<String,String> contextObjectValues = new HashMap<String, String>();
				contextObjectValues.put("attribute", cursor.getString(1));
				contextObjectValues.put("value1", cursor.getString(2));
				contextObjectValues.put("value2", cursor.getString(3));
				contextObjectValues.put("action" , cursor.getString(4));
				contextObjectValues.put("message" , cursor.getString(5));
						
				CallEvent c = AppFactory.buildCallEvent(contextObjectValues);
				list.add(c);
			}
        }
		cursor.close();
        closeDatabase();
        return list;
	}

	@Override
	public List<CallEvent> getByStorageDate(Date storageDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllContexts() {
		openDatabase();
        context.deleteDatabase(Constants.DATABASE_NAME);
        closeDatabase();
		
	}

}
