package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;

import android.support.v4.app.Fragment;
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


public class ReportSuspect extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Persons suspects;
	String cat1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		Log.e("sociam","cat2");
		View view = inflater.inflate(R.layout.report_people1, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_people1); 
		tx.setText("Do You Know Suspect(s)?");
		setBtns(view);
		return view;
	}

	public void onStart() {
		super.onStart();
		suspects =  ((ReportActivity) getActivity()).getCrime().getSuspects();
		

	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people_midBtn);
	  btnS = (Button) v.findViewById(R.id.people_goSummary);
	  btnD = (Button) v.findViewById(R.id.people_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people_TopBtn);
	  btn3 = (ToggleButton) v.findViewById(R.id.people_RightBtn);
	  btn4 = (ToggleButton) v.findViewById(R.id.people_bottomBtn);
	  btn5 = (ToggleButton) v.findViewById(R.id.people_LeftBtn);
	
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	  btn3.setText("Yes");
	  btn5.setText("No");
	  
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
						// 2:Top 3:Right 4:Bottom 5:Left
						if(isChecked){
							switch (num) {
							case 2:								
								break;
							case 3:
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								
								break;
							case 4:
								
								
								break;
							case 5:
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								
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
		
		if(suspects.getisText()){
			String str = suspects.getText();
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
					suspects.setisText(true);
					suspects.setText(eText.getText().toString());
				}else{
					suspects.setisText(false);
					suspects.setText("");
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
