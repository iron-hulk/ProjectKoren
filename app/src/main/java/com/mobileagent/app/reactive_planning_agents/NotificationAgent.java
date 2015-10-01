package com.mobileagent.app.reactive_planning_agents;

import java.util.Observable;
import java.util.Observer;

import com.mobileagent.app.utilities.ContextObject;

public class NotificationAgent implements Observer{
	/**NEED TO DEFINE REACTIVE ACTIONS TO BE DONE**/
	private ContextObject past;
	
	public NotificationAgent(){
		past = null;
	}
	
	@Override
	public void update(Observable o, Object data) {
		if(data instanceof ContextObject){
			ContextObject present = (ContextObject)data;
			if(past != null){
				if(!past.equals(present)){
					//context change
					//update a reactive plan
				}
			}else{
				//first time gathering context
				past = present;
			}
		}
		
	}

}
