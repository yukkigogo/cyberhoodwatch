package com.sociam.android.user;


import java.util.regex.Matcher;
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
			public void afterTextChanged(final Editable s) {
				
				if(s.length()>0 && s.length()<16){
					if((s.toString().indexOf(" ")>=0)){
						tx_reg_ave.setTextColor(Color.RED);
						tx_reg_ave.setText("You cannot use space!");
					}else{
					
					UserAvaiableAsyncTask asyncTask = new UserAvaiableAsyncTask(new UserSetupFragmentCallBack() {
						
						@Override
						public void onTaskDone(int avaiable) {

							if(avaiable==1){
								// okay
								tx_reg_ave.setTextColor(Color.GREEN);
								tx_reg_ave.setText("Avaiable Username");
								((UserRegisterActivity) getActivity()).username = s.toString();
								
							}else if(avaiable==0){
								tx_reg_ave.setTextColor(Color.RED);
								tx_reg_ave.setText("This username is already taken");
								((UserRegisterActivity) getActivity()).username = null;
							}else{
								tx_reg_ave.setText("");
								((UserRegisterActivity) getActivity()).username = null;

							}

							
						}
					});
					
					asyncTask.execute(username.getText().toString());
				
				}
				}else if(s.length()==0){
					tx_reg_ave.setText("");
					((UserRegisterActivity) getActivity()).username = null;

				}else{
					((UserRegisterActivity) getActivity()).username = null;
					tx_reg_ave.setTextColor(Color.RED);
					tx_reg_ave.setText("This username is too long!");
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
					if(s.length()>5){
							pass_avaiable.setTextColor(Color.GREEN);
							pass_avaiable.setText("password is OK!");							
					}else{
						pass_avaiable.setTextColor(Color.RED);
						pass_avaiable.setText("password must be at least 6 characters");
					}
			}
		});
		final TextView pass_same = (TextView) view.findViewById(R.id.password_same);
		pass_same.setTypeface(robothin);
		final EditText password2 = (EditText) view.findViewById(R.id.password2);
		password2.addTextChangedListener(new TextWatcher() {
			
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
					if(s.toString().equals(password1.getText().toString())){
						pass_same.setTextColor(Color.GREEN);
						pass_same.setText("OK!");
						((UserRegisterActivity) getActivity()).password = s.toString();

					}else{
						pass_same.setTextColor(Color.RED);
						pass_same.setText("Enter the passwords do not match!");
						((UserRegisterActivity) getActivity()).password = null;

					}
						
				}
			}
		});
		
		final TextView emailike = (TextView) view.findViewById(R.id.emailike);
		emailike.setTypeface(robothin);
		final EditText emailaddress = (EditText) view.findViewById(R.id.emailaddress);
		emailaddress.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(final Editable s) {
				if(isValidEmail(s.toString())){
					
					EmailAvaiableAsyncTask emailAsync = new EmailAvaiableAsyncTask(new UserSetupFragmentCallBackEmail() {
						
						@Override
						public void onTaskDone(int avaiable) {
						 if(avaiable==1){
                                 // okay
                                 emailike.setTextColor(Color.GREEN);
                                 emailike.setText("Avaiable Email address");
         						((UserRegisterActivity) getActivity()).email = s.toString();

                                 
                         }else if(avaiable==0){
                                 emailike.setTextColor(Color.RED);
                                 emailike.setText("This email is already used");
          						((UserRegisterActivity) getActivity()).email = "";

                         }else{
                                emailike.setText("");
          						((UserRegisterActivity) getActivity()).email = "";
                         }							
						}
					});
					emailAsync.execute(s.toString());
					
				}else{
					emailike.setTextColor(Color.RED);
					emailike.setText("Invalid Email");
					((UserRegisterActivity) getActivity()).email = "";

				}
			}
		});
		
		
		
		
		ImageView reg_user_next = (ImageView) view.findViewById(R.id.reg_user_next);
		TextView reg_user_next_tex = (TextView) view.findViewById(R.id.reg_user_next_tex);
		setGoNext(reg_user_next);
		setGoNext(reg_user_next_tex);
		
		ImageView reg_user_cancel = (ImageView) view.findViewById(R.id.reg_user_cancel);
		TextView reg_user_cancel_text = (TextView) view.findViewById(R.id.reg_user_cancel_tex);
		setCancel(reg_user_cancel_text);
		setCancel(reg_user_cancel);
		
		
		return view;
	}
	
	
	private boolean isValidEmail(String email){
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}

	

	private void setGoNext(Object obj){
	
		((View) obj).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem()+1);
			}
		});
	}
	
	private void setCancel(Object obj){
		((View) obj).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((UserRegisterActivity) getActivity()).finish();
			}
		});
	}
	


	
	

	public interface UserSetupFragmentCallBack{
		public void onTaskDone(int avaiable);
	}

	public interface UserSetupFragmentCallBackEmail{
		public void onTaskDone(int avaiable);
	}

	
	
}
