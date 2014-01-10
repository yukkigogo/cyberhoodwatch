package com.sociam.android.message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;
import com.sociam.android.model.SendMessage;
import com.sociam.android.user.TagRegisterFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

	SharedPreferences sp; 
	View view;
	Typeface robothin ;
	TextView username;
	TextView location;
	
	SendMessage um;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view =inflater.inflate(R.layout.message_screen, container, false);
		
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		um = ((MessageActivity) getActivity()).getUM();
		
		toggleSetup(view);
		
		robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		
		// location		
		
		// username
		username = (TextView) view.findViewById(R.id.msg_username);
		username.setTypeface(robothin);

		location = (TextView) view.findViewById(R.id.msg_location);
		location.setTypeface(robothin);
		
		double lat = ((MessageActivity) getActivity()).getLat(); 
		double lon = ((MessageActivity) getActivity()).getLon();
		
		String loc = getAddress(lat, lon);
		location.setText("@"+ loc);
		
		
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
				um.setMsg(s.toString());
				
			}
		});
		
		message.requestFocus();	
		
		InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(message, InputMethodManager.SHOW_IMPLICIT);
		
		return view;
	}
	
	
	
	private void toggleSetup(View view){
		
		idcard = (ToggleButton) view.findViewById(R.id.user_card);
		
		idcard.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						
						 if(isChecked){
							String str = sp.getString("username",null);
							
							if(str!=null){
								username.setText(str+" says...");
								um.setAnonymous(false);
								um.setUserName(str);
								
							}else{
								AlertDialog.Builder alb = new AlertDialog.Builder(getActivity());
								alb.setTitle("You Don't Have Username Yet");
								alb.setMessage("Do you want to sing up?");
								alb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										//open another activity to register users	
										Intent intent = new Intent();
										intent.setClassName("com.sociam.android", 
												"com.sociam.android.user.UserRegisterActivity");
										startActivity(intent);
										
										
									}
								});
								alb.setNegativeButton("No", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {

										
									}
								});
								
								AlertDialog alertDialog = alb.create();
								alertDialog.show();
									
								idcard.setChecked(false);
							}
								
							

						 }else{
								username.setText("Anonymous says...");
								um.setAnonymous(true);
								um.setUserName("");

							 
						 }
					 }
				});
		
		
	}

	
	public String getAddress(double lat, double lon){
		String address=null;
		
		Geocoder geocorder;
		List<Address> addresses = null;
		geocorder = new Geocoder(getActivity(), Locale.getDefault());
		try {
			addresses = geocorder.getFromLocation(lat, lon, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(addresses.get(0).getSubLocality()!=null)
			address = addresses.get(0).getSubLocality() + addresses.get(0).getLocality();
		else
			address = addresses.get(0).getLocality();
				
		
		return address;
	}
	
	
}
