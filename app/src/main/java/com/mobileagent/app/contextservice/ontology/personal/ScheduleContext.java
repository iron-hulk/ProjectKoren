package com.mobileagent.app.contextservice.ontology.personal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.Time;
import android.util.Log;

public class ScheduleContext {

	private Context mContext;
    private LinkedHashMap<String,String> scheduleContext;

    public ScheduleContext(Context context) {
        this.mContext = context;
    }

    public void buildPersonalContext(){
        readCalendarDetailsWithContext();
    }

    private void readCalendarDetailsWithContext() {
        Uri calendars = Uri.parse("content://com.android.calendar/calendars");
        String[] projection = new String[]{"_id","name"};
        Cursor cursor = mContext.getContentResolver().query(calendars, projection, null, null, null);

        int id = cursor.getColumnIndex("_id");

        if(cursor.moveToFirst()) {
            while(cursor.moveToNext()){
                buildCalendarEvents(cursor.getString(id));
            }
        }
        cursor.close();
    }

    private void buildCalendarEvents(String calendarID){
    	
        Uri events = Uri.parse("content://com.android.calendar/events");
        String[] eventProjection = new String[]{"calendar_id","organizer","eventLocation","title","dtstart","dtend"};

        String mSelectionClause =  " calendar_id = "+calendarID;
        Cursor eventCursor = mContext.getContentResolver().query(events,eventProjection,mSelectionClause,null,null);

        int organizer = eventCursor.getColumnIndex("organizer");
        int title = eventCursor.getColumnIndex("title");
        int dtstart = eventCursor.getColumnIndex("dtstart");
        int dtend = eventCursor.getColumnIndex("dtend");
        int location = eventCursor.getColumnIndex("eventLocation");
        
        while(eventCursor.moveToNext()){
        	if(eventCursor.getLong(dtstart)<=getEndingHour()&&eventCursor.getLong(dtstart)>=getStartingHour()){
        		scheduleContext = new LinkedHashMap<String, String>();
	        	scheduleContext.put("calendar_event_title", eventCursor.getString(title));
	        	scheduleContext.put("calendar_event_organizer", eventCursor.getString(organizer));
	        	scheduleContext.put("calendar_event_location", eventCursor.getString(location));
	        	scheduleContext.put("calendar_event_start", getDate(eventCursor.getLong(dtstart)));
	        	scheduleContext.put("calendar_event_end", getDate(eventCursor.getLong(dtend)));
	        	break;
        	}
        }
        if(scheduleContext == null)
        	scheduleContext = new LinkedHashMap<String, String>();
        eventCursor.close();
    }

    private long getStartingHour(){ 
    	Calendar start = Calendar.getInstance();
    	start.set(Calendar.MINUTE, 0);
    	start.set(Calendar.SECOND, 0);
    	start.set(Calendar.MILLISECOND, 0);
    	
    	return start.getTimeInMillis();
    }
    private long getEndingHour(){ 
    	Calendar end = Calendar.getInstance();
    	end.add(Calendar.HOUR_OF_DAY, 1);
    	end.set(Calendar.MINUTE, 0);
    	end.set(Calendar.SECOND, 0);
    	end.set(Calendar.MILLISECOND, 0);
    	
    	return end.getTimeInMillis();
    }
    
    private String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public LinkedHashMap<String,String> getScheduleContext(){
        return scheduleContext;
    }
}
