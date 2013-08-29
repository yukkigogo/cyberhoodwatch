package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.style.BulletSpan;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


public class ReportSuspectNum extends Fragment {
	
	
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	Persons suspects;
	String cat1;
	int selectedItem=0;
	AlertDialog alertDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.report_people2, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_people2); 
		tx.setText("How Many Suspect(s)");
		setBtns(view);
		return view;
	}

	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();
		suspects = currentCrime.getSuspects();

	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people2_midBtn);
	  btnS = (Button) v.findViewById(R.id.people2_goSummary);
	  btnD = (Button) v.findViewById(R.id.people2_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people2_RightBtmBtn);
	  btn3 = (ToggleButton) v.findViewById(R.id.people2_LeftBtmBtn);
	  btn4 = (ToggleButton) v.findViewById(R.id.people2_LeftTopBtn);
	  btn5 = (ToggleButton) v.findViewById(R.id.people2_RightTopBtn);
	
	  btn2.setTextOff("Two");
	  btn2.setTextOn("Two");
	  btn2.setText("Two");
	  btn3.setTextOn("Other");
	  btn3.setTextOff("Other");
	  btn3.setText("Other");
	  
	  btn4.setTextOn("Unknown");
	  btn4.setTextOff("Unknown");
	  btn4.setText("Unknown");
	  
	  btn5.setTextOff("Alone");
	  btn5.setTextOn("Alone");
	  btn5.setText("Alone");

	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
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
								suspects.setNum(2);
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								break;
							case 3:
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								// open dialog input the number
								spinnerOpen();
								
								break;
							
							case 4:
								suspects.setNum(99);
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								break;
							case 5:
								suspects.setNum(1);
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

					
					private void spinnerOpen() {
						ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
								getActivity(), android.R.layout.simple_list_item_single_choice);
						
						for(int i=1;i<=15;i++) adapter.add(i);
						
						Builder dialog = new AlertDialog.Builder(getActivity());
						
								dialog.setIcon(android.R.drawable.ic_dialog_info);
								dialog.setTitle("Please select from below");
								dialog.setSingleChoiceItems(adapter, selectedItem, onDialogClickListener);
						alertDialog = dialog.create();
						alertDialog.show();
					}
					
					
				});
	}
	

	private DialogInterface.OnClickListener onDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
       
            selectedItem = which;
            suspects.setNum(selectedItem);
            Log.w("sociam","number sus : "+ suspects.getNum());
            alertDialog.dismiss();
        }
    };

	
	
	
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
