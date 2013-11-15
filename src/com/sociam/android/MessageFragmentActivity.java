package com.sociam.android;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MessageFragmentActivity extends FragmentActivity{
	
	private int count=0; 
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.message_main);
		
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Typeface robotocoud = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
		final TextView tv = (TextView) findViewById(R.id.msg_count);
		tv.setTypeface(robotocoud);
		
		
		
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
	
		
		return super.onOptionsItemSelected(item);
	}
	
}
