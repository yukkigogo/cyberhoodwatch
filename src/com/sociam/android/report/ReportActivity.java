package com.sociam.android.report;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.sociam.android.R;

public class ReportActivity extends FragmentActivity {

	ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_main);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyFragmentStatePagerAdapter(getSupportFragmentManager()));
	}
	
	
	
}
