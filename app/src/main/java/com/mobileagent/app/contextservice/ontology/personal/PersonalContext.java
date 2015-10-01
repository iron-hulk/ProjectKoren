package com.mobileagent.app.contextservice.ontology.personal;

import java.util.LinkedHashMap;

import android.content.Context;

public class PersonalContext {

	private Context mContext;
    private LinkedHashMap<String,String> personalContext;
    private ScheduleContext scheduleContext;

    public PersonalContext(Context context){
        mContext = context;
        personalContext = new LinkedHashMap<String, String>();
        scheduleContext = new ScheduleContext(mContext);
    }

    public LinkedHashMap<String,String> getPersonalContext(){
        scheduleContext.buildPersonalContext();
        personalContext.putAll(scheduleContext.getScheduleContext());

        return personalContext;
    }
}
