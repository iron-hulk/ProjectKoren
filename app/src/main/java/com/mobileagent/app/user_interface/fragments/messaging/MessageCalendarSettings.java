package com.mobileagent.app.user_interface.fragments.messaging;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobileagent.app.R;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.MessageEventImpl;
import com.mobileagent.app.utilities.MessageEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class MessageCalendarSettings extends Fragment implements View.OnClickListener{
	
	DBService<MessageEvent> dbService;
	Context context;
	
	Spinner actionMsgCalSpinner;
	
	public MessageCalendarSettings(Context c) {
		dbService = new MessageEventImpl(c);
		context = c;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.message_calendar_settings, container, false);
        
        Button btnSaveMessageCalPlan = (Button) rootView.findViewById(R.id.btnSaveMessageCalPlan);
        actionMsgCalSpinner = (Spinner)rootView.findViewById(R.id.actionMsgCalSpinner);
        
        btnSaveMessageCalPlan.setOnClickListener(this);
        
        return rootView;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnSaveMessageCalPlan:
				 HashMap<String,String> calValues = new HashMap<String, String>();
				 calValues.put("attribute", "calendar");
				 calValues.put("value1", "_");
				 calValues.put("value2", "_");
				 calValues.put("action", actionMsgCalSpinner.getSelectedItem().toString());
				 calValues.put("message", "_");
				 
				 MessageEvent m = AppFactory.buildMessageEvent(calValues);
				 dbService.createContext(m);
				 
				 Toast.makeText(context, "Message Calendar Plan Saved.", Toast.LENGTH_SHORT).show();
				 break;
		
		}
	}

}
