package com.mobileagent.app.services.event_listeners.message_handler;

import com.mobileagent.app.user_interface.fragments.AgentStartFragment;
import com.mobileagent.app.services.reactive_planning_agents.MessageAgent;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class RingMessageHandler extends MessageHandler {

	@Override
	public void handleMessage(String recipient, Context c) {
		if(MessageAgent.messagePlanToExecute.getAction().compareTo("Ring")==0){
    		Log.d("MESSAGE ACTION","Ring");
    		AudioManager am = (AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
    		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    		
			Message message= Message.obtain();
			StringBuilder sb = new StringBuilder();
			sb.append("Message from: "+recipient+" was handled");
	        Bundle contextData = new Bundle();
	        contextData.putString("events", sb.toString());
			message.setData(contextData); 
			AgentStartFragment.handler.sendMessage(message);
    	}else if(successor != null){
    		successor.handleMessage(recipient, c);
    	}
	}

}
