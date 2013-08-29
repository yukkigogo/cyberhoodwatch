package com.sociam.android.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.sociam.android.Crime;
import com.sociam.android.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

// This class for first report page
public class ReportEvidence extends Fragment {

	static final int REQUEST_CAPTURE_IMAGE = 100;
	
	
	ViewPager pager;

	Button btn1,btn2,btnM,btnS;
	//ImageView imageView1;
	Bitmap capturedImage;
	final String SAVE_DIR = "/CrimeTips/";
	private String fileName;
	Location myCurrentLocation;
	
	
	Crime currentCrime;
	
//	//for obtaining location
//	protected LocationManager locationManager;
//	protected LocationListener locationListener;
//	protected Context context;
//	protected Double latitude,longitude; 
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.report_evidence, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		setBtns();
		
		//set up background
		if(currentCrime.getPicON()==1){
			  LinearLayout layout = (LinearLayout) 
					  getActivity().findViewById(R.id.layoutevidence);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}		
		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//locationManager.removeUpdates(this);
	}

	
	protected void setBtns(){
		  btnM = (Button) getActivity().findViewById(R.id.evidencemidBtn);
		  btnS = (Button) getActivity().findViewById(R.id.evidencegoSummary);
		  
		  setListenersInEvi(btnM, 0);
		  setListenersInEvi(btnS, 99);
		
		btn1 = (Button) getActivity().findViewById(R.id.evi_right);
		btn2 = (Button) getActivity().findViewById(R.id.evi_left);
		//imageView1 = (ImageView) getActivity().findViewById(R.id.imageView1);
		setListeners();		

		
	}
	
	protected void setListeners(){		
		btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {				
				
				// take picture get location 
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//getLocation();
				startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Call Police ?")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getActivity(), "Calling Police......"
								, Toast.LENGTH_LONG).show();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing
						}
				}).show();
			}
		});
		
	}

	private void setListenersInEvi(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:
					Log.e("sociam","push the button");
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(pager.getCurrentItem()+1);
					break;
				case 99:
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
			
				default:
					break;
				} 
			}
		});
	}
	
	
//	public void getLocation(){
//		
//		// obtain location
//		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//		Log.v("sociam", "GPS "+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
//		Log.v("sociam", "NETWORK "+locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
//
//		
//		List<String> allProvider =  locationManager.getAllProviders();
//        for(int i = 0 ; i < allProvider.size() ; i++){
//            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,this);
//        }
//		
//	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(REQUEST_CAPTURE_IMAGE == requestCode && resultCode == Activity.RESULT_OK ){
			
			capturedImage = (Bitmap) data.getExtras().get("data");
			//imageView1.setImageBitmap(capturedImage);
			
			try {
				saveBitmap(capturedImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("sociam","fail saving to the folder");
				e.printStackTrace();
			}
			
			// create button
			//button2 = new ImageButton(getActivity());
			//button2.setImageResource(R.drawable.uploadicon);
			// add button to layout
			//FrameLayout layout =(FrameLayout) getActivity().findViewById(R.id.framelayout);
//			layout.addView(button2, new FrameLayout.LayoutParams(
//					FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT
//					,Gravity.BOTTOM | Gravity.CENTER));
//			
			//setListeners2();	
		}
	}

	
	
	
//	protected void setListeners2(){		
//		button2.setOnClickListener(new OnClickListener(){
//			
//			@Override
//			public void onClick(View v) {
//				
//				File file = null;
//
//				file = Environment.getExternalStorageDirectory();
//				Log.v("datas", file.getPath());    
//				
//	            // get current location
//				if(latitude==null || longitude==null){
//
//				
//				}else{
//					Log.v("sociam", "lat "+latitude.toString());
//					Log.v("sociam", "Lon"+longitude.toString());				
//					UploadAsyncTask upload = (UploadAsyncTask) new UploadAsyncTask(
//						getActivity()).execute(Environment.getExternalStorageDirectory().getPath()+SAVE_DIR+fileName
//								,latitude.toString(),longitude.toString());
//				
//				}
//				
//				
//	            
//				
//			}
//		});		
//		
//	}

	
	public void saveBitmap(Bitmap saveImage) throws IOException{
		
	
		// prepare folder to save the image
		File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);   
		try{
	        if(!file.exists()){
	            file.mkdir();
	        }
	    }catch(SecurityException e){
	        e.printStackTrace();
	        throw e;    
	    }
	    
		// decide image name 
	    Date mDate = new Date();
	    SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    fileName = fileNameDate.format(mDate) + ".jpg";
	    String AttachName = file.getAbsolutePath() + "/" + fileName;
		Log.e("sociam", AttachName);
		currentCrime.setFilepath(AttachName);
		currentCrime.setPicOn(1);
		currentCrime.setBitmap(saveImage);
		
	    //convert Bitmap to jpg and store the folder
	    try {
	        FileOutputStream out = new FileOutputStream(AttachName);
	        saveImage.compress(CompressFormat.JPEG, 100, out);
	        out.flush();
	        out.close();
	    } catch(IOException e) {
	        e.printStackTrace();
	        throw e;
	    }
	    
	    // save index
	    ContentValues values = new ContentValues();
	    ContentResolver contentResolver = getActivity().getContentResolver();
	    values.put(Images.Media.MIME_TYPE, "image/jpeg");
	    values.put(Images.Media.TITLE, fileName); 
	    values.put("_data", AttachName);
	    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	    
	}
	
//	// for location manager
//	@Override
//	public void onLocationChanged(Location location) {
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//		
//			Log.v("sociam", "Lat "+latitude + "   Lon "+longitude);
//	}
//	
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub		
//	}
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub		
//	}
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub		
//	}

	
	
}

