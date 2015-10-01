package com.mobileagent.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileagent.app.R;

public class WelcomeFragment extends Fragment {
	
	public WelcomeFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	}
	
	@Override 
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.welcome_fragment, container, false);

        return rootView;
	}
}
