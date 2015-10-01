package com.mobileagent.app.services.event_listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.mobileagent.app.reactive_planning_agents.MessageAgent;
import com.mobileagent.app.services.event_listeners.message_handler.DefaultMessageHandler;
import com.mobileagent.app.services.event_listeners.message_handler.RingMessageHandler;
import com.mobileagent.app.services.event_listeners.message_handler.SilenceMessageHandler;
import com.mobileagent.app.services.event_listeners.message_handler.UrgentMessageHandler;
import com.mobileagent.app.services.event_listeners.message_handler.VibrateMessageHandler;


/**
 * Created by ironhulk on 2014/08/04.
 */
public class IncomingMessagesReceiver extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private RingMessageHandler rmh;
    private SilenceMessageHandler smh;
    private UrgentMessageHandler umh;
    private VibrateMessageHandler vmh;
    private DefaultMessageHandler dmh;
    
    public IncomingMessagesReceiver(){
    	rmh = new RingMessageHandler();
    	smh = new SilenceMessageHandler();
    	umh = new UrgentMessageHandler();
    	vmh = new VibrateMessageHandler();
    	dmh = new DefaultMessageHandler();
    	
    	buildMessageChain();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(ACTION.compareToIgnoreCase(intent.getAction())==0){
            Object[] pduArray = (Object[])intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];

            for(int i = 0;i < pduArray.length; i++){
                messages[i] = SmsMessage.createFromPdu((byte[])pduArray[i]);

            }
            
            if(MessageAgent.messagePlanToExecute!=null){
            	smh.handleMessage(messages[0].getDisplayOriginatingAddress(), context);
        	}else{
        		Toast.makeText(context, "Agent has no plan to execute.", Toast.LENGTH_LONG).show();
        	}
        }
    }
    
    private void buildMessageChain(){
    	smh.setSuccessor(rmh);
    	rmh.setSuccessor(vmh);
    	vmh.setSuccessor(umh);
    	umh.setSuccessor(dmh);
    }
}
