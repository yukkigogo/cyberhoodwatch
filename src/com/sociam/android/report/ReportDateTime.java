package com.sociam.android.report;

import java.util.Calendar;

import com.sociam.android.R;
import com.sociam.android.model.Crime;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;



@SuppressLint("ValidFragment")
public class ReportDateTime extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3;
	Crime currentCrime;
	String cat1;
	boolean reVisit=false;
	int ihour,imin,iyear,imonth,iday;
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.report_datetime, container, false);
		setBtns(view);
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();

		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.layoutpeople1);		  
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}	
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

	}


	


	private void setBtns(View v) {
	
		 TextView tx = (TextView) v.findViewById(R.id.textview_people1); 
		 tx.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		 tx.setText("Date and Time?");
	
		  btn1 = (Button) v.findViewById(R.id.people1_midBtn);
		  btn1.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  btnS = (Button) v.findViewById(R.id.people1_goSummary);
		  btnS.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  btnD = (Button) v.findViewById(R.id.people1_description);
		  btnD.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  
		  btn2 = (ToggleButton) v.findViewById(R.id.people1_Right);
		  btn2.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  btn3 = (ToggleButton) v.findViewById(R.id.people1_Left);
		  btn3.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());	
		
		  btn2.setTextOff("Now");
		  btn2.setTextOn("Now");
		  btn2.setText("Now");
		  btn3.setTextOn("Other");
		  btn3.setTextOff("Other");
		  btn3.setText("Other");
		
		  setListeners(btn1, 0);
		  setListeners(btnS, 99);
		  setListeners(btnD, 999);
		  
		  setToggleListeners(btn2,2);
		  setToggleListeners(btn3,3);
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
						
						if(isChecked){
							if( ((ReportActivity) getActivity()).getDnT() != num){	
								((ReportActivity) getActivity()).setDnT(num);
	
							switch (num) {
							case 2:	
								btn3.setChecked(false);
								currentCrime.setIsNow(true);

								Time now = new Time();
								now.setToNow();
								currentCrime.setDate(now);
								//Log.e("sociam",now.format2445());
								
								((ReportActivity) getActivity()).setAdapter();
								pager.setCurrentItem(pager.getCurrentItem()+1);
								break;
							case 3:
								btn2.setChecked(false);		
								

									
								// start time and date picker 
								/*
								 * http://developer.android.com/guide/topics/ui/controls/pickers.html
								 */
								
								
						        final Calendar calendar = Calendar.getInstance();
						        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
						        final int minute = calendar.get(Calendar.MINUTE);
						        final int year = calendar.get(Calendar.YEAR);
						        final int monthOfYear = calendar.get(Calendar.MONTH);
						        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
								new DatePickerDialog(getActivity(), setDateListener() , year, monthOfYear, dayOfMonth).show(); 
								new TimePickerDialog(getActivity(), setTimeListener(), hour, minute, true).show();
								
								/*
								 * If I use DialogFragment, the exception with getCurrentItem which
								 *  relates to layout error happens. 
								 */
//								DatePickerFragment dp = new DatePickerFragment();
//								dp.show(getActivity().getSupportFragmentManager(), "sociam");
//								
//								TimePickerFragment df = new TimePickerFragment();
//								df.show(getActivity().getSupportFragmentManager(), "sociam");
								

								currentCrime.setIsNow(false);
								((ReportActivity) getActivity()).setAdapter();
							    pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							default:
								break;
							}
						}
							
						}else if(!isChecked){
							
							switch(num){
							case 2:
								// do nothing
								break;
							case 3:
								currentCrime.setIsNow(true);
								Time now = new Time();
								now.setToNow();
								currentCrime.setDate(now);
								
								btn3.setChecked(false);
								btn2.setChecked(false);

								break;
								
							}
						}
						
					}
				});
		
		
			
		
	
	}
	


	private OnDateSetListener setDateListener(){
		OnDateSetListener listner1 = new DatePickerDialog.OnDateSetListener() {			
			@Override
			public void onDateSet(DatePicker view, int year, 
					int monthOfYear, int dayOfMonth) {
				iyear=year;
				imonth=monthOfYear;
				iday=dayOfMonth;
				
				Time t = new Time();
				t.set(00,imin,ihour,iday,imonth,iyear);
				//Log.v("sociam", "time is "+t.format2445());
				currentCrime.setDate(t);	

				
			}
		};
		return listner1;
	}
	
	private OnTimeSetListener setTimeListener(){
		OnTimeSetListener tmd = new TimePickerDialog.OnTimeSetListener() {		
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					ihour = hourOfDay;
					imin = minute;
					
					
			}
		};
		
		return tmd;
	}

	
	
 
	
	private void addText(){		
		final EditText eText = new EditText(getActivity());
		
		if(currentCrime.getIsDateText()){
			String str = currentCrime.getDateText();
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
					currentCrime.setIsDateText(true);
					currentCrime.setDateText(eText.getText().toString());
				}else{
					currentCrime.setIsDateText(false);
					currentCrime.setDateText("");
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


