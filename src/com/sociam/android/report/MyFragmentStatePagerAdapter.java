package com.sociam.android.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;



public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	final int PAGE_COUNT = 11;
	
	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		switch(i){	
		case 0: 
			ReportEvidence rp1 = new ReportEvidence();
			return rp1;
		case 1: 
			ReportCategory rp2 = new ReportCategory();
			return rp2;
		case 2: 
			ReportCategory2 rp3 = new ReportCategory2();
			return rp3;		
		case 3:
			ReportSuspect reportSuspect = new ReportSuspect();
			return reportSuspect;
		case 4:
			return new ReportLocation();

		case 10: 
			ReportSummary rp7 = new ReportSummary();
			return rp7;

		
		default:
			return new ReportCategory();
		}
		
	}

	

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
	

	
}
