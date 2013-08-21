package com.sociam.android.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	final int PAGE_COUNT = 4;
	
	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		switch(i){	
		case 0: 
			Evidence rp1 = new Evidence();
			return rp1;
		case 1: 
			ReportFragment2 rp2 = new ReportFragment2();
			return rp2;
		case 2: 
			ReportFragment3 rp3 = new ReportFragment3();
			return rp3;

		case 3: 
			ReportFragment7 rp7 = new ReportFragment7();
			return rp7;

		
		default:
			return new Evidence();
		}
		
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
	

	
}
