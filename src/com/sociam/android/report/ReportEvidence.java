package com.sociam.android.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sociam.android.Crime;
import com.sociam.android.DataApplication;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

// This class for first report page
public class ReportEvidence extends Fragment {

	static final int REQUEST_CAPTURE_IMAGE = 100;
	
	
	ViewPager pager;

	Button btnM,btnS;
	ToggleButton btn1,btn2;
	Bitmap capturedImage;
	public final String SAVE_DIR = "/CrimeTips/";
	private String fileName;
	Location myCurrentLocation;
	
	DataApplication dapp;
	Crime currentCrime;
	View view;
	

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		dapp = ((ReportActivity) getActivity()).getDataApplication();

		view =inflater.inflate(R.layout.report_evidence, container, false);
		return view;
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
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}		
		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
		((ReportActivity) getActivity()).setAdapter();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//locationManager.removeUpdates(this);
	}

	
	protected void setBtns(){
		TextView title = (TextView) view.findViewById(R.id.textView1);
		title.setTypeface(dapp.getTypefaceRobothin());
		  btnM = (Button) getActivity().findViewById(R.id.evidencemidBtn);
		  btnM.setTypeface(dapp.getTypefaceRobothin());
		  btnS = (Button) getActivity().findViewById(R.id.evidencegoSummary);
		  btnS.setTypeface(dapp.getTypefaceRobothin());
		  
		  setListenersInEvi(btnM, 0);
		  setListenersInEvi(btnS, 99);
		
		btn1 = (ToggleButton) getActivity().findViewById(R.id.evi_right);
		btn1.setTypeface(dapp.getTypefaceRobothin());
		btn1.setOnCheckedChangeListener(new 
				CompoundButton.OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(
					CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
				}else{
					currentCrime.setPicOn(0);

				}
				
			}
		});
		
		btn2 = (ToggleButton) getActivity().findViewById(R.id.evi_left);
		btn2.setTypeface(dapp.getTypefaceRobothin());
		btn2.setOnCheckedChangeListener(new
				CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(
							CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							pager =(ViewPager) getActivity().findViewById(R.id.pager);
							pager.setCurrentItem(pager.getCurrentItem()+1);
							currentCrime.setPicOn(0);

						}else{
							currentCrime.setPicOn(0);
						}
							
						
					}
				});
//		setListeners();		

		
	}
	
//	protected void setListeners(){		
//		btn1.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {				
//				
//				// take picture get location 
//				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				startActivityForResult(intent,REQUEST_CAPTURE_IMAGE);
//				
//			}
//		});
//		
//		btn2.setOnClickListener(new OnClickListener() {			
//			@Override
//			public void onClick(View v) {
//				pager =(ViewPager) getActivity().findViewById(R.id.pager);
//				pager.setCurrentItem(pager.getCurrentItem()+1);
//			}
//		});
//		
//	}
	
	
	private void setListenersInEvi(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:
					Log.e("sociam","push the button");
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					
					pager.setCurrentItem(pager.getCurrentItem()-1);
					break;
				case 99:
					((ReportActivity) getActivity()).setAdapter();
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
			
				default:
					break;
				} 
			}
		});
	}
	
	
	
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
			
		}
		pager.setCurrentItem(pager.getCurrentItem()+1);
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
	    //String AttachName = fileName;

		Log.e("sociam", fileName);
		currentCrime.setFilepath(fileName);
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

	
	
}

