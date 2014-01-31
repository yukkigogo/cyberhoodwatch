package com.sociam.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * This class set up map view in main screen. 
 * 
 * @author yukki
 *
 */
public class MapMainFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.main_map, container);
				
		
		return view;
	}
	
}
