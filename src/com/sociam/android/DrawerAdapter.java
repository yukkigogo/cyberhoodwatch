package com.sociam.android;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.sociam.android.model.Crime;

import android.content.Context;
import android.drm.DrmStore.RightsStatus;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * This is custom ArrayAdapter for Navigation Drawer.
 * 
 * @author yukki
 *@version 1
 */
public class DrawerAdapter extends ArrayAdapter<Crime>{

	private LayoutInflater mLayoutInflater;
	private Location userposition;
	private Context mContext;
	private DataApplication dapp;
	
	public DrawerAdapter(Context context, int textViewResourceId, List<Crime> crime, DataApplication dap) {
		super(context, textViewResourceId,crime);
		dapp = dap;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Crime crime = getItem(position);
		userposition = ((MainActivity) mContext).getLocation();
		
		if(null==convertView) 
			convertView = mLayoutInflater.inflate(R.layout.row_dawer_list, null);
		
		//image add later
		
		
		
		
		TextView category = (TextView) convertView.findViewById(R.id.drawer_category);
		category.setText(crime.getCategory());
		
		
		Calendar cal = crime.getCal();
		
		String hourmin=null;
		SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
		hourmin= time_format.format(cal.getTime());
		
		TextView hourminV = (TextView) convertView.findViewById(R.id.drawer_time);
		hourminV.setTypeface(dapp.getTypefaceRobothin());
		hourminV.setText(hourmin);
		
		
		String date=null;
		if(cal.get(Calendar.YEAR)==MainActivity.rightNow.get(Calendar.YEAR) && 
				cal.get(Calendar.MONTH)==MainActivity.rightNow.get(Calendar.MONTH) &&
				cal.get(Calendar.DATE)==MainActivity.rightNow.get(Calendar.DATE)){
			date = "Today";
		}else{			
			SimpleDateFormat date_format = new SimpleDateFormat("d MMM ");
			date = date_format.format(cal.getTime());
		}
					
			
		TextView dateV = (TextView) convertView.findViewById(R.id.drawer_date);
		dateV.setText(date);
		dateV.setTypeface(dapp.getTypefaceRobothin());
		
		
		// distance		
		String dis = new DecimalFormat("##0.00").format(crime.getDistance());
		
		TextView disV = (TextView) convertView.findViewById(R.id.drawer_distance);
		disV.setText(dis+" miles from here");
		disV.setTypeface(dapp.getTypefaceRobothin());
		
		
		return convertView;
	}
	
}
