package com.mobileagent.app.services.reactive_planning_agents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.util.Log;

import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.ContextObject;

public class CallAgent implements Observer {
	
	private DBService<CallEvent> calldbService;
	public CallEvent currentPlan;
	public static CallEvent planToExecute; 
	private ContextObject co;
	
	public CallAgent(Context c){
		Log.d("CALL AGENT PLAN", "Call Agent Constructor");
		calldbService = new CallEventImpl(c);
	}
	
	@Override
	public void update(Observable o, Object data) {
		Log.d("CALL AGENT PLAN", "Update Called");
		
		if(data instanceof ContextObject){
			ContextObject present = (ContextObject)data;
			if(present != null){
				Log.d("CALL AGENT PLAN", "Initializing Call Event Plans");
				initialiseCallEventPlans(present);
			}
		}
	}
	
	private void initialiseCallEventPlans(ContextObject present){
		List<CallEvent> allCallEventPlans = (calldbService.getAllContexts()!=null)?calldbService.getAllContexts():new ArrayList<CallEvent>();
		co = present;
		for(CallEvent c:allCallEventPlans){
			currentPlan = c;
			setHighestPriorityPlan(currentPlan);
		}
		
		if(planToExecute==null){
			Log.d("MESSAGEAGENT PLAN", "SETTING NO PLAN NEW PLAN");
			planToExecute = new CallEvent();
			planToExecute.setAction("default");
		}
	}
	
	private void setHighestPriorityPlan(CallEvent currentPlan){
		Log.d("CallAgent","setHighestPriorityPlan()");
		
		if(currentPlan.getAttribute().equals("calendar")){
			Log.d("MESSAGE AGENT PLAN", "CALENDAR PLAN SET");
			planToExecute = currentPlan;
		}else if(currentPlan.getAttribute().equals("location")){
			if(co.getLatitude()!=null && co.getLongitude()!=null){
				if(co.getLatitude().compareTo(currentPlan.getValue1()) == 0 && co.getLongitude().compareTo(currentPlan.getValue2()) == 0){
					Log.d("CallAgent","LOCATION PLAN SET");
					planToExecute = currentPlan;
				}
			}
		}else if(currentPlan.getAttribute().equals("motion")){
			
			String planSpeed = currentPlan.getValue1()+".0";
			Log.d("CALL AGENT PLAN", planSpeed + " -> " + co.getSpeed());
			
			
			if(currentPlan.getValue2().equals("Faster Than")){
				if(co.getSpeed().compareTo(planSpeed) > 0){
					Log.d("CALL AGENT PLAN", "MOTION PLAN SET");
					planToExecute = currentPlan;
				}
			}else{
				if(co.getSpeed().compareTo(planSpeed) < 0){
					Log.d("CALL AGENT PLAN", "MOTION PLAN SET");
					planToExecute = currentPlan;
				}
			}
		}else if(currentPlan.getAttribute().equals("time")){
			Calendar now = Calendar.getInstance();
			String hour = (now.get(Calendar.HOUR_OF_DAY)<10)? new String("0"+now.get(Calendar.HOUR_OF_DAY)) : new String(""+now.get(Calendar.HOUR_OF_DAY));
			String minute = (now.get(Calendar.MINUTE)<10)? new String("0"+now.get(Calendar.MINUTE)) : new String(""+now.get(Calendar.MINUTE));

			String startTime = currentPlan.getValue1();
			String endTime = currentPlan.getValue2();
			String currentTime = hour+":"+minute;
			
			if(currentTime.compareTo(startTime)>0 && currentTime.compareTo(endTime)<0){
				Log.d("CALL AGENT PLAN", "TIME PLAN SET");
				planToExecute = currentPlan;
			}
		}else{
			Log.d("CALLAGENT PLAN", "SETTING DEFAULT PLAN");
			
			planToExecute = new CallEvent();
			planToExecute.setAction("default");
		}
		
	}
}
