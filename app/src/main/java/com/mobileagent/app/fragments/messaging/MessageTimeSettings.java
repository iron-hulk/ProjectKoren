package com.mobileagent.app.fragments.messaging;

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
import com.mobileagent.app.repository.impl.MessageEventImpl;
import com.mobileagent.app.utilities.MessageEvent;
import com.mobileagent.app.utilities.factory.AppFactory;

public class MessageTimeSettings extends Fragment implements View.OnClickListener{
	DBService<MessageEvent> dbService;
	Context context;
	EditText edtStartTime;
	EditText edtEndTime;
	EditText edtMsgTimeMessage;
	Spinner actionMsgTimeSpinner;
	
	int currentHour;
	int currentMinute;
	
	public MessageTimeSettings() {
		super();
	}

	public MessageTimeSettings(Context c){
		dbService = new MessageEventImpl(c);
		context = c;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        View rootView = inflater.inflate(R.layout.message_time_settings, container, false);
        edtStartTime = (EditText)rootView.findViewById(R.id.edtMsgStartTime);
        edtEndTime = (EditText)rootView.findViewById(R.id.edtMsgEndTime);
        edtMsgTimeMessage = (EditText)rootView.findViewById(R.id.edtMsgTimeMessage);
        actionMsgTimeSpinner = (Spinner)rootView.findViewById(R.id.actionMsgTimeSpinner);
        Button btnMsgTimeSave = (Button)rootView.findViewById(R.id.btnMsgTimeSave);
        
        edtStartTime.setOnClickListener(this);
        edtEndTime.setOnClickListener(this);
        btnMsgTimeSave.setOnClickListener(this);
        
        return rootView;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.edtMsgStartTime:
				TimePickerDialog mTimer = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                			String minute = (selectedMinute<10)?"0"+selectedMinute:""+selectedMinute;
                			String hour = (selectedHour<10)?"0"+selectedHour:""+selectedHour;
							edtStartTime.setText( ""+ hour + ":" + minute);
			            }
				}, currentHour, currentMinute, true);
				mTimer.setTitle("Select Time");
				mTimer.show();
					
				break;
			case R.id.edtMsgEndTime:
				TimePickerDialog endTimer = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
				
				@Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            			String endminute = (selectedMinute<10)?"0"+selectedMinute:""+selectedMinute;
            			String hour = (selectedHour<10)?"0"+selectedHour:""+selectedHour;
            			
            			edtEndTime.setText( ""+ hour + ":" + endminute);
		            }
		        }, currentHour, currentMinute, true);
				endTimer.setTitle("Select Time");
				endTimer.show();
				break;
			case R.id.btnMsgTimeSave:
				HashMap<String,String> values = new HashMap<String, String>();
		        values.put("attribute", "time");
		        values.put("action", actionMsgTimeSpinner.getSelectedItem().toString());
		        values.put("value1", ""+edtStartTime.getText().toString());
		        values.put("value2", ""+edtEndTime.getText().toString());
		        values.put("message", edtMsgTimeMessage.getText().toString());
		        
				if(edtStartTime.getText().toString().equals("")||edtStartTime.getText().toString()==null){
					Toast.makeText(context, "Please enter a start time, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				if(edtEndTime.getText().toString().equals("")||edtEndTime.getText().toString()==null){
					Toast.makeText(context, "Please enter a end time, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				if(edtMsgTimeMessage.getText().toString().equals("")||edtMsgTimeMessage.getText().toString()==null){
					Toast.makeText(context, "Please enter a message, to complete plan.", Toast.LENGTH_LONG).show();
					break;
				}
				MessageEvent m = AppFactory.buildMessageEvent(values);
				dbService.createContext(m);
				
				edtStartTime.setText("");
				edtEndTime.setText("");
				edtMsgTimeMessage.setText("");
				
				Toast.makeText(context, "Message Time Plan Saved.", Toast.LENGTH_SHORT).show();
				break;
		}
	}
}
