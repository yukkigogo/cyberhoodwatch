package com.sociam.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.sociam.android.report.ReportActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

// this class for the start page. 
public class MainActivity extends FragmentActivity implements LocationListener{
  
	private GoogleMap mMap;
	private ArrayList<Crime> crimes;
	private ArrayList<Marker> markers;

	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	protected Double lat,lng; 
	
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    checkInternet();
    
    setContentView(R.layout.activity_main); 
    //map initialise
    setUpMapIfNeeded();
  }
  
  
  
  public boolean onCreateOptionsMenu(Menu menu) {
  	// TODO Auto-generated method stub
  	MenuInflater inflater = getMenuInflater();
  	inflater.inflate(R.menu.main, menu);
  	
  	return true;
  }
  
  
  @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
	  	switch(item.getItemId()){
	  		case R.id.go_new:
	  			// start new activity of report
	  			Intent intent = new Intent();
	  			intent.setClass(this, ReportActivity.class);
	  			startActivity(intent);
	  	
	  	}
	  
	  	
	  	return super.onOptionsItemSelected(item);
	}
  

  
  private void setUpMapIfNeeded() {
      // Do a null check to confirm that we have not already instantiated the map.  
      if (mMap == null) {
          // Try to obtain the map from the SupportMapFragment.
          mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                  .getMap();
          // Check if we were successful in obtaining the map.
          if (mMap != null) {
              setUpMap();
          }
      }
  }


  
  private void setUpMap() {
	  
	  mMap.setMyLocationEnabled(true);
	  UiSettings settings = mMap.getUiSettings();
	  settings.setCompassEnabled(true);
	  
	  
		  
	  // set up the map. 
	  getCrimesData();
	  plotCrime();
	
	// get current location
		  if(isLocationAvaiable()){
			  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));	
		  }else{
			  Log.e("sociam","Fail isLocationAvaiable");
		  }
	  
		  
		  
  }
 
	 @Override
	protected void onStart() {
		super.onStart();
		
		
		
	
	}

	public void onStop() {
		super.onStop();
		locationManager.removeUpdates(this);
	}
	 
  
  private boolean isLocationAvaiable() {
	// get location from network only	
	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
	LocationProvider provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
	if(lat==null || lng==null){
		Log.w("sociam", "fail obtaing location");
		return false;
	}else{
		return true;
	}
	
}



private void plotCrime() {
	// 
	markers = new ArrayList<Marker>();
	Marker amaker = null;
	for(Crime crime : crimes){
		amaker = mMap.addMarker(new MarkerOptions()
		.position(new LatLng(crime.getLat(), crime.getLon()))
		.title(crime.getDate()));		
	
	
		markers.add(amaker);
	}
	  
	  
  }



  private ArrayList<Crime> getCrimesData() {
		//getting crime data from the server
		
		crimes = new ArrayList<Crime>();
		try {
			BufferedReader br = new BufferedReader(
					readStreamFromURL(new URL("http://sociamvm-yi1g09.ecs.soton.ac.uk/androidcsv.php")));
			
			String currentLine;
			while((currentLine=br.readLine())!=null){
				Log.w("sociam",currentLine);
				String str[] = currentLine.split(",");
				Crime crime = new Crime();
				
				int id = 0;
				double lat = 0;
				double lon = 0;
				
				try {
					id = Integer.parseInt(str[0]);
					lat = Double.parseDouble(str[1]);
					lon = Double.parseDouble(str[2]);					
				} catch (Exception e) {
					// check the double can convert
					Log.e("sociam","fail convert double/interger from csv");
				}
				
				//set up crime values
				crime.setCrimeID(id);
				crime.setLat(lat);
				crime.setLon(lon);
				crime.setDate(str[3]);
				crime.setFilepath(str[4]);
				
				crimes.add(crime);
			}
			
			br.close();
		} catch (Exception e) {
			// bufferreader catch
			Log.e("sociam",e.getMessage());
		}
		
		return crimes;
  }
	
	
	
	private InputStreamReader readStreamFromURL(URL url) {
		// create InputStremReader from URL
		try{
	    	HttpURLConnection con =(HttpURLConnection) url.openConnection();
	    	return new InputStreamReader(con.getInputStream());
		}catch (IOException e){
			Log.e("sociam",e.getMessage());
			return null;
		}	
	}



// make sure Internet is avaiable
  private void checkInternet(){
	  
	  if(!isNetworkAvailable()){
		Log.w("sociam", "Network not avaiable....");
		AlertDialog.Builder alb = new AlertDialog.Builder(this);
		alb.setTitle("Warning");
		alb.setMessage("Please Connect to the Internet");
		alb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Log.w("sociam", "Internet is not working");
		        finish();
		        Process.killProcess(Process.myPid());
			}
		});
		alb.setCancelable(true);
		AlertDialog alDialog = alb.create();
		alDialog.show();	
		  
	  }else{
		Log.w("sociam","Internet OK");  	
		// for http connection for 3.0 higher
		StrictMode.ThreadPolicy policy = new StrictMode.
		    		ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		  
	  }
	  
  }
  
	 private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}



	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		lat = location.getLatitude();
		lng = location.getLongitude();
		Log.v("sociam", "Lat map "+lat + "   Lon map "+lng);
	}
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}



  
}