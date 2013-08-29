package com.sociam.android.report;

import java.util.ArrayList;

import com.sociam.android.Crime;
import com.sociam.android.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

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
					
					details.add("Suspect(s) : "+currentCrime.getSuspects().getNum());

					
		
	   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				        getActivity(),R.layout.list_row,R.id.list1,details);

	   setListAdapter(adapter);
	   
		
	
	}


}
