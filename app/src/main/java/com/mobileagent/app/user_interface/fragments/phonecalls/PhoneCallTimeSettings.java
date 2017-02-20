package com.mobileagent.app.user_interface.fragments.phonecalls;

import java.util.Calendar;
import java.util.HashMap;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobileagent.app.R;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class PhoneCallTimeSettings extends Fragment implements View.OnClickListener{

	DBService<CallEvent> dbService;
	Context context;
	
	//Views
	EditText edtCallStartTime;
	EditText edtCallEndTime;
	EditText edtCallTimeMessage;
	Spinner actionCallTimeSpinner;
	
	int currentHour;
	int currentMinute;
	public PhoneCallTimeSettings() {
		super();
	}

	public PhoneCallTimeSettings(Context c){
		dbService = new CallEventImpl(c);
		context = c;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        View rootView = inflater.inflate(R.layout.time_settings, container, false);
        edtCallStartTime = (EditText)rootView.findViewById(R.id.edtCallStartTime);
        edtCallEndTime = (EditText)rootView.findViewById(R.id.edtCallEndTime);
        edtCallTimeMessage = (EditText)rootView.findViewById(R.id.edtCallTimeMessage);
        actionCallTimeSpinner = (Spinner)rootView.findViewById(R.id.actionCallTimeSpinner);
        
        
        edtCallStartTime.setOnClickListener(this);
        edtCallEndTime.setOnClickListener(this);
        
        Button btnCallTimeSave = (Button)rootView.findViewById(R.id.btnCallTimeSave);
        btnCallTimeSave.setOnClickListener(this);
        
        return rootView;
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.edtCallStartTime:
				TimePickerDialog mTimer = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                			String minute = (selectedMinute<10)?"0"+selectedMinute:""+selectedMinute;
                			String hour = (selectedHour<10)?"0"+selectedHour:""+selectedHour;
                			edtCallStartTime.setText( ""+ hour + ":" + minute);
			            }
				}, currentHour, currentMinute, true);
				mTimer.setTitle("Select Time");
				mTimer.show();
					
				break;
			case R.id.edtCallEndTime:
				TimePickerDialog endTimer = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
					
				@Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            			String endminute = (selectedMinute<10)?"0"+selectedMinute:""+selectedMinute;
            			String hour = (selectedHour<10)?"0"+selectedHour:""+selectedHour;
            			edtCallEndTime.setText( ""+ hour + ":" + endminute);
		            }
		        }, currentHour, currentMinute, true);
				endTimer.setTitle("Select Time");
				endTimer.show();
				break;
			case R.id.btnCallTimeSave:
				HashMap<String,String> values = new HashMap<String, String>();
		        values.put("attribute", "time");
		        values.put("action", actionCallTimeSpinner.getSelectedItem().toString());
		        values.put("value1", edtCallStartTime.getText().toString());
		        values.put("value2", edtCallEndTime.getText().toString());
		        values.put("message", edtCallTimeMessage.getText().toString());
		        
		        if(edtCallStartTime.getText().toString().equals("")||edtCallStartTime.getText().toString()==null){
					Toast.makeText(context, "Please enter a start time, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				if(edtCallEndTime.getText().toString().equals("")||edtCallEndTime.getText().toString()==null){
					Toast.makeText(context, "Please enter a end time, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				if(edtCallTimeMessage.getText().toString().equals("")||edtCallTimeMessage.getText().toString()==null){
					Toast.makeText(context, "Please enter a message, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				CallEvent m = AppFactory.buildCallEvent(values);
				dbService.createContext(m);
				
				edtCallStartTime.setText("");
				edtCallEndTime.setText("");
				edtCallTimeMessage.setText("");
				Toast.makeText(context, "Phone Call Time Plan Saved.", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
