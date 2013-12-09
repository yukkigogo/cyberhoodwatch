package com.sociam.android.user;

import com.sociam.android.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class UserRegisterActivity extends FragmentActivity {

	public static final int USER_REG =1;
	private ViewPager pager;
	private UserRegisterFragmentPagerAdapter adapter;
	
	private SharedPreferences sp; 
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
		setContentView(R.layout.new_register_main);
	
		sp = PreferenceManager.getDefaultSharedPreferences(this);

		setPagerView();
		setFooter();
		
	}


	private void setPagerView() {
		pager = (ViewPager) findViewById(R.id.pager_reg);
		adapter = new UserRegisterFragmentPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);
		
		
	}


	private void setFooter() {
		
	}
	
}
