package com.sociam.android.message;

import com.sociam.android.R;
import com.sociam.android.R.id;
import com.sociam.android.R.layout;
import com.sociam.android.R.menu;
import com.sociam.android.user.UserMessage;

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
	
	
	SharedPreferences sp; 
	
	double lat,lon;
	UserMessage um;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		um = new UserMessage();
		lat = getIntent().getExtras().getDouble("lat");
		lon = getIntent().getExtras().getDouble("lon");
		um.setLatLon(lat, lon);
		
		setContentView(R.layout.message_main);
				
		sp = PreferenceManager.getDefaultSharedPreferences(this);		 
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
	
	}
	
	public double getLat(){
		return this.lat;
	}
	public double getLon(){
		return this.lon;
	}
	
	public UserMessage getUM(){
		return this.um;
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
	
	
	
	
	
	
}
