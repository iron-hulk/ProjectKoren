package com.mobileagent.app.services.context_services;

import java.util.LinkedHashMap;
import java.util.Observable;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.mobileagent.app.contextservice.ontology.environmental.EnvironmentContext;
import com.mobileagent.app.contextservice.ontology.personal.PersonalContext;
import com.mobileagent.app.contextservice.ontology.spatiotemporal.SpatioTemporalContext;
import com.mobileagent.app.reactive_planning_agents.CallAgent;
import com.mobileagent.app.reactive_planning_agents.MessageAgent;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.ContextServiceImpl;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.factory.AppFactory;

//This class will be used to gather context information every two minutes and notify observers of changes
public class ContextObservable extends Observable implements Runnable{

	static final int TWO_MINUTES = 120000;
	private Context c;
	
    public ContextObservable(Context c){
    	Log.d("ContextObservable","Constructor()");
    	this.c = c;
    }
    
	@Override
	public void run() {
		Log.d("ContextObservable","run()");
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
		try{
			Looper.prepare();
			while(true){
				
				if(Thread.currentThread().isInterrupted()){
					CallAgent.planToExecute = null;
					MessageAgent.messagePlanToExecute = null;
					Log.d("ContextObservable","threadInterrupted()");
					return;
				}
				ContextObject now = gatherContext();
				setChanged();
	            notifyObservers(now);
	            
	            Thread.sleep(TWO_MINUTES);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private ContextObject gatherContext(){
		Log.d("ContextObservable","gatherContext()");
		
	    EnvironmentContext environmentContext = new EnvironmentContext(c);
	    SpatioTemporalContext spatioTemporalContext = new SpatioTemporalContext(c);
        PersonalContext personalContext = new PersonalContext(c);
        LinkedHashMap<String,String> contextInfo =  new LinkedHashMap<String, String>();
        DBService<ContextObject> dbService = new ContextServiceImpl(c);
        
		contextInfo.putAll(personalContext.getPersonalContext());
        contextInfo.putAll(spatioTemporalContext.getSpatioTemporalContext());
        
        contextInfo.putAll(environmentContext.getEnvironmentContext());

        ContextObject c = AppFactory.buildContextObject(contextInfo);
        dbService.createContext(c);
        environmentContext.cleanUpContextListeners();
        return c;
	}
}
