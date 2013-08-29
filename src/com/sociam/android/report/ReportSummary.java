package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ReportSummary extends Fragment {
	
	ListView lv;
	Crime currentCrime;
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_summary, container, false);
		//setup listView
//		summarySetup(view);
		
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.smy);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}	
			   
		
		return view;
		
	}


	
	
}
