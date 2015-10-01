package com.mobileagent.app.fragments;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileagent.app.R;
import com.mobileagent.app.reactive_planning_agents.CallAgent;
import com.mobileagent.app.reactive_planning_agents.MessageAgent;
import com.mobileagent.app.repository.DBService;
import com.mobileagent.app.repository.impl.CallEventImpl;
import com.mobileagent.app.repository.impl.ContextServiceImpl;
import com.mobileagent.app.repository.impl.MessageEventImpl;
import com.mobileagent.app.services.context_services.ContextObservable;
import com.mobileagent.app.services.event_listeners.ReceiverService;
import com.mobileagent.app.utilities.CallEvent;
import com.mobileagent.app.utilities.ContextObject;
import com.mobileagent.app.utilities.MessageEvent;


public class AgentStartFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

	public static Handler handler;

    private static DBService<ContextObject> dbService;
    private static DBService<CallEvent> dbCallService;
    private static DBService<MessageEvent> dbMessageService;

	private ScheduledExecutorService scheduledThreadPool;
	private static AudioManager am;
	private static int ringerMode;

    private static Context context;
	ViewGroup rootView;
	Switch agentButton;
	Intent receiver;

	public AgentStartFragment(){
		super();

		
	}

	public static AgentStartFragment setArguements(Context c){
		AgentStartFragment a = new AgentStartFragment();
		dbService = new ContextServiceImpl(c);
		dbCallService = new CallEventImpl(c);
		dbMessageService = new MessageEventImpl(c);
		context = c;
		am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		ringerMode = am.getRingerMode();

        return a;
	}

	@Override 
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		rootView = (ViewGroup) inflater.inflate(R.layout.agent_start, container, false);
		
		TextView txtDisplayContext = (TextView)rootView.findViewById(R.id.txtDisplayContext);
        
        for(CallEvent c:dbCallService.getAllContexts())
        	txtDisplayContext.append("\n"+c.toString());
		
        for(MessageEvent m:dbMessageService.getAllContexts())
        	txtDisplayContext.append("\n\n"+m.toString());
        
		agentButton = (Switch) rootView.findViewById(R.id.agentSwitch);
        agentButton.setOnCheckedChangeListener(this);
        
		final TextView txtDisplayEvents = (TextView)rootView.findViewById(R.id.txtDisplayEvents);
		handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            	txtDisplayEvents.append("\n"+msg.getData().getCharSequence("events"));
            }
        };
        return rootView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if(isChecked) {
//			if(cms.hasConnection(context)){
				Toast.makeText(context, "STARTING AGENT", Toast.LENGTH_SHORT).show();
				Log.d("ContextObservable","registerReceivers()");

				final CallAgent ca = new CallAgent(context);
				final MessageAgent ma = new MessageAgent(context);

				final ContextObservable cs = new ContextObservable(context);
				cs.addObserver(ca);
				cs.addObserver(ma);

				scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
				scheduledThreadPool.schedule(cs,0,TimeUnit.SECONDS);

				receiver = new Intent(context, ReceiverService.class);
		        getActivity().startService(receiver);
//			}else{
//				AlertDialog.Builder builder = new AlertDialog.Builder(context)
//	    		.setTitle("The agent has no connectivity, Please check connectivity \nand try again.")
//	    		.setPositiveButton("Ok", new OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				});
//				builder.show();
//				agentButton.setActivated(false);
//			}
        }else{
        	am.setRingerMode(ringerMode);
    		
        	AlertDialog.Builder builder = new AlertDialog.Builder(context)
        		.setTitle("Would you like to remove all plans?")
        		.setPositiveButton("Delete", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						removeStoredEvents();
						clearUI();
						endAgent();
					}
				}).setNegativeButton("Cancel", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dbService.deleteAllContexts();
						clearUI();
						endAgent();
					}
				});
        	builder.show();
    		Toast.makeText(context, "Agent Mode Stopped", Toast.LENGTH_SHORT).show();
        }
	}
	
	//remove all context and events
	private void removeStoredEvents(){
		dbCallService.deleteAllContexts();
		dbService.deleteAllContexts();
	}
	
	private void clearUI(){
		TextView txtDisplayContext = (TextView)rootView.findViewById(R.id.txtDisplayContext);
		txtDisplayContext.setText("");
	}
	
	private void endAgent(){
		scheduledThreadPool.shutdownNow();
		getActivity().stopService(receiver);
	}
}
