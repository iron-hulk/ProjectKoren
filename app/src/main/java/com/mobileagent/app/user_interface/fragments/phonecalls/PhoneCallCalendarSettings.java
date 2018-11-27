package com.mobileagent.app.user_interface.fragments.phonecalls;

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
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class PhoneCallCalendarSettings extends Fragment implements View.OnClickListener{
	
	DBService<CallEvent> dbService;
	Context context;
	
	Spinner actionCalCallSpinner;
	
	public PhoneCallCalendarSettings(Context c) {
		dbService = new CallEventImpl(c);
		context = c;
	}

	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.calendar_settings, container, false);
	        Button btnSaveCalPlan = (Button) rootView.findViewById(R.id.btnSaveCallCalPlan);
	        actionCalCallSpinner = (Spinner)rootView.findViewById(R.id.actionPersonSpinner);
	        
	        btnSaveCalPlan.setOnClickListener(this);
	        return rootView;
	    }

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.btnSaveCallCalPlan:
				 HashMap<String,String> calValues = new HashMap<String, String>();
				 calValues.put("attribute", "calendar");
				 calValues.put("value1", "_");
				 calValues.put("value2", "_");
				 calValues.put("action", actionCalCallSpinner.getSelectedItem().toString());
				 calValues.put("message", "_");
				 
				 CallEvent m = AppFactory.buildCallEvent(calValues);
				 dbService.createContext(m);
				 
				 Toast.makeText(context, "Phone Call Calendar Plan Saved.", Toast.LENGTH_SHORT).show();
				 break;
		}
	}
}
