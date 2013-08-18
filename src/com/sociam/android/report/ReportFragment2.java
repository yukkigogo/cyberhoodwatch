package com.sociam.android.report;

import com.sociam.android.R;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;


public class ReportFragment2 extends Fragment {
	
	ViewPager pager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {
		return inflater.inflate(R.layout.report_fragment2, container, false);
	}

	public void onStart() {
		super.onStart();
		setBtns();
	}
	
	private void setBtns() {
	  Button btn1 = (Button) getActivity().findViewById(R.id.frag2midBtn);
	  Button btnS = (Button) getActivity().findViewById(R.id.frag2goSummary);
	  Button btnD = (Button) getActivity().findViewById(R.id.frag2description);
	  
	  Button btn2 = (Button) getActivity().findViewById(R.id.frag2TopBtn);
	  Button btn3 = (Button) getActivity().findViewById(R.id.frag2bottomBtn);
	  Button btn4 = (Button) getActivity().findViewById(R.id.frag2LeftBtn);
	  Button btn5 = (Button) getActivity().findViewById(R.id.frag2RightBtn);
	  
	  
	  btn1.setText("Skip");
	  btn2.setText("Violent");
	  btn3.setText("Other");
	  btn4.setText("ASB");
	  btn5.setText("Theft");
	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	}
	
	private void setListeners(Button btn, final int type){
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0 = mid, 1= summary, 2 = description
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

				default:
					break;
				} 
			}
		});
	}
	
}
