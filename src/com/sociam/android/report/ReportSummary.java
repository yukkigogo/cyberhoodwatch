package com.sociam.android.report;

import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportSummary extends Fragment {
	
	ListView lv;
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_summary, container, false);
		//setup listView
//		summarySetup(view);
		return view;
		
	}

	private void summarySetup(View view) {
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1);
		
	//	lv = (ListView) view.findViewById(R.id.ListSummary);
		lv.setAdapter(adapter);
		
	}
	
	
}
