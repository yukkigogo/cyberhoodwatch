package com.sociam.android.report;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.model.Crime;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * This class is to ask the user whether the user is victim or witness, or he/she needs help. 
 * 
 * @author yukki
 *@version 1
 */
public class ReportHappenWho extends Fragment {
	
	ViewPager pager;
	ToggleButton[] btns = new ToggleButton[3];
	Crime currentCrime;
	View view;
	DataApplication dapp;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
																Bundle savedInstanceState) {
	 
		view = inflater.inflate(R.layout.report_victim_whois, container, false);
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		pager = (ViewPager) getActivity().findViewById(R.id.pager);
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		dapp = ((ReportActivity) getActivity()).getDataApplication();
		setBtns();
		
		
	}

	private void setBtns() {
		
		btns[0] = (ToggleButton) view.findViewById(R.id.report_victim_btn1);
		btns[1] = (ToggleButton) view.findViewById(R.id.report_victim_btn2);
		btns[2] = (ToggleButton) view.findViewById(R.id.report_victim_btn3);
		
		for(int i=0; i<3;i++){
			btns[i].setTypeface(dapp.getTypefaceRobothin());
			setToggleListeners(btns[i], i);
		}
		
	}
	
	private void setToggleListeners(final ToggleButton btn,final int num){
		
		
		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {
						if(isChecked){
							switch (num) {
							case 0:
								currentCrime.setHappenwho("tome");
								break;
							case 1:
								currentCrime.setHappenwho("saw");
							break;
							case 2 :
								currentCrime.setHappenwho("help");
							default:	
								break;
							}
							
							for(int i=0; i<3;i++){
								if(i!=num) btns[i].setChecked(false);
							}
							
							if(num!=0)
							   pager.setCurrentItem(pager.getCurrentItem()+1);
							else
								pager.setCurrentItem(pager.getCurrentItem()+2);
						}else if(!isChecked){
							currentCrime.setHappenwho("NULL");
						}
							
						
					}
				});
	}

	
	
	
	
	
}
