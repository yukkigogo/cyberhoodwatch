package com.sociam.android.user;

import java.util.HashMap;

import com.sociam.android.R;
import com.sociam.android.Tag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ToggleButton;

public class UserRegisterActivity extends FragmentActivity {

	public static final int USER_REG =1;
	private ViewPager pager;
	private UserRegisterFragmentPagerAdapter adapter;
	
	private SharedPreferences sp; 
    HashMap<Tag,ToggleButton> btns;

	String username=null;
	String password=null;
	String email="";
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
		setContentView(R.layout.new_register_main);
	
		sp = PreferenceManager.getDefaultSharedPreferences(this);
        btns = new HashMap<Tag, ToggleButton>();

		setPagerView();
		
	}


	private void setPagerView() {
		pager = (ViewPager) findViewById(R.id.pager_reg);
		adapter = new UserRegisterFragmentPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);
		
		
	}

	public ViewPager getPager(){
		return this.pager;
	}

	public HashMap<Tag, ToggleButton> getBtns(){
		return this.btns;
	}
}
