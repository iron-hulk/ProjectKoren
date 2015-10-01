package com.mobileagent.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mobileagent.app.MessageSettings;
import com.mobileagent.app.PhoneCallSettings;
import com.mobileagent.app.R;

public class ActionPlanSelectFragment extends Fragment implements OnClickListener{

	
	public ActionPlanSelectFragment() {
		super();
	}

	@Override 
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.action_plan_selection, container, false);
		
		ImageButton btnPhoneCall = (ImageButton)rootView.findViewById(R.id.btnCalls);
		btnPhoneCall.setOnClickListener(this);
		ImageButton btnMessages = (ImageButton)rootView.findViewById(R.id.btnMessages);
		btnMessages.setOnClickListener(this);
        return rootView;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnCalls: 
				Intent i = new Intent(v.getContext(),PhoneCallSettings.class);
				startActivity(i);
				break;
			case R.id.btnMessages: 
				Intent m = new Intent(v.getContext(),MessageSettings.class);
				startActivity(m);
				break;
		}
		
	}
}
