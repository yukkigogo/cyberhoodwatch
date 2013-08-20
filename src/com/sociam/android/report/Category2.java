package com.sociam.android.report;

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
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


public class Category2 extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	ReportActivity currentCrime;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		return inflater.inflate(R.layout.report_category2, container, false);
	}

	public void onStart() {
		super.onStart();
		setBtns();
		initBtns();
		currentCrime = ((ReportActivity) getActivity());
	}
	
	private void initBtns() {
		sbtn2 = false;
		sbtn3 = false;
		sbtn4 = false;
		sbtn5 = false;
		
	}

	private void setBtns() {
	  btn1 = (Button) getActivity().findViewById(R.id.frag2midBtn);
	  btnS = (Button) getActivity().findViewById(R.id.frag2goSummary);
	  btnD = (Button) getActivity().findViewById(R.id.frag2description);
	  
	  btn2 = (ToggleButton) getActivity().findViewById(R.id.frag2TopBtn);
	  btn3 = (ToggleButton) getActivity().findViewById(R.id.frag2bottomBtn);
	  btn4 = (ToggleButton) getActivity().findViewById(R.id.frag2LeftBtn);
	  btn5 = (ToggleButton) getActivity().findViewById(R.id.frag2RightBtn);
	  
	  
	  
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
					pager.setCurrentItem(2);
					break;
				case 99:
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
				case 999:
					final EditText eText = new EditText(getActivity());
					new AlertDialog.Builder(getActivity())
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("Input Description")
					.setView(eText)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
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
								currentCrime.setCat1(1);
								break;
							case 3:
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								currentCrime.setCat1(0);
								break;
							case 4:
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								currentCrime.setCat1(3);
								break;
							case 5:
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								currentCrime.setCat1(2);
								break;
							default:
								break;
							}
							
							
						}else if(!isChecked){
							currentCrime.setCat1(0);
						}
						
					}
				});
	}
	
}
