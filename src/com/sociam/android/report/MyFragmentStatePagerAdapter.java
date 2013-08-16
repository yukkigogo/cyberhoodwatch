package com.sociam.android.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		switch(i){	
		case 0: 
			ReportFragment1 rp1 = new ReportFragment1();
			return rp1;
		case 1: 
			ReportFragment2 rp2 = new ReportFragment2();
			return rp2;
		case 2: 
			ReportFragment7 rp7 = new ReportFragment7();
			return rp7;

		default:
			return new ReportFragment1();
		}
		
	}

	@Override
	public int getCount() {
		return 3;
	}
	

	
}
