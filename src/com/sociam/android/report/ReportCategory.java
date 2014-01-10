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


public class ReportCategory extends Fragment {
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	boolean isChanged = false;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.report_category, container, false);
		
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		setBtns();
		initBtns();
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		//set up background
		if(currentCrime.getPicON()==1){
			  LinearLayout layout = (LinearLayout) 
					  getActivity().findViewById(R.id.layoutcategory);		  
			layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}
	}
	
	private void initBtns() {
		sbtn2 = false;
		sbtn3 = false;
		sbtn4 = false;
		sbtn5 = false;
		
	}

	private void setBtns() {
		TextView title = (TextView) view.findViewById(R.id.textView1);
		title.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn1 = (Button) getActivity().findViewById(R.id.frag2midBtn);
	  btn1.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnS = (Button) getActivity().findViewById(R.id.frag2goSummary);
	  btnS.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnD = (Button) getActivity().findViewById(R.id.frag2description);
	  btnD.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  
	  btn2 = (ToggleButton) getActivity().findViewById(R.id.frag2RightBtmBtn);
	  btn2.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn3 = (ToggleButton) getActivity().findViewById(R.id.frag2LeftBtmBtn);
	  btn3.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn4 = (ToggleButton) getActivity().findViewById(R.id.frag2LeftTopBtn);
	  btn4.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn5 = (ToggleButton) getActivity().findViewById(R.id.frag2RightTopBtn);
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
							
							
						}
					}).setNegativeButton("Cancel", 
							new DialogInterface.OnClickListener() {						
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
		pager =(ViewPager) getActivity().findViewById(R.id.pager);

		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {
						if(isChecked){
							switch (num) {
							case 2:
								//Log.e("sociam", "In 2 :"+Integer.toString(currentCrime.getCategoryCode()));
								if((currentCrime.getCategoryCode() != 99) 
								&& (currentCrime.getCategoryCode() !=1))
									currentCrime.setCat2Def(true);
								
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								currentCrime.setCategory("Violent");
								currentCrime.setCategoryCode(1);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							case 3:
								//Log.e("sociam", "in 3 "+Integer.toString(currentCrime.getCategoryCode()));
								if((currentCrime.getCategoryCode() != 99)
								&& (currentCrime.getCategoryCode() !=0))
									currentCrime.setCat2Def(true);
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								currentCrime.setCategory("Other");
								currentCrime.setCategoryCode(0);
								pager.setCurrentItem(pager.getCurrentItem()+2);

								break;
							case 4:
								//Log.w("sociam", "in 4 "+Integer.toString(currentCrime.getCategoryCode()));

								if((currentCrime.getCategoryCode() != 99)
										&& (currentCrime.getCategoryCode() !=3))
									currentCrime.setCat2Def(true);
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								currentCrime.setCategory("ASB");
								currentCrime.setCategoryCode(3);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;

							case 5:

								if((currentCrime.getCategoryCode() != 99)
										&& (currentCrime.getCategoryCode() !=2))
									currentCrime.setCat2Def(true);
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								currentCrime.setCategory("Theft");
								currentCrime.setCategoryCode(2);
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							default:
								break;
							}
							
							
						}else if(!isChecked){
							currentCrime.setCategory("");
							currentCrime.setCategoryCode(99);
							// all the 2 become off
						}
						
					}
				});
	}
	
}
