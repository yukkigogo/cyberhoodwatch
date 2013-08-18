package com.sociam.android.report;

import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReportFragment3 extends Fragment {
	
	// location can be implemented by google map.
	
	ViewPager pager;
	
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		return inflater.inflate(
			R.layout.report_fragment3, container, false);
	}
	
	public void onStart() {
		super.onStart();
		setBtns();
	}
	
	private void setBtns() {
		  Button btn1 = (Button) getActivity().findViewById(R.id.frag3midBtn);
		  Button btnS = (Button) getActivity().findViewById(R.id.frag3goSummary);
		  Button btnD = (Button) getActivity().findViewById(R.id.frag3description);
		  
		  Button btn2 = (Button) getActivity().findViewById(R.id.frag3TopBtn);
		  Button btn3 = (Button) getActivity().findViewById(R.id.frag3bottomBtn);
		  //Button btn4 = (Button) getActivity().findViewById(R.id.frag3LeftBtn);
		  //Button btn5 = (Button) getActivity().findViewById(R.id.frag3RightBtn);
		  
		  
		  btn1.setText("Skip");
		  btn2.setText("Here");
		  btn3.setText("Put Adress");
		 
		  
		  setListeners(btn1, 0);
		  setListeners(btnS, 99);
		  setListeners(btnD, 999);
		  
		  setListeners(btn2, 1);
		  setListeners(btn3, 2);
		}
		
		private void setListeners(Button btn, final int type){
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 0 = mid, 99= summary, 999 = description
					switch (type) {
					case 0:
						Log.e("sociam","push the button");
						pager =(ViewPager) getActivity().findViewById(R.id.pager);
						//set next fregment - same number of fragment
						pager.setCurrentItem(3);
						break;
					
					case 1: // here
						
						break;
					case 2: //input address 
						
						break;						
					case 99:
						pager =(ViewPager) getActivity().findViewById(R.id.pager);
						pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
						break;
					
					case 999:
						break;
						
					default:
						break;
					} 
				}
			});
		}
	
	
}
