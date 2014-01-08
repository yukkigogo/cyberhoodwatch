package com.sociam.android.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;



public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
	// don't forget to change report activity as well
	final int PAGE_COUNT = 10;
	
	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		switch(i){	
		case 0: 		
			return new ReportEmargencyQuestion();

		case 1: 

			return new ReportHappenWho();

		case 2: 
			ReportEvidence rp1 = new ReportEvidence();
			return rp1;
		case 3: 
			ReportCategory rp2 = new ReportCategory();
			return rp2;
		case 4: 
			ReportCategory2 rp3 = new ReportCategory2();
			return rp3;		
		case 5:
			return new ReportLocation();
		case 6:
			return new ReportDateTime();		
		case 7:
			return new ReportSeverity();
		case 8: 
			return new ReportAnonymous(); 
		case 9:			
			return new ReportSummary();		
		
		default:
			return null;
		}
		
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}
	

	
}
