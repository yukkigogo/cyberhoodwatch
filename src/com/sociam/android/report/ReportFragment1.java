package com.sociam.android.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
import android.app.Fragment;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ReportFragment1 extends Fragment implements LocationListener{

	static final int REQUEST_CAPTURE_IMAGE = 100;
	
	ImageButton button1;
	ImageButton button2;
	ImageView imageView1;
	Bitmap capturedImage;
	final String SAVE_DIR = "/CrimeTips/";
	private String fileName;
	Location myCurrentLocation;
	
	//for obtaining location
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	protected Double latitude,longitude; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.report_fragment1, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		findViews();
		setListeners();		
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		locationManager.removeUpdates(this);
	}

	
	protected void findViews(){
		button1 = (ImageButton) getActivity().findViewById(R.id.button1);
		imageView1 = (ImageView) getActivity().findViewById(R.id.imageView1);
	}
	
	protected void setListeners(){		
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				getLocation();
				// take picture
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
			}
		});
		
		
		
	}

	public void getLocation(){
		// obtain location

		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Log.v("odebaki", "GPS "+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
		Log.v("odebaki", "NETWORK "+locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

		
		List<String> allProvider =  locationManager.getAllProviders();
        for(int i = 0 ; i < allProvider.size() ; i++){
            locationManager.requestLocationUpdates(allProvider.get(i), 0, 0,this);
        }
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(REQUEST_CAPTURE_IMAGE == requestCode && resultCode == Activity.RESULT_OK ){
			capturedImage = (Bitmap) data.getExtras().get("data");
			imageView1.setImageBitmap(capturedImage);
			
			try {
				saveBitmap(capturedImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("odebaki","fail saving to the folder");
				e.printStackTrace();
			}
			
			// create button
			button2 = new ImageButton(getActivity());
			button2.setImageResource(R.drawable.uploadicon);
			// add button to layout
			FrameLayout layout =(FrameLayout) getActivity().findViewById(R.id.framelayout);
			layout.addView(button2, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT
					,Gravity.BOTTOM | Gravity.CENTER));
			
			setListeners2();	
		}
	}

	
	
	
	protected void setListeners2(){		
		button2.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				File file = null;

				file = Environment.getExternalStorageDirectory();
				Log.v("datas", file.getPath());    
				
	            // get current location
				if(latitude==null || longitude==null){
				Log.v("odebaki", "NULL DESHO!");	
				
				
				}else{
					Log.v("odebaki", "lat "+latitude.toString());
					Log.v("odebaki", "Lon"+longitude.toString());
				
					UploadAsyncTask upload = (UploadAsyncTask) new UploadAsyncTask(
						getActivity()).execute(Environment.getExternalStorageDirectory().getPath()+SAVE_DIR+fileName
								,latitude.toString(),longitude.toString());
				
				}
				
				
	            
				
			}
		});		
		
	}

	
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

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		
		Log.v("odebaki", "Lat "+latitude + "   Lon "+longitude);
		
	}
	

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	
	
}

