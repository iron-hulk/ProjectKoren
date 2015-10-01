package com.mobileagent.app.services.event_listeners.message_handler;

import com.mobileagent.app.fragments.AgentStartFragment;
import com.mobileagent.app.reactive_planning_agents.MessageAgent;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class DefaultMessageHandler extends MessageHandler {

	@Override
	public void handleMessage(String recipient, Context c) {
		if(MessageAgent.messagePlanToExecute.getAction().compareTo("default")==0){
    		Log.d("MESSAGE ACTION","DEFAULT");
    		AudioManager am = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
    		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    		
			Message message= Message.obtain();
	        
			StringBuilder sb = new StringBuilder();
			sb.append("Message from: "+recipient+" was not handled");
	        Bundle contextData = new Bundle();
	        contextData.putString("events", sb.toString());
			message.setData(contextData); 
			AgentStartFragment.handler.sendMessage(message);
    	}else if(successor != null){
    		successor.handleMessage(recipient, c);
    	}
	}

}
