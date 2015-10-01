package com.mobileagent.app.services.event_listeners;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.mobileagent.app.fragments.AgentStartFragment;
import com.mobileagent.app.reactive_planning_agents.CallAgent;

/**
 * Created by ironhulk on 2014/08/04.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

	private Context mContext;
	private TelephonyManager telephony;
	private ITelephony telephonyService;
	static int callRecieverCount = 0;
	static int callStateCount = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.d("COUNT RECEIVER HIT",String.valueOf(callRecieverCount=callRecieverCount+1));
    	
        MyPhoneStateListener phoneListener=new MyPhoneStateListener();
        telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
  
        mContext = context;
        telephony.listen(phoneListener,PhoneStateListener.LISTEN_NONE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        
        public void onCallStateChanged(int state,String incomingNumber){
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                	
                	if(CallAgent.planToExecute.getAction().compareTo("Silence")==0){
    		    		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    		    		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		    		if(!CallAgent.planToExecute.getAttribute().equals("calendar")){
	    		    		SmsManager sms = SmsManager.getDefault();
	    		    		sms.sendTextMessage(incomingNumber, null, CallAgent.planToExecute.getMesage(), null, null);
	    		    		
	    		    		Log.d("CALL ACTION","Silence Calendar");
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
    		    		}else{
    		    			Log.d("CALL ACTION","Silence");
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
	    	    			
	    	    			SmsManager smsManager = SmsManager.getDefault();
	    	    			smsManager.sendTextMessage(incomingNumber, null, "I am currently in a meeting, will contact once available", null , null);
    		    		}
                	}
                	
                	if(CallAgent.planToExecute.getAction().compareTo("Ring")==0){
    		    		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    		    		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    		    		
		    			Log.d("CALL ACTION","Ring");
    	    			Message message= Message.obtain();
    	    	        
    	    			StringBuilder sb = new StringBuilder();
    	    			sb.append("Call from: "+incomingNumber+" was handled");
    	    	        Bundle contextData = new Bundle();
    	    	        contextData.putString("events", sb.toString());
    	    			message.setData(contextData); 
    	    			AgentStartFragment.handler.sendMessage(message);
    	    			
    	    			SmsManager smsManager = SmsManager.getDefault();
		            	smsManager.sendTextMessage(incomingNumber, null, CallAgent.planToExecute.getMesage(), null , null);
    		    	}
                	
                	if(CallAgent.planToExecute.getAction().compareTo("End Call")==0){
    		    		Class c;
						try {
							c = Class.forName(telephony.getClass().getName());
							Method m = c.getDeclaredMethod("getITelephony");
							m.setAccessible(true);
							telephonyService = (ITelephony) m.invoke(telephony);
							telephonyService.endCall();
							
							SmsManager smsManager = SmsManager.getDefault();
			            	smsManager.sendTextMessage(incomingNumber, null, CallAgent.planToExecute.getMesage(), null , null);
			            	
    		    			Log.d("CALL ACTION","End Call");
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
    		    	}
                	
                	if(CallAgent.planToExecute.getAction().compareTo("Vibrate")==0){
    		    		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    		    		am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    		    		
    		    		if(!CallAgent.planToExecute.getAttribute().equals("calendar")){
	    		    		SmsManager sms = SmsManager.getDefault();
	    		    		sms.sendTextMessage(incomingNumber, null, CallAgent.planToExecute.getMesage(), null , null);
	    		    		
	    		    		Log.d("CALL ACTION","Vibrate calendar");
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
	    	    			
    		    		}else{
    		    			Log.d("CALL ACTION","Vibrate");
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
	    	    			SmsManager smsManager = SmsManager.getDefault();
	    	    			smsManager.sendTextMessage(incomingNumber, null, "I am currently in a meeting, will contact once available", null , null);
    		    		}
    		    	}
                	
                	if(CallAgent.planToExecute.getAction().compareTo("Urgently Notify Person")==0){
                		Log.d("CALL ACTION","Urgently Notify Person");
    		    		Class c;
						try {
							
							c = Class.forName(telephony.getClass().getName());
							Method m = c.getDeclaredMethod("getITelephony");
							m.setAccessible(true);
							telephonyService = (ITelephony) m.invoke(telephony);
							telephonyService.endCall();
							
							SmsManager smsManager = SmsManager.getDefault();
			            	smsManager.sendTextMessage(incomingNumber, null, "I am currently in a meeting, will contact once available", null , null);
			            	
	    	    			Message message= Message.obtain();
	    	    	        
	    	    			StringBuilder sb = new StringBuilder();
	    	    			sb.append("Call from: "+incomingNumber+" was handled");
	    	    	        Bundle contextData = new Bundle();
	    	    	        contextData.putString("events", sb.toString());
	    	    			message.setData(contextData); 
	    	    			AgentStartFragment.handler.sendMessage(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
    		    	}
    		    	
                	if(CallAgent.planToExecute.getAction().compareTo("default")==0){
                		Log.d("CALL ACTION","DEFAULT PLAN");
    		    		AudioManager am = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    		    		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		    		
    	    			Message message= Message.obtain();
    	    	        
    	    			StringBuilder sb = new StringBuilder();
    	    			sb.append("Call from: "+incomingNumber+" was not handled");
    	    	        Bundle contextData = new Bundle();
    	    	        contextData.putString("events", sb.toString());
    	    			message.setData(contextData); 
    	    			AgentStartFragment.handler.sendMessage(message);
    		    	}
            }
        }

    }
}
