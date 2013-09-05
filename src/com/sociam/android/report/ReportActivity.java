package com.sociam.android.report;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
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

import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;


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
	public static final int SUMMARY_FRAG_NUM = 6; 
	
	// store crime data
	private Crime crime;
	private Persons suspects,victims;
	private MyFragmentStatePagerAdapter myAdapter;
	ViewPager pager;
	Button[] btns = new Button[7];
	
	SharedPreferences sp;
	Time now;
	
	// ToggleButton for Category2 and cat
	ToggleButton frag3btn2,frag3btn3,frag3btn4,frag3btn5;
	
	
	//for obtaining location
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	protected Double latitude,longitude; 
	
	// store the state
	private int loc=88;
	private int dnt=88;
	private int sev=88;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.report_main);
		
		suspects = new Persons();
		victims = new Persons();
		crime = new Crime();
		crime.setSuspects(suspects);
		crime.setVictim(victims);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);;
		  Log.w("sociam"," coommmooonnn !!  "+ sp.getString("uuid", "something problem with uuid"));

		initSet();
		getLocation();
		setUpView();
		setFooter();
	}
	
	
	private void initSet() {
		now = new Time();
		now.setToNow();
		crime.setDate(now);
		
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
				case 1:	setBtnInFooter(1);
						break;	
				case 2:	setBtnInFooter(2);
						initFrag3Button();
						setTexts();
						if(crime.getCat2Def()){
							allFrag3Off();
							crime.setCat2Def(false);
							crime.setisCategoryText(false);
							crime.setCategoryText("");
						}						
						break;						
				case 3:	setBtnInFooter(3);
						break;
				case 4:	setBtnInFooter(4);
						break;		
				case 5:	setBtnInFooter(5);
						break;		
				case 6:	setBtnInFooter(6);
												
						break;		
//				case 7:	setBtnInFooter(2);
//						break;		
//				case 8:	setBtnInFooter(2);
//						break;		
//				case 9:	setBtnInFooter(3);
//						break;		
//				case 10:setBtnInFooter(3);
//						break;		
//				case 11:setBtnInFooter(3);
//						break;
//				case 12:setBtnInFooter(3);
//					break;		
//				case 13:setBtnInFooter(3);
//						break;		
//				case 14:setBtnInFooter(3);
//						break;		
//				case 15:setBtnInFooter(4);
//						break;		
//				case 16:setBtnInFooter(5);
//						break;		
//				case 17:setBtnInFooter(6);
//						break;		
//				case 18:setBtnInFooter(7);
//						break;		

						
				}
				
			}
			
			// get currentItem de page no zokusei wo set suru
			//http://stackoverflow.com/questions/8117523/how-can-i-
			//get-page-number-in-view-pager-for-android

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
		if(select) btn.setBackground(main);
		else btn.setBackground(mini);
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
//		btns[7] = (Button) findViewById(R.id.btn7);		
		setBtnInFooter(0);
		

	}
	
	private void initFrag3Button(){
		// for category2
		frag3btn2 = (ToggleButton) findViewById(R.id.frag3RightTopBtn);
		frag3btn3 = (ToggleButton) findViewById(R.id.frag3RightBtmBtn);
		frag3btn4 = (ToggleButton) findViewById(R.id.frag3LeftBtmBtn);
		frag3btn5 = (ToggleButton) findViewById(R.id.frag3LeftTopBtn);	
	}
	
	public Crime getCrime(){
		return this.crime;
	}
	
	public MyFragmentStatePagerAdapter getMyfm(){
		return this.myAdapter;
	}
	
	public double getLat(){
		return this.latitude;
	}
	public double getLng(){
		return this.longitude;
	}
	public SharedPreferences getSP(){
		return this.sp;
	}
	public Time getNow(){
		return this.now;
	}
	
	public MyFragmentStatePagerAdapter getMyfragmentStatePagerAdapter(){
		return this.myAdapter;
	}
	
	public int getLoc(){ return this.loc; }
	public void setLoc(int i){ this.loc=i; }
	public int getDnT(){ return this.dnt; }
	public void setDnT(int i){ this.dnt=i; }	
	public int getSev(){ return this.sev; }
	public void setSev(int i){ this.sev=i; }
	
	
	private void setTexts(){
		
		  // the text of button set dynamically
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
	
	private void allFrag3Off(){
		frag3btn2.setChecked(false);
		frag3btn3.setChecked(false);
		frag3btn4.setChecked(false);
		frag3btn5.setChecked(false);
	}
	
	
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
	public void onLocationChanged(Location location) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			if(!crime.getLocationLatLng()){
				crime.setLocationLatLon(true);
				crime.setLat(latitude);
				crime.setLon(longitude);
			}
			//Log.v("sociam", "Lat "+latitude + "   Lon "+longitude);
	}
	
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub		
	}

	
}
