package com.sociam.android.message;

import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MessageFragment extends Fragment{
	
	private int count=0; 
	
	ToggleButton idcard;
	ToggleButton tags; 
	ToggleButton msgmap; 

	SharedPreferences sp; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =inflater.inflate(R.layout.message_screen, container, false);
		
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

		toggleSetup(view);
		
		Typeface robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		
		final TextView tv = (TextView) view.findViewById(R.id.msg_count);
		tv.setTypeface(robothin);
		
		EditText message = (EditText) view.findViewById(R.id.message_text);		
		message.setTypeface(robothin);
		
		message.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0){
					tv.setText("140");
				}else if(s.length()>0){
					count = 140-s.length();
					if(count<0) tv.setTextColor(Color.RED);
					else tv.setTextColor(Color.BLACK);
					tv.setText(Integer.toString(count));
				}
			}
		});
		
		message.requestFocus();	
		
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(message, InputMethodManager.SHOW_IMPLICIT);
		
		return view;
	}
	
	private void toggleSetup(View view){
		
		idcard = (ToggleButton) view.findViewById(R.id.user_card);
		tags = (ToggleButton) view.findViewById(R.id.tags);
		msgmap = (ToggleButton) view.findViewById(R.id.msgmap);
		
	
		
		idcard.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						String str = sp.getString("username", null);
						if(str!=null){
							
							
							
						}else{
							//open another activity to register users	
								Log.v("sociam", "OPEN NEW REGISTER PAGE");
						}
							
					
					}
				});
		
		
	}

	
}
