package com.mobileagent.app.reactive_planning_agents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.Log;

import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.MessageEventImpl;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.MessageEvent;

public class MessageAgent implements Observer {
	
	private DBService<MessageEvent> messagedbService;
	public MessageEvent currentPlan;
	public static MessageEvent messagePlanToExecute; 
	private ContextObject co;
	
	public MessageAgent(Context c){
		messagedbService = new MessageEventImpl(c);
	}
	
	@Override
	public void update(Observable o, Object data) {
		if(data instanceof ContextObject){
			ContextObject present = (ContextObject)data;
			if(present != null){
				Log.d("MESSAGEAGENT PLAN", "Initializing MESSAGE Event Plans");
				initialiseMessageEventPlans(present);
			}
		}
	}
	
	private void initialiseMessageEventPlans(ContextObject present){
		List<MessageEvent> allMessageEventPlans = (messagedbService.getAllContexts()!=null)?messagedbService.getAllContexts():new ArrayList<MessageEvent>();
		co = present;
		for(MessageEvent m:allMessageEventPlans){
			currentPlan = m;
			setHighestPriorityPlan(currentPlan);
		}
		
		if(messagePlanToExecute==null){
			Log.d("MESSAGEAGENT PLAN", "SETTING NO PLAN NEW PLAN");
			messagePlanToExecute = new MessageEvent();
			messagePlanToExecute.setAction("default");
		}
	}
	
	private void setHighestPriorityPlan(MessageEvent currentPlan){
		Log.d("MESSAGEAGENT","setHighestPriorityPlan()");
		Log.d("MESSAGEAGENT WHATS THE PLANS?",currentPlan.toString());
		
		if(currentPlan.getAttribute().equals("calendar")){
			Log.d("MESSAGE AGENT PLAN", "CALENDAR PLAN SET");
			
			messagePlanToExecute = currentPlan;
		}else if(currentPlan.getAttribute().equals("location")){
			
			if(co.getLatitude().compareTo(currentPlan.getValue1()) == 0 && co.getLongitude().compareTo(currentPlan.getValue2()) == 0){
				Log.d("MESSAGE AGENT PLAN", "LOCATION PLAN SET");
				messagePlanToExecute = currentPlan;
			}
		}else if(currentPlan.getAttribute().equals("motion")){
			Log.d("MESSAGE AGENT PLAN", "MOTION PLAN SET");
			
			if(currentPlan.getValue2().equals("Faster Than")){
				if(co.getSpeed().compareTo(currentPlan.getValue2()) > 0)
					messagePlanToExecute = currentPlan;
			}else{
				if(co.getSpeed().compareTo(currentPlan.getValue2()) < 0)
					messagePlanToExecute = currentPlan;
			}
		}else if(currentPlan.getAttribute().equals("time")){
			
			Calendar now = Calendar.getInstance();
			String hour = (now.get(Calendar.HOUR_OF_DAY)<10)? new String("0"+now.get(Calendar.HOUR_OF_DAY)) : new String(""+now.get(Calendar.HOUR_OF_DAY));
			String minute = (now.get(Calendar.MINUTE)<10)? new String("0"+now.get(Calendar.MINUTE)) : new String(""+now.get(Calendar.MINUTE));

			String startTime = currentPlan.getValue1();
			String endTime = currentPlan.getValue2();
			String currentTime = hour+":"+minute;
			if(currentTime.compareTo(startTime)>0 && currentTime.compareTo(endTime)<0){
				Log.d("MESSAGE AGENT PLAN", "TIME PLAN SET");
				messagePlanToExecute = currentPlan;
			}
		}else{
			Log.d("MESSAGEAGENT PLAN", "SETTING DEFAULT PLAN");
			
			messagePlanToExecute = new MessageEvent();
			messagePlanToExecute.setAction("default");
		}
	}
}
