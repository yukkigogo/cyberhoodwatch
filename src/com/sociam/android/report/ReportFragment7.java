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

public class ReportFragment7 extends Fragment {
	
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
		String[] details = { "Picture : YES", "Category : ASB", 
				"Place : Here", "number of Suspects : 1",
                "Suspects gender : male", "Age of suspects : 20-25", 
                "Ethics : white", "Dress colour : blue" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1);
		Log.e("sociam", "read my message");
		
	//	lv = (ListView) view.findViewById(R.id.ListSummary);
		lv.setAdapter(adapter);
		
	}
	
	
}
