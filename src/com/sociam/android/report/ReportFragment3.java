package com.sociam.android.report;

import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportFragment3 extends Fragment {
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		return inflater.inflate(
			R.layout.report_fragment2, container, false);
	}
}
