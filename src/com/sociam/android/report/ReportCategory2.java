package com.sociam.android.report;

import com.google.android.gms.internal.cu;
import com.sociam.android.Crime;
import com.sociam.android.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


public class ReportCategory2 extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	String cat1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		Log.e("sociam","cat2");
		return inflater.inflate(R.layout.report_category2, container, false);
	}

	public void onStart() {
		super.onStart();
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		cat1=currentCrime.getCategory();
		Log.e("sociam","cat1");
		
		setBtns();
		initBtns();
		//setTexts();			
	}
	
	public void setall(){
		setBtns();
		initBtns();
		//setTexts();
	}
	
	private void initBtns() {
		sbtn2 = false;
		sbtn3 = false;
		sbtn4 = false;
		sbtn5 = false;
		
	}

	private void setBtns() {
	  btn1 = (Button) getActivity().findViewById(R.id.frag3midBtn);
	  btnS = (Button) getActivity().findViewById(R.id.frag3goSummary);
	  btnD = (Button) getActivity().findViewById(R.id.frag3description);
	  
	  btn2 = (ToggleButton) getActivity().findViewById(R.id.frag3TopBtn);
	  btn3 = (ToggleButton) getActivity().findViewById(R.id.frag3RightBtn);
	  btn4 = (ToggleButton) getActivity().findViewById(R.id.frag3bottomBtn);
	  btn5 = (ToggleButton) getActivity().findViewById(R.id.frag3LeftBtn);
	  
	  
	  

	  
	
	
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	
	}
	
	
	private void setTexts(){
		
		  // the text of button set dynamically
		  if(cat1=="Violent"){
			  btn2.setTextOn("Damage");
			  btn2.setTextOff("Damage");
			  btn3.setTextOn("Attack");
			  btn3.setTextOff("Attack");
			  btn4.setTextOn("Other");
			  btn4.setTextOff("Other");
			  btn5.setTextOn("Rape");
			  btn5.setTextOff("Rape");
			  Log.e("sociam","why cant read?");
			  
			}else if(cat1=="Theft"){
			  btn2.setTextOn("Robbery");
			  btn2.setTextOff("Robbery");
			  btn3.setTextOn("Bike");
			  btn3.setTextOff("Bike");
			  btn4.setTextOn("Other");
			  btn4.setTextOff("Other");
			  btn5.setTextOn("Shop");
			  btn5.setTextOff("Shop");
			  
		  }else if(cat1=="ASB"){
			  btn2.setTextOn("Noise");
			  btn2.setTextOff("Noise");
			  btn3.setTextOn("Street Drinking");
			  btn3.setTextOff("Street Drinking");
			  btn4.setTextOn("Other");
			  btn4.setTextOff("Other");
			  btn5.setTextOn("Drugs");
			  btn5.setTextOff("Drugs");

		  }else{
			  btn2.setTextOn("");
			  btn2.setTextOff("");
			  btn3.setTextOn("");
			  btn3.setTextOff("");
			  btn4.setTextOn("");
			  btn4.setTextOff("");
			  btn5.setTextOn("");
			  btn5.setTextOff("");

		  }
	}
	
	
	
	
	
	
	private void setListeners(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:
					Log.e("sociam","push the button");
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(pager.getCurrentItem()+1);
					break;
				case 99:
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
				case 999:
					addText();
					break;
			
			
				default:
					break;
				} 
			}
		});
	}
	
	private void setToggleListeners(final ToggleButton btn,final int num){
		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {
						if(isChecked){
							switch (num) {
							case 2:								
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								
								if(cat1=="Violent"){
									currentCrime.setCategory("Damage");
								}else if(cat1=="Theft"){
									currentCrime.setCategory("Robbery");
								}else if(cat1=="ASB"){
									currentCrime.setCategory("Noise");
								}
								break;
							case 3:
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								if(cat1=="Violent"){
									currentCrime.setCategory("Attack");
								}else if(cat1=="Theft"){
									currentCrime.setCategory("Bike");
								}else if(cat1=="ASB"){
									currentCrime.setCategory("StreetDrinking");
								}
								break;
							case 4:
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								if(cat1=="Violent"){
									currentCrime.setCategory("Rape");
								}else if(cat1=="Theft"){
									currentCrime.setCategory("Shop");
								}else if(cat1=="ASB"){
									currentCrime.setCategory("Drugs");
								}
								break;
							case 5:
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								addText();
								break;
							default:
								break;
							}
							
							
						}else if(!isChecked){
							
						}
						
					}
				});
	}
	

	
	private void addText(){		
		final EditText eText = new EditText(getActivity());
		
		if(currentCrime.getisCategoryText()){
			String str = currentCrime.getCategoryText();
			eText.setText(str, TextView.BufferType.EDITABLE);
		}
		
		new AlertDialog.Builder(getActivity())
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("Input Description")
		.setView(eText)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(eText.getText().toString().length()>0){
					currentCrime.setisCategoryText(true);
					currentCrime.setCategoryText(eText.getText().toString());
				}else{
					currentCrime.setisCategoryText(false);
					currentCrime.setCategoryText("");					
				}
				
				// out the input to toast at the moment
				Toast.makeText(getActivity(), eText.getText().toString()
						, Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				}
		}).show();
	}
	
}
