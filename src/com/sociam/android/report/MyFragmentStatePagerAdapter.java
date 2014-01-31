package com.sociam.android.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;


/**
 * This class is control the sliding function in the report page.
 * 
 * @see android.support.v4.app.FragmentStatePagerAdapter
 * @author yukki
 * @version 1
 */
public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
	// don't forget to change report activity as well
	/**
	 * number of page
	 */
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
			return new ReportEvidence();
		case 3: 
			return new ReportCategory();
		case 4: 
			return new ReportCategory2();
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
