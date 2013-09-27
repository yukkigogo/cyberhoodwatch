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


public class ReportLocation extends Fragment {
	
	
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	
	
	String age_range=null;
	AlertDialog alertDialog;
	int selectedItem=0;
	ArrayAdapter<String> adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.report_people2, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_people2); 
		tx.setText("Location?");
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
					findViewById(R.id.layoutpeople2);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people2_midBtn);
	  btnS = (Button) v.findViewById(R.id.people2_goSummary);
	  btnD = (Button) v.findViewById(R.id.people2_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people2_RightBtmBtn);
	  btn3 = (ToggleButton) v.findViewById(R.id.people2_LeftBtmBtn);
	  btn4 = (ToggleButton) v.findViewById(R.id.people2_LeftTopBtn);
	  btn5 = (ToggleButton) v.findViewById(R.id.people2_RightTopBtn);
	
	
	  btn2.setTextOff("From Map");
	  btn2.setTextOn("From Map");
	  btn2.setText("From Map");
	  btn3.setTextOn("Input Address");
	  btn3.setTextOff("Input Address");
	  btn3.setText("Input Address");
	  btn4.setTextOn("UnKnown");
	  btn4.setTextOff("UnKnown");
	  btn4.setText("UnKnonw");
	  btn5.setTextOff("Here");
	  btn5.setTextOn("Here");
	  btn5.setText("Here");
	  
	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	}
	
	private void setLocation(){
		double lat = ((ReportActivity) getActivity()).getLat();
		double lng = ((ReportActivity) getActivity()).getLng();
		currentCrime.setLocationLatLon(true);
		currentCrime.setLat(lat);
		currentCrime.setLon(lng);
		Log.w("sociam","Lat and Lng : "+ lat + " " + lng);
		
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
					((ReportActivity) getActivity()).setAdapter();

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

						// 2:RB 3:LB 4:LT 5:RT
						if(isChecked){
							if( ((ReportActivity) getActivity()).getLoc() != num){	
								((ReportActivity) getActivity()).setLoc(num);
		
							switch (num) {
							case 2:	
								// show map
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								
								Toast.makeText(getActivity(), "Sorry - Haven't Implemented it yet!",
										Toast.LENGTH_LONG).show();
								
								break;
							case 3:
								
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								// add address
								
								addText();
								((ReportActivity) getActivity()).setAdapter();
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							case 4:
								// set up location null	
								currentCrime.setLocationLatLon(false);
								currentCrime.setisAddress(true);
								currentCrime.setAddress("Unknown");
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								((ReportActivity) getActivity()).setAdapter();

								pager.setCurrentItem(pager.getCurrentItem()+1);
								
								break;
							case 5:
								// set up the location from getActivity

								setLocation();
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								((ReportActivity) getActivity()).setAdapter();

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
		.setTitle("Input Address")
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
