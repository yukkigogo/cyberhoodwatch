package com.sociam.android.report;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.model.Crime;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * This class is first page of report page. It ask the user if the incident is emergency or not.
 * 
 * @author yukki
 *@version 1
 */
public class ReportEmargencyQuestion extends Fragment {
	
	ViewPager pager;
	View view;
	DataApplication dapp;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.report_emagency_question, container, false); 	
		dapp = ((ReportActivity) getActivity()).getDataApplication();
		
		TextView title = (TextView) view.findViewById(R.id.text_emargency);
		title.setTypeface(dapp.getTypefaceRobothin());
		Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.blink);
		title.startAnimation(animation);
		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
		dapp = ((ReportActivity) getActivity()).getDataApplication();
		
		setButtons(view);
		
		
		return view;
	}
	private void setButtons(View view) {
		
		Button yes = (Button) view.findViewById(R.id.emargency_yes);
		yes.setTypeface(dapp.getTypefaceRobothin());
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Call Police?")
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), " Calling police (Not implemented!) .... "
								, Toast.LENGTH_SHORT).show();
						
						
					}
				}).setNegativeButton("NO", 
						new DialogInterface.OnClickListener() {						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing
						}
				}).show();
				
				
			}
		});
		
		
		Button no = (Button) view.findViewById(R.id.emargency_no);
		no.setTypeface(dapp.getTypefaceRobothin());
		no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem()+1);
				
			}
		});
		
	}

	
	
}
