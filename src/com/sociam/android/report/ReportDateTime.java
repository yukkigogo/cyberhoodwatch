package com.sociam.android.report;

import java.util.Calendar;

import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.report_people1, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_people1); 

		tx.setText("Date and Time?");
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
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}	
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people1_midBtn);
	  btnS = (Button) v.findViewById(R.id.people1_goSummary);
	  btnD = (Button) v.findViewById(R.id.people1_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people1_Right);
	  btn3 = (ToggleButton) v.findViewById(R.id.people1_Left);
	
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
					Log.e("sociam","push the button");
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
						
						if(isChecked){
							if( ((ReportActivity) getActivity()).getDnT() != num){	
								((ReportActivity) getActivity()).setDnT(num);
	
							switch (num) {
							case 2:	
								btn3.setChecked(false);
								
								Time now = new Time();
								now.setToNow();
								currentCrime.setDateText(now.format2445());								
								Log.e("sociam",now.format2445());
							
								pager.setCurrentItem(pager.getCurrentItem()+1);
								break;
							case 3:
								btn2.setChecked(false);		
								// start time and date picker 
								/*
								 * http://developer.android.com/guide/topics/ui/controls/pickers.html
								 */
								
								
								
								
								
								DatePickerFragment dp = new DatePickerFragment();
								dp.show(getActivity().getSupportFragmentManager(), "sociam");
								
								TimePickerFragment df = new TimePickerFragment();
								df.show(getActivity().getSupportFragmentManager(), "sociam");

								
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
	
	private static class TimePickerFragment extends DialogFragment
							implements TimePickerDialog.OnTimeSetListener {
		
      @Override
      public Dialog onCreateDialog(Bundle savedInstanceState) {
          final Calendar c = Calendar.getInstance();
          int hour = c.get(Calendar.HOUR_OF_DAY);
          int minute = c.get(Calendar.MINUTE);
    	  
    	return new TimePickerDialog(getActivity(), this, hour, minute, 
    			DateFormat.is24HourFormat(getActivity()));
      }
        @Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// save to the val
			Log.e("sociam", hourOfDay+" : "+ minute);
		}
	}

	private static class DatePickerFragment extends DialogFragment
    							implements DatePickerDialog.OnDateSetListener {
	  
		public Dialog onCreateDialog(Bundle savedInstanceState) {
	        final Calendar d = Calendar.getInstance();
	        int year = d.get(Calendar.YEAR);
	        int month = d.get(Calendar.MONTH);
	        int day = d.get(Calendar.DAY_OF_MONTH);

	        return new DatePickerDialog(getActivity(), this, year, month, day);
	    }
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Log.e("sociam", year + " "+monthOfYear +" "+ dayOfMonth);
			
		}
		
	}

	
}


