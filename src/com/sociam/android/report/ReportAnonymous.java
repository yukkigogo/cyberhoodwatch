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
import android.content.SharedPreferences;
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
public class ReportAnonymous extends Fragment {

	
	ViewPager pager;

	Button btnM,btnS;
	ToggleButton btn2,btn1;
	View view;
	Crime currentCrime;
	SharedPreferences sp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		view = inflater.inflate(R.layout.report_anonymous, container, false);
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		sp = ((ReportActivity) getActivity()).getSP();
		setBtns();
		
		//set up background
		if(currentCrime.getPicON()==1){
			  LinearLayout layout = (LinearLayout) 
					  view.findViewById(R.id.layout_rep_anonymous);		  
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}		
		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
		((ReportActivity) getActivity()).setAdapter();

	}

	@Override
	public void onStop() {
		super.onStop();
		//locationManager.removeUpdates(this);
	}

	
	protected void setBtns(){
		TextView title = (TextView) view.findViewById(R.id.rep_anony_title);
		title.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  btnM = (Button) view.findViewById(R.id.rep_anony_midBtn);
		  btnM.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  btnS = (Button) view.findViewById(R.id.rep_anony_summary);
		  btnS.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		  
		  setListenersInEvi(btnM, 0);
		  setListenersInEvi(btnS, 99);
		
		btn1 = (ToggleButton) view.findViewById(R.id.rep_anony_right);
		btn1.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		btn1.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							btn2.setChecked(false);
							currentCrime.setidcode(true);
							((ReportActivity) getActivity()).setAdapter();

							pager.setCurrentItem(pager.getCurrentItem()+1);

						}else{
							currentCrime.setidcode(true);
						}
					}
				});
		btn2 = (ToggleButton) getActivity().findViewById(R.id.rep_anony_left);
		btn2.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		btn2.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					setUsernameReport();
					((ReportActivity) getActivity()).setAdapter();

					pager.setCurrentItem(pager.getCurrentItem()+1);

				}else{
					currentCrime.setidcode(true);
				}
				
			}
		});
		
	}
	
	private void setUsernameReport(){
		btn1.setChecked(false);
		//check user 
		String username = sp.getString("username", "NULL");
		if(!username.equals("NULL")){
			btn2.setChecked(true);
			currentCrime.setidcode(false);
			currentCrime.setUserID(username);
		}else{
			
			AlertDialog.Builder alb = new AlertDialog.Builder(getActivity());
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
				
					Toast.makeText(getActivity(), "Report as Anonymous", 
							Toast.LENGTH_LONG).show();
					btn1.setChecked(true);
					btn2.setChecked(false);
				}
			});
			
			AlertDialog alertDialog = alb.create();
			alertDialog.show();
			
			
			
			
		}
		
		
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
	
	
	
}

