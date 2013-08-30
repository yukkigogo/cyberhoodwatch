package com.sociam.android.report;

import java.util.ArrayList;

import com.google.android.gms.internal.cu;
import com.sociam.android.Crime;
import com.sociam.android.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class SummaryListFragment extends ListFragment {
	
	Crime currentCrime;
	ArrayAdapter<String> adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		currentCrime = ((ReportActivity) getActivity()).getCrime();
		setAdapter(); 
	
	}

	
	private void setAdapter(){
		
		ArrayList<String> details = new ArrayList<String>();
		
		
		details.add("Picture : "+ (currentCrime.getFilepath() !=null ? "Yes" : "No"));

		details.add("Category : "+currentCrime.getCategory());
		if(currentCrime.getisCategoryText()) 
			details.add(currentCrime.getCategoryText());			
		
		
		
		if(currentCrime.getLocationLatLng()){
			details.add("Location : Here");
		}else if(currentCrime.getIsAddress()){
			details.add("Location : "+currentCrime.getAddress());
		}

		
		
		
		details.add("Time and Date : " + 
		(currentCrime.getIsNow() == true ? "Now" : currentCrime.getDate()));
		if(currentCrime.getIsDateText())
			details.add(currentCrime.getDateText());
		
		
		String severity="Not Serious";
		switch (currentCrime.getSeverity()){
		case 88 :
			break;
		case 1 :
			break;
		case 2:
			severity = "Serious";
			break;
		case 3 :
			severity = "Very Serious";
			break;
		case 4:
			severity = "Extremely Serious";
			break;
			
		}		
		details.add("How Serious? : " + severity);
		
				
		adapter = new ArrayAdapter<String>(
			        getActivity(),R.layout.list_row,R.id.list1,details);
		
		setListAdapter(adapter);
		
	}
	

}
