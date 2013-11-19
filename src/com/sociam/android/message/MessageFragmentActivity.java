package com.sociam.android.message;

import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;
import com.sociam.android.R.menu;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


// main activity for message
public class MessageFragmentActivity extends FragmentActivity{
	
	private int count=0; 
	
	ToggleButton idcard;
	ToggleButton tags; 
	ToggleButton msgmap; 
	
	SharedPreferences sp; 

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.message_main);
				
		sp = PreferenceManager.getDefaultSharedPreferences(this);		 

		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Typeface robotocoud = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
		final TextView tv = (TextView) findViewById(R.id.msg_count);
		tv.setTypeface(robotocoud);
		
		
		toggleSetup();
		
		
		EditText message = (EditText) findViewById(R.id.message_text);
		Typeface robothin = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
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
					count = 140-s.length();
					if(count<0) tv.setTextColor(Color.RED);
					else tv.setTextColor(Color.BLACK);
					tv.setText(Integer.toString(count));
				}
			}
		});
		
		message.requestFocus();	
		InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(message, InputMethodManager.SHOW_IMPLICIT);
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()){
		case android.R.id.home:
			finish();			
			break;
			
		case R.id.msg_send:
		
			break;
		
		}	
		
		return super.onOptionsItemSelected(item);
	}
	
	
	private void toggleSetup(){
		
		idcard = (ToggleButton) findViewById(R.id.user_card);
		tags = (ToggleButton) findViewById(R.id.tags);
		msgmap = (ToggleButton) findViewById(R.id.msgmap);
		
	
		
		idcard.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						String str = sp.getString("username", null);
						if(str!=null){
							
							
							
						}else{
							//open another activity to register users	
								
						}
							
					
					}
				});
		
		
	}
	
	
	
	
}
