package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ReportSummary extends Fragment {
	
	ListView lv;
	Crime currentCrime;
	Button btnSubmit;
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_summary, container, false);

		
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.smy);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}	
		
		setBtn(view);
		
		return view;
		
	}

	
	private void setBtn(View view){
		
		btnSubmit = (Button) view.findViewById(R.id.smybtn);
		String android_id = android.provider.Settings.Secure.getString(
				 getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// show dialog for comfimation and upload
				new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Do you want to upload this incidence?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// upload to the server 
						
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing						
					}
				}).show();
				
			}
		});
	}
	
	
	
}
