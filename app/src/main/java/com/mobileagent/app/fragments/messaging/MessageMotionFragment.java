package com.mobileagent.app.fragments.messaging;

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
import com.mobileagent.app.repository.impl.MessageEventImpl;
import com.mobileagent.app.utilities.MessageEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class MessageMotionFragment extends Fragment{

	DBService<MessageEvent> dbService;
	Context context;
	
	public MessageMotionFragment() {
		super();
	}

	public MessageMotionFragment (Context c){
		dbService = new MessageEventImpl(c);
		context = c;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.message_motion_settings, container, false);
        final EditText edtSpeedGreater = (EditText)rootView.findViewById(R.id.edtMMotionSpeedGreater);
        final RadioGroup group = (RadioGroup)rootView.findViewById(R.id.radioMMotionGroup);

        final EditText edtSpeedMessage = (EditText)rootView.findViewById(R.id.edtMMotionSpeedMessage);
        final Spinner actionMotionSpinner = (Spinner)rootView.findViewById(R.id.actionMMotionSpinner);
        
        Button btnSaveSetting = (Button)rootView.findViewById(R.id.btnMMotionSave);
        btnSaveSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//get values and save to database
				if(edtSpeedGreater.getText().toString().equals("")||edtSpeedGreater.getText().toString()==null){
						Toast.makeText(context, "Please enter a speed, to complete plan.", Toast.LENGTH_LONG).show();
						return;
				}
		        int btnID = group.getCheckedRadioButtonId();
		        if(btnID == 0){
					Toast.makeText(context, "Please select an operator to complete plan.", Toast.LENGTH_LONG).show();
					return;
				}
		        RadioButton selected = (RadioButton)rootView.findViewById(btnID);
				HashMap<String,String> values = new HashMap<String, String>();
		        values.put("attribute", "motion");
		        values.put("action", actionMotionSpinner.getSelectedItem().toString());
		        values.put("value1", edtSpeedGreater.getText().toString());
		        values.put("value2", selected.getText().toString());
		        values.put("message", edtSpeedMessage.getText().toString());
		        
		       
				if(edtSpeedMessage.getText().toString().equals("")||edtSpeedMessage.getText().toString()==null){
					Toast.makeText(context, "Please enter a message, to complete plan.", Toast.LENGTH_LONG).show();
					return;
				}
				
				MessageEvent m = AppFactory.buildMessageEvent(values);
				dbService.createContext(m);
				
				edtSpeedGreater.setText("");
				edtSpeedMessage.setText("");
				
				Toast.makeText(context, "Message Motion Plan Saved.", Toast.LENGTH_SHORT).show();
			}
		});
        
        return rootView;
    }
}
