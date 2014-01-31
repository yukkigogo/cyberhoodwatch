package com.sociam.android.report;

import com.sociam.android.R;
import com.sociam.android.model.Crime;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;

/**
 * This is second category page. The page buttons are changed depend on the first category page.
 * 
 * @author yukki
 *@version 1
 */
@SuppressLint("NewApi")
public class ReportCategory2 extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	String cat1;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.report_category2, container, false);
		return view;
	}

	public void onStart() {
		super.onStart();
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		cat1=currentCrime.getCategory();
		Log.e("sociam","cat1");		  
	
		//set up background
		if(currentCrime.getPicON()==1){
			  LinearLayout layout = (LinearLayout) 
					  getActivity().findViewById(R.id.layoutcategory2);		  
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		 }

		setBtns();
	}
	
	/**
	 * Set to default 
	 */
	public void setall(){
		setBtns();
	}
	


	private void setBtns() {
		TextView title = (TextView) view.findViewById(R.id.textView1);
		title.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn1 = (Button) getActivity().findViewById(R.id.frag3midBtn);
	  btn1.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnS = (Button) getActivity().findViewById(R.id.frag3goSummary);
	  btnS.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnD = (Button) getActivity().findViewById(R.id.frag3description);
	  btnD.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  
	  btn2 = (ToggleButton) getActivity().findViewById(R.id.frag3RightTopBtn);
	  btn2.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn3 = (ToggleButton) getActivity().findViewById(R.id.frag3RightBtmBtn);
	  btn3.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn4 = (ToggleButton) getActivity().findViewById(R.id.frag3LeftBtmBtn);
	  btn4.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn5 = (ToggleButton) getActivity().findViewById(R.id.frag3LeftTopBtn);
	  btn5.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  
	
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
					pager.setCurrentItem(pager.getCurrentItem()-1);
					break;
				case 99:
					((ReportActivity) getActivity()).setAdapter();

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
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

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
								
								if(currentCrime.getCategoryCode()==1){
									currentCrime.setCategory("Violent-Damage");
								}else if(currentCrime.getCategoryCode()==2){
									currentCrime.setCategory("Theft-Robbery");
								}else if(currentCrime.getCategoryCode()==3){
									currentCrime.setCategory("ASB-Noise");
								}

								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							case 3:
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								
								if(currentCrime.getCategoryCode()==1){
									currentCrime.setCategory("Violent-Attack");
								}else if(currentCrime.getCategoryCode()==2){
									currentCrime.setCategory("Theft-Bike");
								}else if(currentCrime.getCategoryCode()==3){
									currentCrime.setCategory("ASB-StreetDrinking");
								}
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							
							case 4:
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								
								String catcode = null;
								if(currentCrime.getCategoryCode()==1) catcode="Violent";
								else if(currentCrime.getCategoryCode()==2) catcode = "Theft";
								else if(currentCrime.getCategoryCode()==3) catcode = "ASB";
								else catcode="Other";
								
								currentCrime.setCategory(catcode + "-other");
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							
							case 5:
								Log.w("sociam", "in 4 "+Integer.toString(currentCrime.getCategoryCode()));
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								if(currentCrime.getCategoryCode()==1){
									currentCrime.setCategory("Violent-Rape");
								}else if(currentCrime.getCategoryCode()==2){
									currentCrime.setCategory("Theft-Shop");
								}else if(currentCrime.getCategoryCode()==3){
									currentCrime.setCategory("ASB-Drugs");
								}
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
