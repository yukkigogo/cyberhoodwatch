package com.sociam.android.user;


import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class UserSetupFragment extends Fragment{
	
	SharedPreferences sp;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		View view = getActivity().getLayoutInflater().inflate(R.layout.register_user, null);
		
		
		Typeface robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		
		TextView tx1 = (TextView) view.findViewById(R.id.reg_text1);
		tx1.setTypeface(robothin);
		TextView tx2 = (TextView) view.findViewById(R.id.reg_text2);
		tx2.setTypeface(robothin);
		TextView tx3 = (TextView) view.findViewById(R.id.reg_text3);
		tx3.setTypeface(robothin);
		TextView tx4 = (TextView) view.findViewById(R.id.reg_text4);
		tx4.setTypeface(robothin);
		
		final TextView tx_reg_ave = (TextView) view.findViewById(R.id.reg_avaiablity);
		tx_reg_ave.setTypeface(robothin);
		
		final EditText username = (EditText) view.findViewById(R.id.reg_username);
		username.setTypeface(robothin);
		username.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				if(s.length()>0){
					UserAvaiableAsyncTask asyncTask = new UserAvaiableAsyncTask(new UserSetupFragmentCallBack() {
						
						@Override
						public void onTaskDone(int avaiable) {

							if(avaiable==1){
								// okay
								tx_reg_ave.setTextColor(Color.GREEN);
								tx_reg_ave.setText("Avaiable Username");
								
							}else if(avaiable==0){
								tx_reg_ave.setTextColor(Color.RED);
								tx_reg_ave.setText("This username is already taken");
							}else{
								tx_reg_ave.setText("");
							}

							
						}
					});
					
					asyncTask.execute(username.getText().toString());
				
				}
		

	
			}
			
		});
				
		
		return view;
	}
		
	
	
	

	public interface UserSetupFragmentCallBack{
		public void onTaskDone(int avaiable);
	}

	
}
