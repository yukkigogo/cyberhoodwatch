package com.sociam.android.report;

import java.util.ArrayList;

import com.sociam.android.Crime;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class SummaryListFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Crime currentCrime = ((ReportActivity) getActivity()).getCrime();
		 

		ArrayList<String> details = new ArrayList<String>();
					details.add("Picture : "+ currentCrime.getFilepath());
					details.add("Category : "+currentCrime.getCategory());
					
		if(currentCrime.getisCategoryText()) 
			details.add(currentCrime.getCategoryText());			
					
					
		
	   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				        getActivity(),
				        android.R.layout.simple_list_item_1,
				        details);

	   setListAdapter(adapter);
	
	}


}
