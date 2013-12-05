package com.sociam.android;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

public class DataApplication extends Application {
	
	private HashMap<String, String> tagMap;
	
	
	private 	LocationManager locationManager;
	private  Location currentBestLocation;
	

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// set location
		//setMyLocationManager();
		
		// make user 
		String usertags = "female-general:student-general:susu-southampton";
		setTagMap(usertags);	
		
	}
	
	public void setTagMap(String usertags){
		tagMap = new HashMap<String, String>();
		
		if(usertags!=null){
			String[] tagWcat = usertags.split(":");
			for(String str:tagWcat){
				String[] ary=str.split("-");
				tagMap.put(ary[0], ary[1]);				
			}
		}
	}
		
	
	public HashMap<String,String> getTagMap4User(){
		return this.tagMap;
	}

	public Typeface getTypefaceRobothin() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		
		return robothin;
	}


//	  private void setMyLocationManager(){
//
//		  //set up current location using LocationManager
//		  locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
//	        
//		//Location listener set as old location
//			currentBestLocation = getLastLocationBest(
//					locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER),
//					locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
//			//Log.e("sociam", "renew location and provider " + currentBestLocationlocation.getProvider());
//
//			
//			
//		//LocationListener	
//		 LocationListener locationListener = new LocationListener() {
//			
//			@Override
//			public void onStatusChanged(String provider, int status, Bundle extras) {}
//			
//			@Override
//			public void onProviderEnabled(String provider) {}
//			
//			@Override
//			public void onProviderDisabled(String provider) {}
//			
//			@Override
//			public void onLocationChanged(Location location) {
//				if(isBetterLocationisBetterLocation(location, currentBestLocation))
//						currentBestLocation = location;
//						Log.e("sociam", "renew location and provider " + currentBestLocation.getProvider());
//				
//			}
//			
//		}; 
//		  
//			
//			// set Location provider
//			List<String> allProvider =  locationManager.getAllProviders();
//			for(int i = 0 ; i < allProvider.size() ; i++){
//	            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,(LocationListener) this);
//	        }
//				 
//			
//			/*
//			 * /check either network/gps is abalialbe if not show the aleart dialog
//			 * 
//			 */
//	        if((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) && 
//	        		(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))){
//	        
//	         	
//	        	new AlertDialog.Builder(this)
//	        	.setIcon(android.R.drawable.ic_dialog_info)
//	        	.setTitle("Warning")
//	        	.setMessage("Please enable location access")
//	        	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						startActivity(new Intent(
//						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//					}
//				})
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Process.killProcess(Process.myPid());
//					}
//				}).show();
//	        	
//	        }
//
//	  }
//	
//	  
//	  
////		private Location getLastLocationBest(Location loc1, Location loc2){
////			if(isBetterLocationisBetterLocation(loc1, loc2)){
////				return loc1;
////			}else{
////				return loc2;
////			}
////			
////			
////		}
	
}
