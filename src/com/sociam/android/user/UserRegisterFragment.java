package com.sociam.android.user;


import java.util.regex.Pattern;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;
import com.sociam.android.message.MessageActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserRegisterFragment extends Fragment{
	
	SharedPreferences sp;
	private DataApplication dapp;
	private ViewPager pager;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		dapp = (DataApplication) ((UserRegisterActivity) getActivity()).getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		View view = getActivity().getLayoutInflater().inflate(R.layout.register_user, null);
		pager = ((UserRegisterActivity) getActivity()).getPager();
		
		Typeface robothin = dapp.getTypefaceRobothin();
		
//		TextView tx5 = (TextView) view.findViewById(R.id.reg_new_user_title);
//		tx5.setTypeface(dapp.getTypefaceRoboRegItalic());
				
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
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
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
			
		final TextView pass_avaiable = (TextView) view.findViewById(R.id.pass_avaiable);
		pass_avaiable.setTypeface(robothin);
		final EditText password1 = (EditText) view.findViewById(R.id.password1);
		password1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void afterTextChanged(Editable s) {
					if(s.length()>8){
						if(checkPasswordOK(s.toString())){
							pass_avaiable.setTextColor(Color.GREEN);
							pass_avaiable.setText("password is OK!");							
							
						}else{
							pass_avaiable.setTextColor(Color.RED);
							pass_avaiable.setText("password must contain numerals and alphabets");							
						}
						
					}else{
						pass_avaiable.setTextColor(Color.RED);
						pass_avaiable.setText("password must be at least 8 characters and contain numerals and alphabets");
					}
			}
		});
		final TextView pass_same = (TextView) view.findViewById(R.id.password_same);
		final EditText password2 = (EditText) view.findViewById(R.id.password2);
		
		
		final EditText emailaddress = (EditText) view.findViewById(R.id.emailaddress);
		final TextView emailike = (TextView) view.findViewById(R.id.emailike);
		
		final TextView reg_user_next_tex = (TextView) view.findViewById(R.id.reg_user_next_tex); 
		setClickNGoNext(reg_user_next_tex);
		ImageView reg_user_next = (ImageView) view.findViewById(R.id.reg_user_next);
		setClickNGoNext(reg_user_next);
		
		
		return view;
	}
		
	
	private void setClickNGoNext(Object obj) {
		((View) obj).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem()+1);
			}
		});
		
	}


	private boolean checkPasswordOK(String str){
		
		if(str.length()>7 && 
				Pattern.compile("[0-9]").matcher(str).find() &&
				Pattern.compile("[a-z,A-Z]").matcher(str).find())
		return true;
		
		else return false;
	}
	
	

	public interface UserSetupFragmentCallBack{
		public void onTaskDone(int avaiable);
	}

	
}
