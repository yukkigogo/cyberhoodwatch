package com.sociam.android;

import android.os.Bundle;
import android.preference.PreferenceFragment;

// a class deals with personal 
public class SettingsFragment extends PreferenceFragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
