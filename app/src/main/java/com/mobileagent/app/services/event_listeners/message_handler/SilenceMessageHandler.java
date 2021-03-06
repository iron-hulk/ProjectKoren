package com.mobileagent.app.services.event_listeners.message_handler;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;

import com.mobileagent.app.user_interface.fragments.AgentStartFragment;
import com.mobileagent.app.services.reactive_planning_agents.MessageAgent;

public class SilenceMessageHandler extends MessageHandler {

	@Override
	public void handleMessage(String recipient,Context c) {
		
		if(MessageAgent.messagePlanToExecute.getAction().compareTo("Silence")==0){
			AudioManager am = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
    		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		
    		if(!MessageAgent.messagePlanToExecute.getAttribute().equals("calendar")){
    			Log.d("MESSAGE ACTION","Silence");
            	SmsManager smsManager = SmsManager.getDefault();
            	smsManager.sendTextMessage(recipient, null, MessageAgent.messagePlanToExecute.getMesage(), null , null);

            	Message message= Message.obtain();
            	StringBuilder sb = new StringBuilder();
    			sb.append("Message from: "+recipient+" was handled");
    	        Bundle contextData = new Bundle();
    	        contextData.putString("events", sb.toString());
    			message.setData(contextData); 
    			AgentStartFragment.handler.sendMessage(message);
    		}else{
    			Log.d("MESSAGE ACTION","Silence Calendar");
    			Message message= Message.obtain();
    			StringBuilder sb = new StringBuilder();
    			sb.append("Message from: "+recipient+" was handled");
    	        Bundle contextData = new Bundle();
    	        contextData.putString("events", sb.toString());
    			message.setData(contextData); 
    			AgentStartFragment.handler.sendMessage(message);
    		}
		}else if(successor != null){
    		successor.handleMessage(recipient, c);
		}
	}

}
