package com.mobileagent.app.utilities.factory;

import java.util.HashMap;

import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.MessageEvent;

/**
 * Created by ironhulk on 2014/07/29.
 */
public class AppFactory {

    public static ContextObject buildContextObject(HashMap<String, String> contextValues){
        ContextObject co = new ContextObject();
        co.setLatitude(contextValues.get("latitude"));
        co.setLongitude(contextValues.get("longitude"));
        co.setAddress(contextValues.get("address"));
        co.setCode(contextValues.get("code"));
        co.setSpeed(contextValues.get("speed"));
        co.setAccuracy(contextValues.get("accuracy"));
        co.setTimeSinceUpdate(contextValues.get("time_since_update"));
        co.setDeviceEnclosed(contextValues.get("device_enclosed"));
        co.setCalendarEventTitle(contextValues.get("calendar_event_title"));
        co.setCalendarEventLocation(contextValues.get("calendar_event_location"));
        co.setCalendarEventOrganizer(contextValues.get("calendar_event_organizer"));
        co.setCalendarEventStart(contextValues.get("calendar_event_start"));
        co.setCalendarEventEnd(contextValues.get("calendar_event_end"));
        return co;
    }
    
    public static CallEvent buildCallEvent(HashMap<String, String> contextValues){
    	CallEvent co = new CallEvent();
        co.setAttribute(contextValues.get("attribute"));
        co.setAction(contextValues.get("action"));
        co.setValue1(contextValues.get("value1"));
        co.setValue2(contextValues.get("value2"));
        co.setMesage(contextValues.get("message"));
        return co;
    }
    
    public static MessageEvent buildMessageEvent(HashMap<String, String> contextValues){
    	MessageEvent co = new MessageEvent();
        co.setAttribute(contextValues.get("attribute"));
        co.setAction(contextValues.get("action"));
        co.setValue1(contextValues.get("value1"));
        co.setValue2(contextValues.get("value2"));
        co.setMesage(contextValues.get("message"));
        return co;
    }
}
