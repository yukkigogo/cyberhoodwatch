package com.sociam.android.report;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class SummaryListFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String[] details = { "Picture : YES", "Category : ASB", 
			"Place : Here", "number of Suspects : 1",
            "Suspects gender : male", "Age of suspects : 20-25", 
            "Ethics : white", "Dress colour : blue" };
		
		
		   ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				        getActivity(),
				        android.R.layout.simple_list_item_1,
				        details);

		   setListAdapter(adapter);
	
	}


}
