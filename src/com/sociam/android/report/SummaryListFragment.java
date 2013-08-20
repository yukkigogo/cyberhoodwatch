package com.sociam.android.report;

import com.sociam.android.Crime;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class SummaryListFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Crime currentCrime = ((ReportActivity) getActivity()).getCrime();
		String[] details = { "Picture : "+ currentCrime.getFilepath(), 
				"Category : "+currentCrime.getCategory(), 
			"Place : Here", "number of Suspects : 1",
            "Suspects gender : male", "Age of suspects : 20-25", 
            "Ethics : white", "Dress colour : blue" ,"test test",
            "test test test"};
		
		
		   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				        getActivity(),
				        android.R.layout.simple_list_item_1,
				        details);

		   setListAdapter(adapter);
	
	}


}
