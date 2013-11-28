package com.sociam.android.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class UserRegisterFragmentPagerAdapter extends FragmentStatePagerAdapter {

	final int PAGE_COUNT=2;
	
	public UserRegisterFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		
		switch (i) {
		case 0:
			return new UserRegisterFragment();
			
		case 1:
			return new TagRegisterFragment();
			
		default:
			return null;
		
		}
		
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

}
