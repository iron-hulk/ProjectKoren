package com.mobileagent.app.user_interface.fragments.phonecalls;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobileagent.app.R;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.factory.AppFactory;


public class PhoneCallLocationSettings extends Fragment{

	DBService<CallEvent> dbService;
	Context context;
	
	public PhoneCallLocationSettings() {
		super();
	}

	public PhoneCallLocationSettings(Context c){
		dbService = new CallEventImpl(c);
		context = c;
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.location_settings, container, false);
	        final EditText edtLatitude = (EditText)rootView.findViewById(R.id.edtLatitude);
	        final EditText edtLongitude = (EditText)rootView.findViewById(R.id.edtLongitude);
	        final EditText edtLocationMessage = (EditText)rootView.findViewById(R.id.edtMessage);
	        final Spinner actionLocationSpinner = (Spinner)rootView.findViewById(R.id.actionSpinner);
	        
	        Button btnSaveSetting = (Button)rootView.findViewById(R.id.btnSave);
	        btnSaveSetting.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//get values and save to database
					HashMap<String,String> values = new HashMap<String, String>();
			        values.put("attribute", "location");
			        values.put("action", actionLocationSpinner.getSelectedItem().toString());
			        values.put("value1", edtLatitude.getText().toString());
			        values.put("value2", edtLongitude.getText().toString());
			        values.put("message", edtLocationMessage.getText().toString());
			        
			        if(edtLatitude.getText().toString().equals("")||edtLatitude.getText().toString()==null){
						Toast.makeText(context, "Please enter a latitude, to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
					if(edtLongitude.getText().toString().equals("")||edtLongitude.getText().toString()==null){
						Toast.makeText(context, "Please enter a longitude, to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
					if(edtLocationMessage.getText().toString().equals("")||edtLocationMessage.getText().toString()==null){
						Toast.makeText(context, "Please enter a message, to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
					
					CallEvent m = AppFactory.buildCallEvent(values);
					dbService.createContext(m);
					
					edtLatitude.setText("");
					edtLongitude.setText("");
					edtLocationMessage.setText("");
					
					Toast.makeText(context, "Phone Call Location Plan Saved.", Toast.LENGTH_SHORT).show();
				}
			});
	        
	        return rootView;
	    }
}
