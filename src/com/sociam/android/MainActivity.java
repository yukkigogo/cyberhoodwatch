package com.sociam.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.sociam.android.report.ReportActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.audiofx.BassBoost.Settings;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Time;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

// this class for the start page. 
@SuppressLint("ValidFragment")
public class MainActivity extends FragmentActivity implements LocationListener,
												OnInfoWindowClickListener{
  
	private GoogleMap mMap;
	private ArrayList<Crime> crimes;
	private ArrayList<Marker> markers;

	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	SharedPreferences sp; 
	
	private ListView listDrawer;
	private DrawerLayout drawerlayout;
	private ActionBarDrawerToggle mDrawerToggle;
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    // set for p
	sp = PreferenceManager.getDefaultSharedPreferences(this);		 
	if(sp.getBoolean("first_time", true)){
		// first time
		setUpOnlyOnce();
	 }else{
		 // secound time
		  Log.e("sociam","my currentID !!  "+ sp.getString("uuid", "something problem with uuid"));
  
	 }

    if(checkInternet()){
    
    setContentView(R.layout.activity_main); 
    //map initialise
    setUpMapIfNeeded();
    setbtn();
    }
  }
  
  private void setbtn() {
	  ImageButton ibtn = (ImageButton) findViewById(R.id.report_incident);
	  ibtn.setOnClickListener(new OnClickListener() {	
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClassName("com.sociam.android", "com.sociam.android.report.ReportActivity");
  			startActivity(intent);
			
		}
	});
  }

  	@Override
	protected void onResume() {
		super.onResume();
	   

  }
  
  	
  	
  @Override
  protected void onRestart() {
		super.onRestart();
	 

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
//	  	switch(item.getItemId()){
//	  		case R.id.go_new:
//	  			// start new activity of report
//	  			Intent intent = new Intent();
//	  			intent.setClass(this, ReportActivity.class);
//	  			startActivity(intent);  	
//	  	}
	  	return super.onOptionsItemSelected(item);
	}
  
  private void setUpOnlyOnce(){
	  Editor e = sp.edit();
	  e.putBoolean("first_time", false);
	  e.commit();
	
	  UUID uuid = UUID.randomUUID();
	  e.putString("uuid", uuid.toString());
	  e.commit();
	  Log.e("sociam","my ID!!  "+ uuid);
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
	
	  //set up current location using LocationManager
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
        
		List<String> allProvider =  locationManager.getAllProviders();
		for(int i = 0 ; i < allProvider.size() ; i++){
            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,(LocationListener) this);
        }
		
		//check either network/gps is abalialbe 
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && 
        		!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
        
        	new AlertDialog.Builder(this)
        	.setIcon(android.R.drawable.ic_dialog_info)
        	.setTitle("Warning")
        	.setMessage("Please enable location access")
        	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).show();
        	
        }
		
		//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	  
	  // set up the map. 
	  getCrimesData();
	  plotCrime();
	
	  //setup navigation drawer
	 // setNavigationDrawer();
	  
	  //setup inforwindow
	  mMap.setOnInfoWindowClickListener(this);
	  mMap.setInfoWindowAdapter(new CustomInfoAdapter());
		  
  }
 
//	 private void setNavigationDrawer() {
//		 drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//		 listDrawer = (ListView) findViewById(R.id.left_drawer);
//		 //listDrawer.setAdapter(new ArrayAdapter<Crime>(this, R.id.left_drawer,crimes));
//		 mDrawerToggle = new ActionBarDrawerToggle(this, drawerlayout, 
//				 R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer){
//			
//		        public void onDrawerClosed(View drawerView) {
//		            Log.i("sociam", "onDrawerClosed");
//		        }
//		        
//		        public void onDrawerOpened(View drawerView) {
//		            Log.i("sociam", "onDrawerOpened");
//		        }
//		        
//		        public void onDrawerSlide(View drawerView, float slideOffset) {
//		            super.onDrawerSlide(drawerView, slideOffset);
//		            Log.i("sociam", "onDrawerSlide : " + slideOffset);
//		        }
//		        
//		        public void onDrawerStateChanged(int newState) {
//		            Log.i("sociam", "onDrawerStateChanged  new state : " + newState);
//		        }
//		        
//		 };
//		 
//		 drawerlayout.setDrawerListener(mDrawerToggle);
//		 getActionBar().setDisplayHomeAsUpEnabled(true);
//		 getActionBar().setHomeButtonEnabled(true);
//		 
//	 }
//
//	    @Override  
//	    protected void onPostCreate(Bundle savedInstanceState) {  
//	        super.onPostCreate(savedInstanceState);  
//	        // Sync the toggle state after onRestoreInstanceState has occurred.  
//	        mDrawerToggle.syncState();  
//	    }  
//	  
//	    @Override  
//	    public void onConfigurationChanged(Configuration newConfig) {  
//	        super.onConfigurationChanged(newConfig);  
//	        mDrawerToggle.onConfigurationChanged(newConfig);  
//	    }  
	  

	 

	@Override
	protected void onStart() {
		super.onStart();	
	}

	public void onStop() {
		super.onStop();
		locationManager.removeUpdates(this);
	}
	 
  





  private void plotCrime() {
	// 
	markers = new ArrayList<Marker>();
	Marker amaker = null;
	for(int i=0;i<crimes.size();i++){
		
		if(crimes.get(i).getLocationLatLng()){
			amaker = mMap.addMarker(new MarkerOptions()
			.position(new LatLng(crimes.get(i).getLat(), crimes.get(i).getLon()))
			.title(Integer.toString(i))
			.snippet(crimes.get(i).getFilepath()));		
		
		
			markers.add(amaker);
		}else{
			Log.v("sociam", "Crime "+i+"no location information");
		}	
	}
	  
	  
  }



  @SuppressLint("SimpleDateFormat")
private ArrayList<Crime> getCrimesData() {
		//getting crime data from the server
		
		crimes = new ArrayList<Crime>();
		try {
			BufferedReader br = new BufferedReader(
					readStreamFromURL(new URL("http://sociamvm-yi1g09.ecs.soton.ac.uk/androidcsv.php")));
			
			String currentLine;
			while((currentLine=br.readLine())!=null){
				//Log.w("sociam",currentLine);
				String str[] = currentLine.split(",");
				
				int is_loc_latlon=0;
				is_loc_latlon = Integer.parseInt(str[8]);

				if(is_loc_latlon==1){
				Crime crime = new Crime();
				
				//for(int i=0;i<str.length;i++){
				//	Log.e("sociam","line " + i +" : "+str[i]);
				//}
				
				int crime_id = 0;
				int id_code=0;
				int pic_on=0;
				int is_address=0;
				int is_cat_text=0;
				double lat = 0;
				double lon = 0;
				int is_date_text=0;
				int severity=0;
				int up_thumb=0;
				int down_thumb=0;
				
				try {
					crime_id = Integer.parseInt(str[0]);
					id_code = Integer.parseInt(str[2]);
					pic_on = Integer.parseInt(str[3]);
					is_cat_text= Integer.parseInt(str[6]);
					is_address = Integer.parseInt(str[9]);
					lat = Double.parseDouble(str[10]);
					lon = Double.parseDouble(str[11]);	
					is_date_text = Integer.parseInt(str[14]);
					severity=Integer.parseInt(str[16]);
				} catch (Exception e) {
					// check the double can convert
					Log.e("sociam","fail convert double/interger from csv" + e.getMessage());
					
				}
				
				//set up crime values
				crime.setCrimeID(crime_id);
				crime.setUserID(str[1]);
				if(id_code==1) crime.setidcode(true); else crime.setidcode(false); 
				crime.setPicOn(pic_on);
				crime.setFilepath(str[4]);
				crime.setCategory(str[5]);
				if(is_cat_text==1) {
					crime.setisCategoryText(true); 
					crime.setCategoryText(str[7]);
				}else{ 
					crime.setisCategoryText(false);					
				}
				
				if(is_loc_latlon==1){
					crime.setLocationLatLon(true);
					crime.setLat(lat);
					crime.setLon(lon);
				}else{
					crime.setLocationLatLon(false);
				}
				
				if(is_address==1){
					crime.setisAddress(true);
					crime.setAddress(str[12]);
				}else{
					crime.setisAddress(false);
				}
				
				
				SimpleDateFormat  format = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");
				try {
					Date date = format.parse(str[13]);
					Calendar calender = Calendar.getInstance();
					calender.setTimeInMillis(date.getTime());
					crime.setCal(calender);

					
					//Log.v("sociam", crime.getDate().format2445());
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				crime.setDate(new Time(str[13]));
				
				if(is_date_text==1){
					crime.setIsDateText(true);
					crime.setDateText(str[15]);
				}else{
					crime.setIsDateText(false);
				}

				crime.setSeverity(severity);
				
				crime.setUpThumb(up_thumb);
				crime.setDownThumb(down_thumb);
					
				//Log.v("sociam","Lat Lon :"+crime.getLat()+" : "+crime.getLon() );
				
				crimes.add(crime);
				}
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
  private boolean checkInternet(){
	  
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
		return false;  
	  }else{
		Log.w("sociam","Internet OK");  	
		// for http connection for 3.0 higher
		StrictMode.ThreadPolicy policy = new StrictMode.
		    		ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		 return true; 
	  }
	  
  }
  
	 private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}

	 
	/*
	 * 
	 * android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location location) {
		// obtain the current position and move to the place
		LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 15);
		mMap.animateCamera(cameraUpdate);
		locationManager.removeUpdates(this);
		
	}
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}


	/*
	 * InfoWindow Customise
	 */
	private class CustomInfoAdapter implements InfoWindowAdapter{
		
		private final View mWindow;
		
		public CustomInfoAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.info_window, null);
		}
		
		@Override
		public View getInfoContents(Marker arg0) {
			return null;
		}

		@Override
		public View getInfoWindow(Marker maker) {
			render(maker,mWindow);
			return mWindow;
		}
		
		
		private void render(Marker maker, View view) {
			
			TextView category = (TextView) view.findViewById(R.id.view_category);
			//TextView cate_text = (TextView) view.findViewById(R.id.view_cat_detail);
			TextView date = (TextView) view.findViewById(R.id.view_date);
			TextView time = (TextView) view.findViewById(R.id.view_time);
			TextView trust = (TextView) view.findViewById(R.id.view_trust);
			TextView distrust = (TextView) view.findViewById(R.id.view_distrust);
			//ImageView imageView = (ImageView) view.findViewById(R.id.view_picture);
			
			int i = Integer.parseInt(maker.getTitle());
			Calendar cal = crimes.get(i).getCal();
			SimpleDateFormat date_format = new SimpleDateFormat("d MMM yyyy");
			SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
			
			
			
			category.setText("Category : "+crimes.get(i).getCategory());
			//cate_text.setText(crimes.get(i).getCategoryText());
			date.setText("Date : "+date_format.format(cal.getTime()));
			time.setText("Time : "+ time_format.format(cal.getTime()));
			trust.setText("trust : "+crimes.get(i).getUpThumbs());
			distrust.setText("distrust : "+crimes.get(i).getDownThumb());
			
			Log.v("sociam", "show the filepath "+ crimes.get(i).getFilepath());

			
			//imageView.setImageBitmap(Downloader.getImageFromURL(maker.getSnippet()));
			
			

		}
		
		
	}
	
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		
		int num = Integer.parseInt(marker.getTitle());
		
		InfoWindowDialogFragment iwdf = new InfoWindowDialogFragment(num);
		iwdf.show(getSupportFragmentManager(), "sociam");
		marker.hideInfoWindow();
			
	}

	public class InfoWindowDialogFragment extends DialogFragment{
		private int crimenum;
		public InfoWindowDialogFragment(int num) {
			this.crimenum=num;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Do you want to see more detail?")
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//open another dialog
							DetailDialogFragment ddf = new DetailDialogFragment(crimenum);
							ddf.show(getSupportFragmentManager(), "sociam");
							InfoWindowDialogFragment.this.getDialog().dismiss();
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//do nothing
							InfoWindowDialogFragment.this.getDialog().dismiss();
						}
					});
			
			return builder.create();
		}
		
		
	}
	
	public class DetailDialogFragment extends DialogFragment{
		private int crime_num;
		private Crime crime;
		public DetailDialogFragment(int num) {
			this.crime_num=num;
			crime = crimes.get(crime_num);
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			View view = getActivity().getLayoutInflater().inflate(R.layout.map_marker_detail_dialog, null);
			ImageView imv = (ImageView) view.findViewById(R.id.map_marker_picture);
			imv.setImageBitmap(Downloader.getImageFromURL(crime.getFilepath()));
			
			ArrayList<String> details = getDetails(crime);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
					android.R.layout.simple_expandable_list_item_1,details);
			ListView listview = (ListView) view.findViewById(R.id.map_maker_detail_listview);
			listview.setAdapter(adapter);
			
			builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DetailDialogFragment.this.getDialog().dismiss();
					
				}
			});
			builder.setView(view);
			
			return builder.create();
		}

		private ArrayList<String> getDetails(Crime crime) {
			
			ArrayList<String> str = new ArrayList<String>();
			 str.add("Category : "+crime.getCategory());
			 if(crime.getisCategoryText()) str.add(crime.getCategoryText());
			 if(crime.getIsAddress()) str.add("Address : "+crime.getAddress());
			 
			 	Calendar cal = crime.getCal();
				SimpleDateFormat date_format = new SimpleDateFormat("d MMM yyyy");
				SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
			 
			 str.add("Date : "+ date_format.format(cal.getTime()));
			 str.add("Time : "+ time_format.format(cal.getTime()));
			 if(crime.getIsAddress()) str.add(crime.getDateText());
			 
			 switch (crime.getSeverity()){
			 case 1: 
				 str.add("Not Serious");
				 break;
			 case 2:
				 str.add("Serious");
				 break;
			 case 3:
				 str.add("Very Serious");
				 break;
			 case 4:
				 str.add("Extremely Serious");				 
			default :
				break;
			 }
			 
			 str.add("Trust Level : "+crime.getUpThumbs());
			 str.add("Distrust Level : "+crime.getDownThumb());
			
			return str;
		}
		
		
	}
	

  
}