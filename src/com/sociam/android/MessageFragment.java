package com.sociam.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MessageFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =inflater.inflate(R.layout.message_main, container, false);
		
		
		Typeface robotocoud = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
		final TextView tv = (TextView) view.findViewById(R.id.msg_count);
		tv.setTypeface(robotocoud);
		
		
		
		EditText message = (EditText) view.findViewById(R.id.message_text);
		Typeface robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		message.setTypeface(robothin);
		
		message.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					tv.setText("140");
				}else if(s.length()>0){
					int count = 140-s.length();
					if(count<0) tv.setTextColor(Color.RED);
					tv.setText(Integer.toString(count));
				}
			}
		});
		
		
		
		message.requestFocus();	
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);

		
		return view;
	}
	
	
	
}
