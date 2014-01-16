package com.sociam.android;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.sociam.android.model.Crime;
import com.sociam.android.model.MsgMarker;
import com.sociam.android.model.RecieveMessage;
import com.sociam.android.model.ReplyMessage;
import com.sociam.android.model.Tag;
import com.sociam.android.report.ReportActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.audiofx.BassBoost.Settings;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.Layout;
import android.text.format.Time;
import android.text.style.BulletSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

// this class for the start page. 
@SuppressLint("ValidFragment")
public class MainActivity extends FragmentActivity implements LocationListener,
												OnInfoWindowClickListener{
  
	 GoogleMap mMap;
	protected Context context;
	private SharedPreferences sp; 
	private DataApplication dapp;
	boolean msg_window_visible=false;
	Timer timer;
	myTimerTask timerTask;
	
	// Crime data
	private ArrayList<Crime> crimes;
	private ArrayList<Crime> sortedCrimesDis;
	private ArrayList<Marker> markers;
	private HashMap<Integer,Marker> mapMarkers;
	ArrayList<MsgMarker> aryMsgs ;

	// for messaging system
	//private HashMap<Marker, RecieveMessage> msg_maker_hash;
	private boolean isFinishSetupReplysArray; 
	//Location
	private static final int ONE_MINUTES = 1000 * 60 * 1;
	private Location currentBestlocation;
	protected LocationManager locationManager;
	//protected LocationListener locationListener;
	private boolean oldlocation=false;
	private LatLng latlng;
	private int mapmode=0;
	private double lat1;
	private double lon1;

	
	//drawer
	private DrawerLayout mDrawerLayout;
	private ListView mListView;
	private ActionBarDrawerToggle mDrawerToggle;
	
	// right now
	public static final Calendar rightNow = Calendar.getInstance();
	
	

	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    // set for general 
	sp = PreferenceManager.getDefaultSharedPreferences(this);		 
	dapp = (DataApplication)this.getApplication();

	if(sp.getBoolean("first_time", true)){
		// first time
		setUpOnlyOnce();
	 }else{
		 // secound time
		  Log.e("sociam","my currentID !!  "+ sp.getString("uuid", "something problem with uuid"));
  
	 }

    if(checkInternet()){
    
    setContentView(R.layout.activity_main); 
    // start location manager
    setMyLocationManager();
  
    // obtain map data from the server
    getCrimesData();
    
    // compute distance userpoistion and crimes
    getDistanceFromHere(currentBestlocation, crimes);
    
    //map initialise
    setUpMapIfNeeded();
    setbtn();
    
    
    //drawer instanceate
    setDrawer();
    
    //update the tags
     updateTags();
     
     //update auto function
     timer = new Timer();
     myTimerTask mTask = new myTimerTask(this);
     timer.schedule(mTask, 10000, 120000);
    }
  }
	@Override
	protected void onResume() {
		super.onResume();
		//myTimer.scheduleAtFixedRate(myTimerTask, 0, 60000);
		
	}	
	
	@Override
	protected void onRestart() {
			super.onRestart();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	
	}

	public void onStop() {
		super.onStop();
		locationManager.removeUpdates(this);
	
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	

	/*
	 * helper function to reload the data from the server
	 */
	public void reloadData(){
		Log.e("sociam", "every 10 sec - go inside!!");
			Log.e("sociam", "every 10 sec");
	    mMap.clear();	
	    getCrimesData();
	    getDistanceFromHere(currentBestlocation, crimes);
	    setUpMap();
	    setbtn();
	    updateTags();
		
	}
	

	
	private void updateTags() {
	  UpdateTagAsyncTask task = new UpdateTagAsyncTask(this);
	  task.execute("sociam");
	
  }



private void setDrawer() {
	  
	  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	  mListView = (ListView) findViewById(R.id.list_drawer);
	  
	 
	  // sort by time
	 // Collections.reverse(crimes);
	  sortedCrimesDis = new ArrayList<Crime>(crimes);
	  Collections.sort(sortedCrimesDis);
	  DrawerAdapter mAdapter = new DrawerAdapter(this,0, sortedCrimesDis,dapp);
	  
	 
	  
	  
	  mListView.setAdapter(mAdapter);
  
	  mListView.setOnItemClickListener(new DrawerItemClickListener());
	  
   
  
      mDrawerToggle = new ActionBarDrawerToggle(this, 
    		  									mDrawerLayout, 
    		  									R.drawable.ic_drawer, 
    		  									R.string.open_drawer,
    		  									R.string.close_drawer){
    	  
    	  @Override
    	public void onDrawerClosed(View drawerView) {    		  
    		  //super.onDrawerClosed(drawerView);
    	}
    	  
    	@Override
    	public void onDrawerOpened(View drawerView) {   										
   			//super.onDrawerOpened(drawerView);
    												}
    	  
    	  
      };
  
      
      mDrawerLayout.setDrawerListener(mDrawerToggle);
  
      getActionBar().setDisplayHomeAsUpEnabled(true);
      getActionBar().setHomeButtonEnabled(true);
  }


  /*
   * Helper function for nativation drawer
   */

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      // Sync the toggle state after onRestoreInstanceState has occurred.
      mDrawerToggle.syncState();
   }
  
  
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      // Pass any configuration change to the drawer toggls
      mDrawerToggle.onConfigurationChanged(newConfig);
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
	  
	 ImageButton msgBtn = (ImageButton) findViewById(R.id.report_msg);
	 msgBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			setMyLocationManager();
			Intent intent = new Intent();
			intent.setClassName("com.sociam.android", "com.sociam.android.message.MessageActivity");
			intent.putExtra("lat", currentBestlocation.getLatitude());
			intent.putExtra("lon", currentBestlocation.getLongitude());
			startActivity(intent);
			
		}
	});
  }

 
  
  
  public boolean onCreateOptionsMenu(Menu menu) {
  	MenuInflater inflater = getMenuInflater();
  	inflater.inflate(R.menu.main, menu);
  	
  	return true;
  }
  
  
  @Override
	public boolean onOptionsItemSelected(MenuItem item) {

	  	
	  if (mDrawerToggle.onOptionsItemSelected(item)) {
         
		  return true;
        }
	  
	  
	  switch(item.getItemId()){
	  		case R.id.action_settings :
	  			// show the participant information 
	  			ConsentFormDialog consentFormDialog = new ConsentFormDialog();
	  			consentFormDialog.show(getSupportFragmentManager(), "sociam");
	  			break;
	  			
	  		case R.id.action_consentform:
	  			// show the consent form
	  			ConsentFormDialog2 consentFormDialog2 = new ConsentFormDialog2();
	  			consentFormDialog2.show(getSupportFragmentManager(), "sociam");
	  			break;
	  	
	  		case R.id.action_gotofeedback:
	  			// show the consent form
	  			AlertDialog.Builder alb1 = new AlertDialog.Builder(this);
	  			alb1.setTitle("Notification");
	  			alb1.setMessage("Go to feedback page?");
	  			alb1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW, 
						Uri.parse("https://docs.google.com/forms/d/1D4Ol0Jx9eIpvlqwxZ6GSN6XHYVTMhbxzibmNLRwyUDQ/viewform"));
						startActivity(intent);
					}
				});
	  			alb1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
	  			
	  			AlertDialog albc = alb1.create();
	  			albc.show();
	  			
	  			break;	
	  			
	  		case R.id.menu_settings:
	  			
	  			if(sp.getString("username", null)!=null){
	  				UserPreferenceDialog id = new UserPreferenceDialog();
	  				id.show(getSupportFragmentManager(), "sociam");
	  			}else{
	  				
					AlertDialog.Builder alb = new AlertDialog.Builder(this);
					alb.setTitle("You Don't Have Username Yet");
					alb.setMessage("Do you want to sing up?");
					alb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//open another activity to register users	
							Intent intent = new Intent();
							intent.setClassName("com.sociam.android", 
									"com.sociam.android.user.UserRegisterActivity");
							startActivity(intent);
							
							
						}
					});
					alb.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					
					AlertDialog alertDialog = alb.create();
					alertDialog.show();
	  				
	  			}
	  			break;
	  			
	  		case R.id.menu_3d:
	  			if(mapmode==0 || mapmode==1){
		  			if(mapmode==0){
		  				currentBestlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		  				latlng = new LatLng(currentBestlocation.getLatitude(), currentBestlocation.getLongitude());
		  			}
	  				
		  			LatLng latlngnow = mMap.getCameraPosition().target;
	  				CameraPosition cameraPosition = new CameraPosition.Builder()
					.zoom(15)
					.target(latlngnow)
					.build();
		  			mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		  			mapmode=2;
	  			}else if(mapmode==2){
	  				change3Dview(mMap.getCameraPosition().target);
	  			}
	  			break;
	  		
//	  		case R.id.menu_reload:
//	  			reloadData();
//	  			break;
	  }
	  	
	
	  	
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
	  
	  
	  e.putInt("tagver", 1);
	  e.commit();
	  
	  int icon =(new Random()).nextInt(5);
	  e.putInt("icon", icon);
	  e.commit();
	  Log.e("sociam","my ICON!!  "+ icon);

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


  private void setMyLocationManager(){
	  Log.v("sociam","Loading Location Mangaer......");
	  //set up current location using LocationManager
		
	  locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
      
	  currentBestlocation = getLastLocationBest(
				locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER),
				locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	  
	  
		List<String> allProvider =  locationManager.getAllProviders();
		for(int i = 0 ; i < allProvider.size() ; i++){
            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,(LocationListener) this);
        }
		

			 
		//check either network/gps is abalialbe 
        if((!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) && 
        		(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))){
        
         	
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

  }
  
  
  
  
  private void setUpMap() {
	  
	  mMap.setMyLocationEnabled(true);
	  UiSettings settings = mMap.getUiSettings();
	  settings.setCompassEnabled(true);
	  settings.setZoomControlsEnabled(false);
	
	  //update heropoint
	  if(sp.contains("username")){
	  	UserHeropointAsyncTask asyncTask = new UserHeropointAsyncTask(this);
	  	asyncTask.execute(sp.getString("username", null));
	  }
	  // set up the map. 	 
	  plotCrime();
	
	  //setup message
	  getMessagesAndMarker();
	  isFinishSetupReplysArray = setReplayMsgHashmap();
	  
	  //setup inforwindow
	  mMap.setOnInfoWindowClickListener(this);
	  mMap.setInfoWindowAdapter(new CustomInfoAdapter());
	  
	  
  }
 



  private void plotCrime() {
	// 
	//markers = new ArrayList<Marker>();
	mapMarkers = new HashMap<Integer, Marker>();
	Marker amaker = null;
	for(int i=0;i<crimes.size();i++){
		
		Calendar now_5 = Calendar.getInstance();
		now_5.add(Calendar.HOUR, -5);
		Calendar now_24 = Calendar.getInstance();
		now_24.add(Calendar.DAY_OF_MONTH, -1);
		Calendar ctime = crimes.get(i).getCal();
		
		if(crimes.get(i).getLocationLatLng()){
				
				if(ctime.compareTo(now_5)>0){	
					amaker = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(crimes.get(i).getLat(), crimes.get(i).getLon()))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.incident_red))
					.title(Integer.toString(i))
					.snippet(crimes.get(i).getFilepath()));		
				}else if(ctime.compareTo(now_24) > 0){
					amaker = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(crimes.get(i).getLat(), crimes.get(i).getLon()))
					.title(Integer.toString(i))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.incident_yellow))
					.snippet(crimes.get(i).getFilepath()));		
				} else{	
				amaker = mMap.addMarker(new MarkerOptions()
				.position(new LatLng(crimes.get(i).getLat(), crimes.get(i).getLon()))
				.title(Integer.toString(i))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.incident_blue))
				.snippet(crimes.get(i).getFilepath()));		
				}
			
			
			
			mapMarkers.put(crimes.get(i).getCrimeID(), amaker);
			//markers.add(amaker);
		}else{
			Log.v("sociam", "Crime "+i+"no location information");
		}	
	}
	  
	  
  }


  private void setLatestLatLon(){
		    	
		    if(currentBestlocation!=null){	
		    	lat1 = currentBestlocation.getLatitude();
		    	lon1 = currentBestlocation.getLongitude();
		    }else{
		    	Location lastknown = getLastLocationBest(
						locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER),
						locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		    			
		    			
		    	lat1 = lastknown.getLatitude();
		    	lon1 = lastknown.getLongitude();
		    }
		  		    
  }
  
  
  private String getData(int type){
	  	
	  	String response = null;
	  	HttpClient httpClient = new DefaultHttpClient();
	  	HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/androidcsv.php");
	  	
	    ResponseHandler<String> responseHandler =new BasicResponseHandler();
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    	
	    	setLatestLatLon();
	    	
	    try{
	    	multipartEntity.addPart("type",new StringBody(Integer.toString(type)));
	    	
	    	multipartEntity.addPart("lat",new StringBody(Double.toString(currentBestlocation.getLatitude())));
	    	multipartEntity.addPart("lon",new StringBody(Double.toString(currentBestlocation.getLongitude())));

		    httpPost.setEntity(multipartEntity);
		    response=httpClient.execute(httpPost, responseHandler);
		    
	    }catch(Exception e){
	    	Log.e("sociam",e.getMessage());
	    }  
	 // Log.e("sociam", type+" "+response);
	  return response;
  }
  
  
  
  

  @SuppressLint("SimpleDateFormat")
private ArrayList<Crime> getCrimesData() {
	  	
	  	String response1 = getData(0);
	      
		//getting crime data from the server
		crimes = new ArrayList<Crime>();
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response1.getBytes())));
			
			
			String currentLine;
			while((currentLine=br.readLine())!=null){
				Log.e("sociam", currentLine);

				String str[] = currentLine.split(",");
				
				int is_loc_latlon=0;
				is_loc_latlon = Integer.parseInt(str[8]);

				if(is_loc_latlon==1){
				Crime crime = new Crime();
				
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
					up_thumb = Integer.parseInt(str[17]);
					down_thumb = Integer.parseInt(str[18]);
				} catch (Exception e) {
					// check the double can convert
					Log.e("sociam","fail convert double/interger from csv");
					
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
				
				// use calender
				
				SimpleDateFormat  format = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");
				try {
					Date date = format.parse(str[13]);
					Calendar calender = Calendar.getInstance();
					calender.setTimeInMillis(date.getTime());
					crime.setCal(calender);

					
				} catch (Exception e) {
					Log.e("sociam", e.getMessage());
				}
				
				crime.setDate(new Time(str[13]));
				
				// till here
				
				if(is_date_text==1){
					crime.setIsDateText(true);
					crime.setDateText(str[15]);
				}else{
					crime.setIsDateText(false);
				}

				crime.setSeverity(severity);
				
				crime.setUpThumb(up_thumb);
				crime.setDownThumb(down_thumb);
				
				if(str.length>19){
					crime.setHappenwho(str[19]);
				}else{
					crime.setHappenwho("NULL");
				}
				
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
	
	

  private void getMessagesAndMarker() {
	   aryMsgs = new ArrayList<MsgMarker>();
		//HashMap<Marker,RecieveMessage> msg_maker_hash = new HashMap<Marker, RecieveMessage>();
		
		String response = getData(1);
		
		
		try{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getBytes())));
			
			String currentLine;
			int i =0;
			while((currentLine=br.readLine())!=null){
				//Log.e("sociam","should be message line \n"+currentLine);
				
				currentLine = currentLine.replaceAll("\"", "");

				
				RecieveMessage r_msg = new RecieveMessage();
				String str[] = currentLine.split(",");

				// tag can be empty 
				try {
					r_msg.setID(Integer.parseInt(str[0]));						
				} catch (Exception e) {
					Log.e("sociam","integer point");

				}

				String u_name = str[1];
				
				r_msg.setUser(u_name);
				r_msg.setIdCode(Integer.parseInt(str[2]));

				r_msg.setLat(Double.parseDouble(str[3]));

				r_msg.setLng(Double.parseDouble(str[4]));
				
				
				
				try {
					SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = format.parse(str[5]);

					Calendar calender = Calendar.getInstance();
					calender.setTimeInMillis(date.getTime());
					r_msg.setTime(calender);

					
				} catch (Exception e) {
					Log.e("sociam", "Date Message problem MainActivity:777");
				}
				
				String msgtext = str[6].replaceAll("\"", "");

				r_msg.setMsg(msgtext);
				r_msg.setUpThumb(Integer.parseInt(str[8]));
				

				r_msg.setDownThumb(Integer.parseInt(str[9]));
				
				//tags 
				String tagsString = str[7];
				try{
					Log.e("sociam", tagsString);
				}catch(Exception e){
					Log.e("sociam", "problem of tag");
				}
				
				if(tagsString.indexOf("-")>-1){
					String[] tags = tagsString.split(":");

					for(String strs :tags){

						String[] tagcate =strs.split("-");

						Tag tag_m=null;
						if(tagcate[1]==null){ 

							tag_m	= new Tag(tagcate[0], "");
						}else{

							tag_m = new Tag(tagcate[0],tagcate[1]);							
						}							
						r_msg.addTag(tag_m);
						
						//Marker marker = getMsgMarker(r_msg, i);
						//msg_maker_hash.put(marker, r_msg);
						aryMsgs.add(getMsgMarker(r_msg, i));						
						i++;
						
					}
				}else{
					//Marker marker = getMsgMarker(r_msg, i );
					//msg_maker_hash.put(marker, r_msg);
					aryMsgs.add(getMsgMarker(r_msg, i));		
					i++;
				}
				
			}
			
			
		}catch(Exception e){
			Log.e("sociam", "Problem occurs MainActivity Line 815");
		}
		
		
	  }

   private boolean setReplayMsgHashmap(){
	   final ArrayList<ReplyMessage> retunAry = new ArrayList<ReplyMessage>();
	   setLatestLatLon();
	   GetDataFromServerAsyncTask asyncTask = new GetDataFromServerAsyncTask(new GetDateFromCallback() {
		
		@Override
		public void onTaskDone(String response) {
			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								new ByteArrayInputStream(response.getBytes())));
				String currentLine;
				while((currentLine=br.readLine())!=null){
					String str[] = currentLine.split(",");
					ReplyMessage replymsg = new ReplyMessage();
					replymsg.setID(Integer.parseInt(str[0]));
					int parenet_id = Integer.parseInt(str[1]);
					replymsg.setParenetId(parenet_id);
					replymsg.setUser(str[2]);
					if(str[3].equals("0")) replymsg.setIdCode(false); 
					else replymsg.setIdCode(true);
					
					SimpleDateFormat  format = new SimpleDateFormat("\"yyyy-MM-dd HH:mm:ss\"");
					try {
						Date date = format.parse(str[6]);
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis(date.getTime());
						replymsg.setTime(cal);
					} catch (Exception e) {
						// TODO: handle exception
					}
						
					replymsg.setMsg(str[7]);
					
					retunAry.add(replymsg);
				}
				
				// set up each msg-reply
				for(MsgMarker m : aryMsgs){
					   RecieveMessage revm = m.getRecieveMessage();
					   for(ReplyMessage msg : retunAry){
						   if(msg.getParentId()==revm.getID()){
							   revm.setReplyMsg(msg);
						   }
					   }
				 }
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	});
	   asyncTask.execute(
			   "2",
			   Double.toString(currentBestlocation.getLatitude()),
			   Double.toString(currentBestlocation.getLongitude())
			   );

	   return true;
   }
  
   public boolean isFinishReplyUpdate(){
	   return this.isFinishSetupReplysArray;
   }
   
  private MsgMarker getMsgMarker(RecieveMessage rm, int i){
	  	Marker marker = mMap.addMarker(new MarkerOptions()
		.position(new LatLng(rm.getLat(), rm.getLng()))
		.snippet(Integer.toString(i))
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.msg_n)));		
	  	
	  	MsgMarker msgmarker = new MsgMarker(marker, rm, i);
	  	
	  	
	  	return msgmarker;
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
		        //Process.killProcess(Process.myPid());
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
	 * Location Listener implementations 
	 */
	@Override
	public void onLocationChanged(Location location) {
		
		if(isBetterLocationisBetterLocation(location, currentBestlocation))
				currentBestlocation = location;
		
		
		// obtain the current position and move to the place		
		if(mapmode==0){ // initialisation - change view
			change3Dview(new LatLng(currentBestlocation.getLatitude(), currentBestlocation.getLongitude()));
		}
		
		//Log.e("sociam","now we call onlocation changed");

		if(oldlocation){
			oldlocation=false;
		    getDistanceFromHere(getLocation(), crimes);
		    setDrawer();
		}
		
		
	}
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	/*
	 * change 3d view
	 */
	
	public void change3Dview(LatLng latlngs){
		CameraPosition cameraPosition = new CameraPosition.Builder()
			.tilt(60)
			.target(latlngs)
			.zoom(18)
			.build();
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		mapmode=1;
		
	}

	/*
	 * InfoWindow Customise
	 */
	@SuppressLint("ResourceAsColor")
	private class CustomInfoAdapter implements InfoWindowAdapter{
		
		private  View mWindow;
		
		TextView tx_msg ;
		TextView tx_user ;
		TextView tx_time;
		LinearLayout layout;
		
		
		public CustomInfoAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.info_window, null);
		
		}
		
		@Override
		public View getInfoContents(Marker arg0) {
			return null;
		}

		@Override
		public View getInfoWindow(Marker mk) {
			mWindow = getLayoutInflater().inflate(R.layout.info_window, null);
			if(mk.getTitle()!=null){
				
				if(tx_msg!=null){
					tx_msg.setText("");
					tx_user.setText("");
					tx_time.setText("");
					//layout.setBackgroundColor(Color.TRANSPARENT);
					layout.setVisibility(View.INVISIBLE);
				}
				
				render(mk,mWindow);
				
				return mWindow;
				
			}else{
				
				mk.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.msg_p));

				int index = Integer.parseInt(mk.getSnippet());
				for(MsgMarker m : aryMsgs){
					if(m.getIndex()!=index){
						mk.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.msg_n));
					}else{
						setMsgMarker(m.getRecieveMessage(), mk);												
					}
				}
				
				layout.setVisibility(View.VISIBLE);
				msg_window_visible=true;
				
			
				
				
//					for(Marker key : msg_maker_hash.keySet()){
//						RecieveMessage rm = msg_maker_hash.get(key);
//						if(rm==null)
//							Log.e("sociam", "recievemessage is nullT.T");
//						else
//							Log.e("sociam", "recive msg is not null" + rm.getMsg());
//						
//					}
//					if(msg_maker_hash.get(marker)==null) Log.e("sociam", "because you cannot trust marker object!");

				
				return null;

			}

			}
		
		
		private void setMsgMarker(final RecieveMessage rm, Marker mk) {

			
			layout = (LinearLayout) findViewById(R.id.message_screen_onmain);
			layout.setBackgroundColor(R.color.half_black);
			layout.setClickable(true);
			
			tx_msg = (TextView) findViewById(R.id.msg_text);
			tx_msg.setTypeface(dapp.getTypefaceRobothin());
			tx_msg.setText(rm.getMsg());		
			
			
			tx_user = (TextView) findViewById(R.id.msg_username);
			tx_user.setTypeface(dapp.getTypefaceRobothin());
			if(rm.getIdCode()==1){
				tx_user.setText("Anonymous says...");
			}else{
				
				tx_user.setText(rm.getUser()+ " says...");
			}
			
			SimpleDateFormat date_format = new SimpleDateFormat("HH:mm");
			SimpleDateFormat datewk = new SimpleDateFormat("E", Locale.UK);
			Calendar t = rm.getTime();
			
				
			tx_time = (TextView) findViewById(R.id.msg_text_time);
			tx_time.setTypeface(dapp.getTypefaceRobothin());				
			tx_time.setText(datewk.format(rm.getTime().getTime())+" "+date_format.format(t.getTime()));
			
			ImageView msg_img_upthumb = (ImageView) findViewById(R.id.msg_img_upthumb);
			msg_img_upthumb.setImageResource(R.drawable.thumb_up);
			
			TextView msg_num_upthumb = (TextView) findViewById(R.id.msg_num_upthumb);
			msg_num_upthumb.setTypeface(dapp.getTypefaceRobothin());
			msg_num_upthumb.setText(Integer.toString(rm.getUpThumb()));
			
			ImageView msg_img_downthumb = (ImageView) findViewById(R.id.msg_img_downthumb);
			msg_img_downthumb.setImageResource(R.drawable.thumb_down);
			
			TextView msg_num_downthumb = (TextView) findViewById(R.id.msg_num_downthumb);
			msg_num_downthumb.setTypeface(dapp.getTypefaceRobothin());
			msg_num_downthumb.setText(Integer.toString(rm.getDonwThumb()));
			
			ImageView extend_arrow = (ImageView) findViewById(R.id.msg_downarrow);
			extend_arrow.setImageResource(R.drawable.downarrows);
			//extend_arrow.setClickable(true);
			
			
			layout.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					layout.setBackgroundColor(Color.CYAN);
				}
			});
			
			
			
			layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//Log.e("sociam","Clicked can you see it?");
					MainMessageDetailFragmentDialog detailFragmentDialog = new MainMessageDetailFragmentDialog(rm);
					detailFragmentDialog.show(getSupportFragmentManager(), "sociam");
					
					
				}
				
				
			});
			
		}

		private void render(Marker maker, View view) {
			
			TextView auther = (TextView) view.findViewById(R.id.view_name);
			//TextView cate_text = (TextView) view.findViewById(R.id.view_cat_detail);
			TextView date = (TextView) view.findViewById(R.id.view_datetime);
			TextView time = (TextView) view.findViewById(R.id.view_category);
			TextView trust = (TextView) view.findViewById(R.id.view_trust);
			TextView distrust = (TextView) view.findViewById(R.id.view_distrust);
			
			
			
			//ImageView imageView = (ImageView) view.findViewById(R.id.view_picture);
			
			
			int i = Integer.parseInt(maker.getTitle());
			Crime crime = crimes.get(i);
			Calendar cal = crime.getCal();
			SimpleDateFormat date_format = new SimpleDateFormat("d MMM ");
			SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
			
			//who happen
			String happen=null;
			if(crime.getHappenwho().equals("NULL")) happen = " reported...";
			else if(crime.getHappenwho().equals("tome")) happen = " reported...";
			else if(crime.getHappenwho().equals("saw")) happen = " saw...";
			else if(crime.getHappenwho().equals("help")) happen = " needs to help....";

			
			auther.setTypeface(dapp.getTypefaceRobothin());
			if(crime.getIdCode())	auther.setText("Anonymous" + happen);
			else auther.setText(crime.getUserID() + happen);
			
			
			
			date.setText(date_format.format(cal.getTime())+" "+time_format.format(cal.getTime()));
			date.setTypeface(dapp.getTypefaceRobothin());
			
			time.setText(crimes.get(i).getCategory());
			time.setTypeface(dapp.getTypefaceRobothin());
			
			trust.setText(Integer.toString(crimes.get(i).getUpThumbs()));
			distrust.setText(Integer.toString(crimes.get(i).getDownThumb()));
			
			Log.v("sociam", "show the filepath "+ crimes.get(i).getFilepath());
			

		}
		
		
	}
	
	
	@Override
	public void onInfoWindowClick(Marker marker) {
		
		int num = Integer.parseInt(marker.getTitle());
		

		//DetailDialogFragment ddf = new DetailDialogFragment(num);
		//ddf.show(getSupportFragmentManager(), "sociam");
		
		MainReportDetailDialogFragment ddf = new MainReportDetailDialogFragment(crimes.get(num));
		ddf.show(getSupportFragmentManager(), "sociam");
		marker.hideInfoWindow();
			
	}

	
	public class MainReportDetailDialogFragment extends DialogFragment {

		Crime crime;
//		SharedPreferences sp;
//		DataApplication dapp;
		public MainReportDetailDialogFragment(Crime crime) {
			this.crime = crime;
//			sp = PreferenceManager.getDefaultSharedPreferences(con);		 
//			dapp = (DataApplication) con.getApplicationContext();
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			setRetainInstance(true);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			View view = getActivity().getLayoutInflater().inflate(R.layout.main_report_detail, null);
			
			view = setDetails(crime, view);
			
			builder.setView(view);		
			return builder.create();
		}



		
		private View setDetails(final Crime crime, View v) {
			View view = v;
			
			//who happen
			String happen=null;
			if(crime.getHappenwho().equals("NULL")) happen = " reported...";
			else if(crime.getHappenwho().equals("tome")) happen = " reported...";
			else if(crime.getHappenwho().equals("saw")) happen = " saw...";
			else if(crime.getHappenwho().equals("help")) happen = " needs to help....";
			
			TextView auther = (TextView) view.findViewById(R.id.mrd_auther);
			auther.setTypeface(dapp.getTypefaceRobothin());
		

			
			if(crime.getIdCode()) auther.setText("Anonymous" + happen);
			else auther.setText( crime.getUserID()+ happen);
			
			if(crime.getPicON()==1){
	            ImageView imv = (ImageView) view.findViewById(R.id.mrd_picture);
	            imv.setImageBitmap(Downloader.getImageFromURL(crime.getFilepath()));

			}
			// adding the elements dynamically
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.mrd_layout);
			
			// category
			LinearLayout.LayoutParams pane = new LayoutParams(
					LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			TextView category = new TextView(getActivity());
			category.setTypeface(dapp.getTypefaceRobothin());
			category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			category.setLayoutParams(pane);
			
			if((crime.getCategory().indexOf("-") == -1) && crime.getCategory().indexOf("Other") > -1 ){
				category.setText(" Some incident");
			}else if(crime.getCategory().indexOf("-") == -1){
				if(crime.getCategory().indexOf("ASB")>-1) category.setText("Anti Social Behaviour");
				else category.setText(crime.getCategory());
			}else if(crime.getCategory().indexOf("-" ) > -1){
				String str[] = crime.getCategory().split("-");
				if(str[0].indexOf("ASB") >-1) category.setText("Anti Social Behaviour" +" - " + str[1]);
				else category.setText(str[0]+" - "+str[1]);
			}
			layout.addView(category);

			
			// detail of category
			if(crime.getisCategoryText()) {
				LinearLayout.LayoutParams pane1 = new LayoutParams(
						LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				TextView sub_cate = new TextView(getActivity());
				sub_cate.setTypeface(dapp.getTypefaceRobothin());
				sub_cate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
				sub_cate.setLayoutParams(pane1);
				
				sub_cate.setText("   "+crime.getCategoryText());
				layout.addView(sub_cate);
			}
			
			if(crime.getIsDateText()){
				LinearLayout.LayoutParams pane2 = new LayoutParams(
						LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				TextView sub_time = new TextView(getActivity());
				sub_time.setTypeface(dapp.getTypefaceRobothin());
				sub_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				sub_time.setLayoutParams(pane2);
				
				sub_time.setText("   "+crime.getDateText());
				layout.addView(sub_time);
				
			}
			
			
			// seriousness 
			 String seriousness=null;
			 switch (crime.getSeverity()){
			 case 1: 
				 seriousness = "Not serious incident";
				 break;
			 case 2:
			 	seriousness ="Serious incident";
				 break;
			 case 3:
				 seriousness ="Very Serious incident";
				 break;
			 case 4:
				 seriousness = "Extremely serious incident";				 
			default :
				break;
			 }
			TextView serious = new TextView(getActivity());
			serious.setTypeface(dapp.getTypefaceRobothin());
			serious.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			serious.setLayoutParams(pane);
			serious.setText(seriousness);
			layout.addView(serious);
			
		
			
			
			// time and thumb_nums  
			Calendar cal = crime.getCal();
			SimpleDateFormat date_format = new SimpleDateFormat("d MMM");
			SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
			
			TextView timeview = (TextView) view.findViewById(R.id.text_time);
			timeview.setTypeface(dapp.getTypefaceRobothin());
			timeview.setText(date_format.format(cal.getTime()) +" "+ time_format.format(cal.getTime()));
			
			TextView up_thumb = (TextView) view.findViewById(R.id.num_upthumb);
			up_thumb.setTypeface(dapp.getTypefaceRobothin());
			up_thumb.setText(Integer.toString(crime.getUpThumbs()));
		
			TextView down_thumb = (TextView) view.findViewById(R.id.num_downthumb);
			down_thumb.setTypeface(dapp.getTypefaceRobothin());
			down_thumb.setText(Integer.toString(crime.getDownThumb()));
			
			// close button
			ImageView close = (ImageView) view.findViewById(R.id.mrd_closebtn);
			close.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainReportDetailDialogFragment.this.getDialog().dismiss();
				}
			});
			
			// vote button 
			Button vote = (Button) view.findViewById(R.id.mrd_btn_vote);
			vote.setTypeface(dapp.getTypefaceRobothin());
			vote.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
		             if(isAlreadyEvalCrime(Integer.toString(crime.getCrimeID()))){
	                     Toast.makeText(getActivity(), "You already evaluated this report", Toast.LENGTH_SHORT).show();
	             }else if(isMyCrimeReport(Integer.toString(crime.getCrimeID()))){
	                     Toast.makeText(getActivity(), "You cannot evaluate own reports", Toast.LENGTH_SHORT).show();
	             }else{
	            	 EvaluateDialogFragment dialog = new EvaluateDialogFragment
	            			 (Integer.toString(crime.getCrimeID()), 0);
	            	 dialog.show(getActivity().getSupportFragmentManager(), "sociam");
	             }
					
				}
			});
			
			
			return view;
		}

		@Override
		public void onDestroyView() {
		  if (getDialog() != null && getRetainInstance())
		    getDialog().setOnDismissListener(null);
		  super.onDestroyView();
		}
		
		public boolean isMyCrimeReport(String crime_id){
			Log.e("sociam",sp.getString("crime_id", ""));
			String[] crimes = sp.getString("crime_id", "").split(",");
			for(String str : crimes){
				if(str.equals(crime_id)) {
					return true;
				}
			}
			return false;
		}
		
		public boolean isAlreadyEvalCrime(String crime_id){
			String[] crimes_eval = sp.getString("eval_crime", "").split(",");
			for(String str : crimes_eval){
				if(str.equals(crime_id)){
					return true;
				}
			}
			return false;
		}
		
	}

	
	
	
	
	public boolean isMyCrimeReport(String crime_id){
		Log.e("sociam",sp.getString("crime_id", ""));
		String[] crimes = sp.getString("crime_id", "").split(",");
		for(String str : crimes){
			if(str.equals(crime_id)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAlreadyEvalCrime(String crime_id){
		String[] crimes_eval = sp.getString("eval_crime", "").split(",");
		for(String str : crimes_eval){
			if(str.equals(crime_id)){
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/*
	 * helper class to show evaluate dialog
	 * plan deprecated 
	 */
	public class EvaluateDialog extends DialogFragment{
		private String crime_id;
		public EvaluateDialog(String id) {
			this.crime_id=id;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			setRetainInstance(true);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Evaluate the Incident");
			builder.setNegativeButton("Up Vote", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// connect server to evaluate and closed and reload
					
						EvaluateAsyncTask evaluate = new EvaluateAsyncTask(getActivity());
						evaluate.execute(crime_id,"1","0","crime");
						((MainActivity) getActivity()).reloadData();
						
					
					EvaluateDialog.this.getDialog().dismiss();
				}
			});
			
			builder.setNeutralButton("Down Vote", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
					EvaluateAsyncTask evaluate = new EvaluateAsyncTask(getActivity());
					evaluate.execute(crime_id,"0","1","crime");
					((MainActivity) getActivity()).reloadData();

					
					
					EvaluateDialog.this.getDialog().dismiss();

				}
			});
			
			builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// do nothing
					EvaluateDialog.this.getDialog().dismiss();
					
				}
			});
						
			return builder.create();
		}
		
		@Override
		public void onDestroyView() {
		  if (getDialog() != null && getRetainInstance())
		    getDialog().setOnDismissListener(null);
		  super.onDestroyView();
		}

		
	}
	
	/*
	 * helper class to show the perticepent information
	 */
	public class ConsentFormDialog extends DialogFragment{		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.participent_information, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setTitle("PARTICIPANT INFORMATION");
			builder.setView(view);
			builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ConsentFormDialog.this.getDialog().dismiss();
					
				}
			});
			
			return builder.create();
		}
	}
	
	/*
	 * helper class to show consent form
	 */
	public class ConsentFormDialog2 extends DialogFragment{		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.consent_form, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setTitle("CONSENT FORM");
			builder.setView(view);
			builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ConsentFormDialog2.this.getDialog().dismiss();
					
				}
			});
			
			return builder.create();
		}
	}

	
	public class MyPreferenceShow extends DialogFragment{		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.my_preference, null);
			
			TextView id = (TextView) view.findViewById(R.id.mypre_textView2);
			id.setText(sp.getString("uuid", "something problem with uuid"));
			
			TextView crime_id = (TextView) view.findViewById(R.id.mypre_textView4);
			crime_id.setText(sp.getString("crime_id", "something wrong with crime_id"));

			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setTitle("My Preference");
			builder.setView(view);
			builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MyPreferenceShow.this.getDialog().dismiss();
					
				}
			});
			
			return builder.create();
		}
	}

	/*
	 * helper class for auto update every 3 min 
	 */
	private class myTimerTask extends TimerTask{
		private Handler handler;
		private Context context;
		
		public myTimerTask(Context con) {
			handler = new Handler();
			this.context = con;
		}
		
		@Override
		public void run() {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					((MainActivity) context).reloadData();
				}
			});
		}
		
		
	}
	
	
	
	
	/*
	 * helper class to contral Navigation Drawer
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Log.v("sociam","clicked"+ position);
			
			Crime crime = sortedCrimesDis.get(position);
			
			Marker m = mapMarkers.get(crime.getCrimeID());
			m.showInfoWindow();
			mDrawerLayout.closeDrawer(mListView);
			// close drawer and focus on the maker
			
			LatLng latlng = new LatLng(crime.getLat(), crime.getLon());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 15);
			
			
			
			mMap.animateCamera(cameraUpdate);
		}
		
		
		
	}

	
	public Location getLocation(){
		if(currentBestlocation!=null){
//			Log.w("sociam","location is new");
		}else{
//			Log.w("sociam","location is old");
			
			currentBestlocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			oldlocation=true;
		}
		return this.currentBestlocation;
	}
	
	
	public void getDistanceFromHere(Location userloc, ArrayList<Crime> crimelist){
		
		// add distance to each crime obj
		
		for(Crime crime:crimelist){
		
			Location crimeloc = new Location("crimepoint");
			crimeloc.setLatitude(crime.getLat());
			crimeloc.setLongitude(crime.getLon());
			
			float distance1  = userloc.distanceTo(crimeloc);
			double distance = distance1;
			double disInMile = distance *  0.00062137119;
			
			crime.setDistance(disInMile);
		
		}

	}
	
	/*
	 * Location helper methods
	 */
	
	  private boolean isBetterLocationisBetterLocation(Location mylocation, Location currentBestLocation) {
		    if (currentBestLocation == null) {
		        // A new location is always better than no location
		        return true;
		    }

		    // Check whether the new location fix is newer or older
		    long timeDelta = mylocation.getTime() - currentBestLocation.getTime();
		    boolean isSignificantlyNewer = timeDelta > ONE_MINUTES;
		    boolean isSignificantlyOlder = timeDelta < -ONE_MINUTES;
		    boolean isNewer = timeDelta > 0;

		    // If it's been more than two minutes since the current location, use the new location
		    // because the user has likely moved
		    if (isSignificantlyNewer) {
		        return true;
		    // If the new location is more than two minutes older, it must be worse
		    } else if (isSignificantlyOlder) {
		        return false;
		    }

		    // Check whether the new location fix is more or less accurate
		    int accuracyDelta = (int) (mylocation.getAccuracy() - currentBestLocation.getAccuracy());
		    boolean isLessAccurate = accuracyDelta > 0;
		    boolean isMoreAccurate = accuracyDelta < 0;
		    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		    // Check if the old and new location are from the same provider
		    boolean isFromSameProvider = isSameProvider(mylocation.getProvider(),
		            currentBestLocation.getProvider());

		    // Determine location quality using a combination of timeliness and accuracy
		    if (isMoreAccurate) {
		        return true;
		    } else if (isNewer && !isLessAccurate) {
		        return true;
		    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
		        return true;
		    }
		    return false;
		}

		/** Checks whether two providers are the same */
		private boolean isSameProvider(String provider1, String provider2) {
		    if (provider1 == null) {
		      return provider2 == null;
		    }
		    return provider1.equals(provider2);
		}
		
		private Location getLastLocationBest(Location loc1, Location loc2){
			if(isBetterLocationisBetterLocation(loc1, loc2)){
				return loc1;
			}else{
				return loc2;
			}
		}
		
		
		public interface GetDateFromCallback{
			public void onTaskDone(String response);
		}


		
	
}