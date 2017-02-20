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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobileagent.app.R;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class PhoneCallMotionSettings extends Fragment {
	DBService<CallEvent> dbService;
	Context context;
	
	public PhoneCallMotionSettings() {
		super();
	}

	public PhoneCallMotionSettings(Context c){
		dbService = new CallEventImpl(c);
		context = c;
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

	        final View rootView = inflater.inflate(R.layout.phone_call_motion, container, false);
	        final EditText edtSpeedGreater = (EditText)rootView.findViewById(R.id.edtSpeedGreater);
	        final RadioGroup group = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
	        final EditText edtSpeedMessage = (EditText)rootView.findViewById(R.id.edtSpeedMessage);
	        final Spinner actionMotionSpinner = (Spinner)rootView.findViewById(R.id.actionMotionSpinner);
	        
	        Button btnSaveSetting = (Button)rootView.findViewById(R.id.btnMotionSave);
	        btnSaveSetting.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(edtSpeedGreater.getText().toString().equals("")||edtSpeedGreater.getText().toString()==null){
						Toast.makeText(context, "Please select a speed to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
					
					int btnID = group.getCheckedRadioButtonId();
					
					if(btnID == 0){
						Toast.makeText(context, "Please select an operator to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
					if(edtSpeedMessage.getText().toString().equals("")||edtSpeedMessage.getText().toString()==null){
						Toast.makeText(context, "Please enter a message, to complete plan.", Toast.LENGTH_LONG).show();
						return;
					}
			        final RadioButton selected = (RadioButton)rootView.findViewById(btnID);
					//get values and save to database
					HashMap<String,String> values = new HashMap<String, String>();
			        values.put("attribute", "motion");
			        values.put("action", actionMotionSpinner.getSelectedItem().toString());
			        values.put("value1", edtSpeedGreater.getText().toString());
			        values.put("value2", selected.getText().toString());
			        values.put("message", edtSpeedMessage.getText().toString());
			        
					
					CallEvent m = AppFactory.buildCallEvent(values);
					dbService.createContext(m);
					
					edtSpeedGreater.setText("");
					edtSpeedMessage.setText("");
					
					Toast.makeText(context, "Phone Call Motion Plan Saved.", Toast.LENGTH_SHORT).show();
				}
			});
	        
	        return rootView;
	    }
}
