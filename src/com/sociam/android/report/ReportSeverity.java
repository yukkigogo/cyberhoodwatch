package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.R;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


public class ReportSeverity extends Fragment {
	
	
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	
	
	String age_range=null;
	AlertDialog alertDialog;
	ArrayAdapter<String> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.report_severity, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_severity); 
		tx.setText("Serious Incident?");
		setBtns(view);
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();


		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) getActivity().
					findViewById(R.id.layoutseverity);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.severity_midBtn);
	  btnS = (Button) v.findViewById(R.id.severity_goSummary);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.severity_RightBtmBtn);
	  btn3 = (ToggleButton) v.findViewById(R.id.severity_LeftBtmBtn);
	  btn4 = (ToggleButton) v.findViewById(R.id.severity_LeftTopBtn);
	  btn5 = (ToggleButton) v.findViewById(R.id.severity_RightTopBtn);
	
	
	  btn2.setTextOff("Very");
	  btn2.setTextOn("Very");
	  btn2.setText("Very");
	  btn3.setTextOn("Extremely");
	  btn3.setTextOff("Extremely");
	  btn3.setText("Extremely");
	  btn4.setTextOn("Not");
	  btn4.setTextOff("Not");
	  btn4.setText("Not");
	  btn5.setTextOff("Serious");
	  btn5.setTextOn("Serious");
	  btn5.setText("Serious");
	  
	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);

	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	}
	
	
	private void setListeners(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:
					pager.setCurrentItem(pager.getCurrentItem()-1);
					break;
				case 99:
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
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {

						// 2:3 3:4 4:1 5:2
						if(isChecked){
//							Log.e("sociam", "show the number 1 : "+ ((ReportActivity) getActivity()).getEvi());
	  
						if( ((ReportActivity) getActivity()).getSev() != num){	
							((ReportActivity) getActivity()).setSev(num);
//							Log.e("sociam", "show the number 2 : "+ ((ReportActivity) getActivity()).getEvi());
							switch (num) {
							case 2:	
								// show map
								
								currentCrime.setSeverity(3);
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							case 3:
								
								currentCrime.setSeverity(4);
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							case 4:
								// set up location null	
								currentCrime.setSeverity(1);
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							case 5:
								// set up the location from getActivity

								currentCrime.setSeverity(2);
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;

							default:
								break;
							}
						}	
							
						}else if(!isChecked){
							
						}
						
					}

			});
	}
	


	
	
	
	
	
	private void addText(){		
		final EditText eText = new EditText(getActivity());
		
		if(currentCrime.getIsAddress()){
			String str = currentCrime.getAddress();
			eText.setText(str, TextView.BufferType.EDITABLE);
		}
		
		new AlertDialog.Builder(getActivity())
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("Input Detail")
		.setView(eText)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(eText.getText().toString().length()>0){
					currentCrime.setisAddress(true);
					currentCrime.setAddress(eText.getText().toString());
				}else{
					currentCrime.setisAddress(false);
					currentCrime.setAddress("");
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
