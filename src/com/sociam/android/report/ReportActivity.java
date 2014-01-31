package com.sociam.android.report;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.model.Crime;
/**
 * This class is the main activity for reporting an incident. 
 * <p>
 * Report page uses slide pages which is implemented by pager adapter. 
 * MyFragmentStatePargerAdapter controls the sliding pages. 
 * <p>
 * When the class called, a Crime object was created in order to store user input. 
 * The class also starts location listner in order to get the user's location.
 * 
 * @see android.support.v4.app.FragmentStatePagerAdapter
 * @author yukki
 *
 */

/*
 * when you add another fragment
 * 0. set footer_xml
 * 
 * 1. this java
 * add variable, this.setFooter() , this.initbutton();
 * 
 * 2.MyFragmentStatePagerAdapter
 * getItem, pagecount
 * 
 */
@SuppressLint("NewApi")
public class ReportActivity extends FragmentActivity implements LocationListener{
	
	// PAGE_COUNT - 1
	/**
	 * total number of sliding page minus 1. 
	 */
	public static final int SUMMARY_FRAG_NUM = 9; 
	
	// store crime data
	private Crime crime;
	//private Personal suspects,victims;
	private MyFragmentStatePagerAdapter myAdapter;
	ViewPager pager;
	Button[] btns = new Button[10];
	
	SharedPreferences sp;
	Time now;
	
	// ToggleButton for Category2 and cat
	ToggleButton frag3btn2, frag3btn3, frag3btn4, frag3btn5;
	
	
	//for obtaining location
	protected LocationManager locationManager;
	private Location currentBestLocation;
	protected Context context;
	protected Double latitude,longitude; 	
	/** one min use for location setting */
	private static final int ONE_MINUTES = 1000 * 60 * 1;
	
	// store the state
	private int loc=88;
	private int dnt=88;
	private int sev=88;
	
	//ArrayAdapter<String> adapter; 
	SummaryCustomAdapter adapter; 
	DataApplication dapp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.report_main);
		
		crime = new Crime();
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		dapp = (DataApplication) this.getApplication();

		initSet();
		getLocation();
		setUpView();
		setFooter();
	}
	
	
	private void initSet() {
		// setup time
		now = new Time();
		now.setToNow();
		crime.setDate(now);

		//adapter = new ArrayAdapter<String>(this, R.layout.list_row, R.id.list1);
		adapter = new SummaryCustomAdapter(this,0);
	}


	public void onStop() {
		super.onStop();
		locationManager.removeUpdates(this);
	}


	
	// implement pageChangeLitener
	private void setUpView(){
		pager = (ViewPager) findViewById(R.id.pager);
		myAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(myAdapter);
		pager.setCurrentItem(0);
		initButton();
	}

	private void setFooter() {
		pager.setOnPageChangeListener(new OnPageChangeListener() {			
			@Override
			public void onPageSelected(int position) {
				switch(position){
				case 0: setBtnInFooter(0);
				break;
				case 1: setBtnInFooter(1);
				break;
		
				case 2: setBtnInFooter(2);
						break;
				case 3:	setBtnInFooter(3);
						break;	
				case 4:	setBtnInFooter(4);
						initFrag3Button();
						setTexts();
						if(crime.getCat2Def()){
							allFrag3Off();
							crime.setCat2Def(false);
							crime.setisCategoryText(false);
							crime.setCategoryText("");
						}						
						break;						
				case 5:	setBtnInFooter(5);
						break;
				case 6:	setBtnInFooter(6);
						break;		
				case 7:	setBtnInFooter(7);
						break;		
				case 8:	setBtnInFooter(8);
						break;		
				case 9:	setBtnInFooter(9);
					break;		
					
				default : 
					setBtnInFooter(6);
					break;
					

						
				}
				
			}
			


			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}

	
	


	private void setBtnInFooter(int i){
		for(int j=0;j<btns.length;j++){
			if(i==j){
				String num = Integer.toString(i+1);
				setBtn(btns[j], num, 60, 60, true);
				
			}else{
				setBtn(btns[j], "", 20, 20, false);
				
			}
				
		}
		
	}
	
	@SuppressLint("NewApi")
	private void  setBtn(Button btn, String text, int h, int w,boolean select){
		Resources res = getResources();
		Drawable main = res.getDrawable(R.drawable.rounded_cell);
		Drawable mini = res.getDrawable(R.drawable.rounded_mini);
		if(select) btn.setBackgroundDrawable(main);
		else btn.setBackgroundDrawable(mini);
		btn.setText(text);
		btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		btn.setHeight(h);
		btn.setWidth(w);
	}
	
	private void initButton() {
		// for footer
		btns[0] = (Button) findViewById(R.id.btn0);
		btns[1] = (Button) findViewById(R.id.btn1);
		btns[2] = (Button) findViewById(R.id.btn2);
		btns[3] = (Button) findViewById(R.id.btn3);
		btns[4] = (Button) findViewById(R.id.btn4);
		btns[5] = (Button) findViewById(R.id.btn5);
		btns[6] = (Button) findViewById(R.id.btn6);
		btns[7] = (Button) findViewById(R.id.btn7);		
		btns[8] = (Button) findViewById(R.id.btn8);		
		btns[9] = (Button) findViewById(R.id.btn9);		

		setBtnInFooter(0);
	}
	
	private void initFrag3Button(){
		// for category2
		frag3btn2 = (ToggleButton) findViewById(R.id.frag3RightTopBtn);
		frag3btn3 = (ToggleButton) findViewById(R.id.frag3RightBtmBtn);
		frag3btn4 = (ToggleButton) findViewById(R.id.frag3LeftBtmBtn);
		frag3btn5 = (ToggleButton) findViewById(R.id.frag3LeftTopBtn);	
	}
	
	/**
	 * Return crime object
	 * @return crime obj
	 */
	public Crime getCrime(){
		return this.crime;
	}
	
//	public MyFragmentStatePagerAdapter getMyfm(){
//		return this.myAdapter;
//	}
	
	/**
	 * Return user location - latitude
	 * @return latitude 
	 */
	public double getLat(){
		return this.latitude;
	}
	
	/**
	 * Return user location - longitude
	 * @return longitude
	 */
	public double getLng(){
		return this.longitude;
	}
	
	/** 
	 * Used by ReportLocation.class only
	 * Return number of button in ReportLocation 
	 * @return num of button
	 * @see com.sociam.android.report.ReportLocation
	 */
	public int getLoc(){ return this.loc; }
	
	/**
	 * Used by  ReportLocation.class only
	 * @param i number of input
	 * @see com.sociam.android.report.ReportLocation
	 */
	public void setLoc(int i){ this.loc=i; }
	
	/**
	 * Return this application's SharedPreference object
	 * @return SharedPreference
	 */
	public SharedPreferences getSP(){
		return this.sp;
	}
	
	/**
	 * Return Time object which is already setting current time and date
	 * @return current time and date
	 */
	public Time getNow(){
		return this.now;
	}
	/**
	 * Return MyFragmenetStateAdapter 
	 * <p>
	 * MyFragmenentStateAdapter controls sliding (fragment) pages.
	 * @see android.support.v4.app.FragmentStatePagerAdapter
	 * @return custom FragmentStatePagerAdapter
	 */
	public MyFragmentStatePagerAdapter getMyfragmentStatePagerAdapter(){
		return this.myAdapter;
	}
	
	
	/**
	 * Used by ReportDateTime.java only
	 * Return  num of toggle button
	 * @return num of toggle button
	 */
	public int getDnT(){ return this.dnt; }
	/**
	 * Used by ReportDateTime.java only
	 * Set num of toggle button
	 * @param i num of toggle button 
	 */	
	public void setDnT(int i){ this.dnt=i; }	
	
	/**
	 * Used by ReportSeverity.java only
	 * Return num of toggle button
	 * @return num of toggle button
	 */
	public int getSev(){ return this.sev; }
	/**
	 * Used by ReportSeverity.java only
	 * Set num of toggle button
	 * @param i num of toggle button. 
	 */
	public void setSev(int i){ this.sev=i; }
	
	/**
	 * Return custom arrayadapter which is used for SummaryListFragments.java
	 *
	 * @return ArrayAdapter<String> crime context
	 */
	public ArrayAdapter<String> getArrayAdapter(){
		return this.adapter;
	}

	// the text of button set dynamically
	private void setTexts(){
		  if(crime.getCategoryCode()==1){
			  frag3btn2.setText("Damage");
			  frag3btn2.setTextOn("Damage");
			  frag3btn2.setTextOff("Damage");
			  frag3btn3.setText("Attack");
			  frag3btn3.setTextOn("Attack");
			  frag3btn3.setTextOff("Attack");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Rape");
			  frag3btn5.setTextOn("Rape");
			  frag3btn5.setTextOff("Rape");
		
			  
			}else if(crime.getCategoryCode()==2){
			  frag3btn2.setText("Robbery");
			  frag3btn2.setTextOn("Robbery");
			  frag3btn2.setTextOff("Robbery");
			  frag3btn3.setText("Bike");
			  frag3btn3.setTextOn("Bike");
			  frag3btn3.setTextOff("Bike");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Shop");
			  frag3btn5.setTextOn("Shop");
			  frag3btn5.setTextOff("Shop");
			  
		  }else if(crime.getCategoryCode()==3){
			  frag3btn2.setText("Noise");
			  frag3btn2.setTextOn("Noise");
			  frag3btn2.setTextOff("Noise");
			  frag3btn3.setText("Street Drinking");
			  frag3btn3.setTextOn("Street Drinking");
			  frag3btn3.setTextOff("Street Drinking");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Drugs");
			  frag3btn5.setTextOn("Drugs");
			  frag3btn5.setTextOff("Drugs");

		  }else{
			  frag3btn2.setText("");
			  frag3btn2.setTextOn("");
			  frag3btn2.setTextOff("");
			  frag3btn3.setText("");
			  frag3btn3.setTextOn("");
			  frag3btn3.setTextOff("");
			  frag3btn4.setText("");
			  frag3btn4.setTextOn("");
			  frag3btn4.setTextOff("");
			  frag3btn5.setText("");
			  frag3btn5.setTextOn("");
			  frag3btn5.setTextOff("");

		  }
	}
	
	// category page2 button off
	private void allFrag3Off(){
		frag3btn2.setChecked(false);
		frag3btn3.setChecked(false);
		frag3btn4.setChecked(false);
		frag3btn5.setChecked(false);
	}
	
	/**
	 * Start Location Manager 
	 */
	public void getLocation(){		
		// obtain location
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Log.v("sociam", "GPS "+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
		Log.v("sociam", "NETWORK "+locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

		
		List<String> allProvider =  locationManager.getAllProviders();
        for(int i = 0 ; i < allProvider.size() ; i++){
            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,(LocationListener) this);
        }
		
       
        
        
        
	}
	
	

	// for location manager
	/**
	 * get best location
	 */
	public void onLocationChanged(Location location) {
		if(currentBestLocation!=null){
			if(isBetterLocationisBetterLocation(location, currentBestLocation))
				currentBestLocation = location;
			
		}else{
			currentBestLocation = location;
		}
			
			latitude = currentBestLocation.getLatitude();
			longitude = currentBestLocation.getLongitude();
			
			if(!crime.getLocationLatLng()){
				crime.setLocationLatLon(true);
				crime.setLat(latitude);
				crime.setLon(longitude);
			}
			//Log.v("sociam", "Lat "+latitude + "   Lon "+longitude);
	}
	
	public void onProviderDisabled(String provider) {}
	public void onProviderEnabled(String provider) {}
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	
	/**
	 * Set a list of summary page
	 * <p>
	 * The properties of the crime obj is converted to a String
	 * then store arrayadapter
	 */
	public void setAdapter(){
		
		ArrayList<String> details = new ArrayList<String>();
		
		// who is victim
		if(crime.getHappenwho()!="NULL"){ // user didn't skip the page
			if (crime.getHappenwho().equals("tome")) {
				details.add("Something Happen to me");
			}else if(crime.getHappenwho().equals("saw")){
				details.add("I Saw Somthing");
			}else if(crime.getHappenwho().equals("help")){
				details.add("I need help");
			}
		}
		// picture
		details.add("Picture : "+ (crime.getFilepath() !=null ? "Yes" : "No"));
		
		// category
		details.add("Category : "+crime.getCategory());
		if(crime.getisCategoryText()) 
			details.add(crime.getCategoryText());			
		
		
		// Location
		if(crime.getLocationLatLng()){
			details.add("Location : Here");
		}else if(crime.getIsAddress()){
			details.add("Location : "+crime.getAddress());
		}

		
		// date time?
		if(crime.getIsNow() == true)
			details.add("Time and Date : " +  "Now"); 
		else { 
			Time t = crime.getDate();
			String str =t.format("%d-%m-%Y %H:%M");
			details.add("Time and Date : "+ str );
		}
		
		
		if(crime.getIsDateText())
			details.add(crime.getDateText());
		
		// severity
		String severity="Not Serious";
		switch (crime.getSeverity()){
		case 88 :
			break;
		case 1 :
			break;
		case 2:
			severity = "Serious";
			break;
		case 3 :
			severity = "Very Serious";
			break;
		case 4:
			severity = "Extremely Serious";
			break;
			
		}		
		details.add("How Serious? : " + severity);
		
		
		// anonymous report?
		if(crime.getIdCode()){
			details.add("Anonymous Report");
		}else{
			String name = sp.getString("username", "Anonymous");
			details.add("Report by "+name);
		}
		adapter.clear();
		adapter.addAll(details);
		
		
	}
	
	/**
	 * Return the Application object
	 * @return Application object
	 */
	public DataApplication getDataApplication(){
		return this.dapp;
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

	
		private class SummaryCustomAdapter extends ArrayAdapter<String>{

			private LayoutInflater layoutInflater;
			public SummaryCustomAdapter(Context context, int resource) {
				super(context, resource);
				layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				String str = getItem(position);
				
				if(convertView==null) convertView = layoutInflater.inflate(R.layout.list_row, null);
				
				TextView txt = (TextView) convertView.findViewById(R.id.list1);
				txt.setTypeface(dapp.getTypefaceRobothin());
				txt.setText(str);
				return convertView;
			}
			
		}
		
}
