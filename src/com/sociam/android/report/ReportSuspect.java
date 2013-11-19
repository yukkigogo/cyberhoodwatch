package com.sociam.android.report;

import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


public class ReportSuspect extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
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

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();
		suspects = currentCrime.getSuspects();
		pager =(ViewPager) getActivity().findViewById(R.id.pager);


		//set up background
		if(currentCrime.getPicON()==1){
			  LinearLayout layout = (LinearLayout) 
					  getActivity().findViewById(R.id.layoutpeople1);		  
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}
		
	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people1_midBtn);
	  btnS = (Button) v.findViewById(R.id.people1_goSummary);
	  btnD = (Button) v.findViewById(R.id.people1_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people1_Right);
	  btn3 = (ToggleButton) v.findViewById(R.id.people1_Left);
	
	
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
					pager.setCurrentItem(pager.getCurrentItem()+1);
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
		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {

						
						if(isChecked){
							switch (num) {
							case 2:	
								currentCrime.setUseeSuspects(true);
								btn3.setChecked(false);
								// alert dialog wanna add detail or not?
								//DetailDialogFragment ddf = new DetailDialogFragment();
								//ddf.show(getActivity().getSupportFragmentManager(), "sociam");
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							case 3:
								currentCrime.setUseeSuspects(false);
								btn2.setChecked(false);		
								// jump to the victim page
								pager.setCurrentItem(pager.getCurrentItem()+1);
								
								break;
							
							default:
								break;
							}
							
							
						}else if(!isChecked){
							
						}
						
					}
				});
	}
	

	@SuppressLint("ValidFragment")
	private class DetailDialogFragment extends DialogFragment{
		//ViewPager pager =(ViewPager) getActivity().findViewById(R.id.pager);
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Add Detail?")
			.setMessage("Do you want to add detail of suspect(s)?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// jump to next page
					//pager.setCurrentItem(pager.getCurrentItem()+1);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// jump to victim
					//pager.setCurrentItem(pager.getCurrentItem()+4);
				}
			});
			
			
			return builder.create();
		}
		
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
