package com.mobileagent.app.services.event_listeners;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ReceiverService extends Service {

	private	IncomingCallReceiver icr;
	private IncomingMessagesReceiver imr;
	private IntentFilter callFilter;
	private IntentFilter messageFilter;
	
	@Override
    public void onCreate(){
        Log.d("ReceiverService onCreateCommand", "In On Create Command");
        icr = new IncomingCallReceiver();
		imr  = new IncomingMessagesReceiver();
		
		callFilter = new IntentFilter("android.intent.action.PHONE_STATE");
		messageFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    }
	 
	@Override
    public int onStartCommand(Intent i,int flags,int startId){
        Log.d("ReceiverService onStartCommand","In On Start Command");
        this.registerReceiver(imr,messageFilter);
        this.registerReceiver(icr,callFilter);
        return(START_NOT_STICKY);
    }

    @Override
    public void onDestroy(){
        Log.d("ReceiverService onDestroy", "In On Destroy");
        this.unregisterReceiver(imr);
		this.unregisterReceiver(icr);
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
