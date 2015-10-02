package com.mobileagent.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.mobileagent.app.fragments.ActionPlanSelectFragment;
import com.mobileagent.app.fragments.AgentStartFragment;
import com.mobileagent.app.fragments.WelcomeFragment;

public class HomeActivity extends Activity {

	private Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
	            case 0: return new WelcomeFragment();
	            case 1: return new ActionPlanSelectFragment();
	            case 2: return AgentStartFragment.setArguements(context);
	            default: return new WelcomeFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }       
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
