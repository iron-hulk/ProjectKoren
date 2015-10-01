package com.mobileagent.app;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.mobileagent.app.fragments.messaging.MessageMotionFragment;
import com.mobileagent.app.fragments.phonecalls.PhoneCallCalendarSettings;
import com.mobileagent.app.fragments.phonecalls.PhoneCallLocationSettings;
import com.mobileagent.app.fragments.phonecalls.PhoneCallMotionSettings;
import com.mobileagent.app.fragments.phonecalls.PhoneCallTimeSettings;

public class PhoneCallSettings extends FragmentActivity implements ActionBar.TabListener{
    
	PhoneCallSettingsCollectionPagerAdapter pcsc;
	ViewPager mViewPager;
	ActionBar actionBar;
	private String[] tabs = {"Location", "Calendar", "Time", "Motion"};
	Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.phone_call_settings);
            
            pcsc = new PhoneCallSettingsCollectionPagerAdapter(getSupportFragmentManager());
            actionBar = getActionBar();
            mViewPager = (ViewPager) findViewById(R.id.callPager);
            
            mViewPager.setAdapter(pcsc);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            
            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name)
                        .setTabListener(this));
            }
            
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
    }

    public class PhoneCallSettingsCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public PhoneCallSettingsCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
	            case 0:
	            	fragment = new PhoneCallLocationSettings(c);
	            	break;
	            case 1:
	            	fragment = new PhoneCallCalendarSettings(c);
	            	break;
	            case 2:
	            	fragment = new PhoneCallTimeSettings(c);
	            	break;
	            case 3:
	            	fragment = new PhoneCallMotionSettings(c);
	            	break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
