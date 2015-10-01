package com.mobileagent.app.services.event_listeners.message_handler;

import android.content.Context;

public abstract class MessageHandler {

	protected MessageHandler successor;
	
	public void setSuccessor(MessageHandler successor){
		this.successor = successor;
	}
	
	public abstract void handleMessage(String recipient,Context c);
}
