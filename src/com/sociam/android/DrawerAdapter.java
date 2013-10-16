package com.sociam.android;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

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

public class DrawerAdapter extends ArrayAdapter<Crime>{

	private LayoutInflater mLayoutInflater;
	private Location userposition;
	private Context mContext;
	
	public DrawerAdapter(Context context, int textViewResourceId, List<Crime> crime) {
		super(context, textViewResourceId,crime);
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
		SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
		hourmin= time_format.format(cal.getTime());
		
		TextView hourminV = (TextView) convertView.findViewById(R.id.drawer_time);
		hourminV.setText(hourmin);
		
		String date=null;
		if(cal.get(Calendar.YEAR)==MainActivity.rightNow.get(Calendar.YEAR) && 
				cal.get(Calendar.MONTH)==MainActivity.rightNow.get(Calendar.MONTH) &&
				cal.get(Calendar.DATE)==MainActivity.rightNow.get(Calendar.DATE)){
			date = "Today";
		}else{			
			SimpleDateFormat date_format = new SimpleDateFormat("d MMM yyyy");
			date = date_format.format(cal.getTime());
		}
					
			
		TextView dateV = (TextView) convertView.findViewById(R.id.drawer_date);
		dateV.setText(date);
		
		
		// distance
//		Location crimeloc = new Location("crimepoint");
//		crimeloc.setLatitude(crime.getLat());
//		crimeloc.setLongitude(crime.getLon());
//		
//		if(userposition==null)
//		Log.w("sociam","Yes");
//		
//		float distance1  = userposition.distanceTo(crimeloc);
//		double distance = distance1;
//		double disInMile = distance *  0.00062137119;

		
		String dis = new DecimalFormat("##0.00").format(crime.getDistance());
		
		TextView disV = (TextView) convertView.findViewById(R.id.drawer_distance);
		disV.setText(dis+" miles from here");
		
		
		return convertView;
	}
	
}
