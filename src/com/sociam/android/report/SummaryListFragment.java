package com.sociam.android.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.android.gms.internal.cu;
import com.sociam.android.Crime;
import com.sociam.android.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SummaryListFragment extends ListFragment {
	
	Crime currentCrime;
	ArrayAdapter<String> adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		//setAdapter();
		setListAdapter(((ReportActivity) getActivity()).setAdapter()); 
		
		//getActivity().getSupportFragmentManager().beginTransaction().add(this, "SummaryFrag").commit();

	}


	
//	private void setAdapter(){
//		
//		ArrayList<String> details = new ArrayList<String>();
//		
//		
//		details.add("Picture : "+ (currentCrime.getFilepath() !=null ? "Yes" : "No"));
//		details.add("Category : "+currentCrime.getCategory());
//		if(currentCrime.getisCategoryText()) 
//			details.add(currentCrime.getCategoryText());			
//		
//		
//		
//		if(currentCrime.getLocationLatLng()){
//			details.add("Location : Here");
//		}else if(currentCrime.getIsAddress()){
//			details.add("Location : "+currentCrime.getAddress());
//		}
//
//		
//		
//		if(currentCrime.getIsNow() == true)
//			details.add("Time and Date : " +  "Now"); 
//		else { 
//			Time t = currentCrime.getDate();
//			String str =t.format("%d-%m-%Y %H:%M");
//			details.add("Time and Date : "+ str );
//		}
//		
//		if(currentCrime.getIsDateText())
//			details.add(currentCrime.getDateText());
//		
//		
//		String severity="Not Serious";
//		switch (currentCrime.getSeverity()){
//		case 88 :
//			break;
//		case 1 :
//			break;
//		case 2:
//			severity = "Serious";
//			break;
//		case 3 :
//			severity = "Very Serious";
//			break;
//		case 4:
//			severity = "Extremely Serious";
//			break;
//			
//		}		
//		details.add("How Serious? : " + severity);
//		
//				
//		adapter = new ArrayAdapter<String>(
//			        getActivity(),R.layout.list_row,R.id.list1,details);
//		
//		setListAdapter(adapter);
//		
//	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}


	

}
