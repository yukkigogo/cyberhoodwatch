package com.sociam.android.report;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sociam.android.R;
import com.sociam.android.model.Crime;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


/**
 * This class show the location options. The user is able to select where the incident hapend.
 * The potential valuse are here, input address, unknown and the place can be choice from map. 
 * @author yukki
 *@version 1
 */
public class ReportLocation extends Fragment {
	
	
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	
	
	String age_range=null;
	AlertDialog alertDialog;
	int selectedItem=0;
	ArrayAdapter<String> adapter;
	private GoogleMap mMap;
	private Marker m;
	LatLng latlng;
	View viewformap, view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.report_location, container, false);
		setBtns(view);
		return view;
	}

	@SuppressLint("NewApi")
	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();


		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) getActivity().
					findViewById(R.id.layoutpeople2);		  
			  layout.setBackgroundDrawable(currentCrime.getBitmapdrawable());
		}		
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
		setLocation();

	}


	


	private void setBtns(View v) {
		TextView tx = (TextView) v.findViewById(R.id.textview_people2); 
		tx.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
		tx.setText("Location?");

	  btn1 = (Button) v.findViewById(R.id.people2_midBtn);
	  btn1.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnS = (Button) v.findViewById(R.id.people2_goSummary);
	  btnS.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btnD = (Button) v.findViewById(R.id.people2_description);
	  btnD.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people2_RightBtmBtn);
	  btn2.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn3 = (ToggleButton) v.findViewById(R.id.people2_LeftBtmBtn);
	  btn3.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn4 = (ToggleButton) v.findViewById(R.id.people2_LeftTopBtn);
	  btn4.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	  btn5 = (ToggleButton) v.findViewById(R.id.people2_RightTopBtn);
	  btn5.setTypeface(((ReportActivity) getActivity()).dapp.getTypefaceRobothin());
	
	
	  btn2.setTextOff("From Map");
	  btn2.setTextOn("From Map");
	  btn2.setText("From Map");
	  btn3.setTextOn("Input Address");
	  btn3.setTextOff("Input Address");
	  btn3.setText("Input Address");
	  btn4.setTextOn("Unknown");
	  btn4.setTextOff("Unknown");
	  btn4.setText("UnKnonw");
	  btn5.setTextOff("Here");
	  btn5.setTextOn("Here");
	  btn5.setText("Here");
	  
	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	}
	
	private void setLocation(){
		double lat = ((ReportActivity) getActivity()).getLat();
		double lng = ((ReportActivity) getActivity()).getLng();
		currentCrime.setLocationLatLon(true);
		currentCrime.setLat(lat);
		currentCrime.setLon(lng);
		Log.w("sociam","Lat and Lng : "+ lat + " " + lng);
		
	}
	
	
	private void setListeners(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:

					pager.setCurrentItem(pager.getCurrentItem()-1);
					break;
				case 99:
					((ReportActivity) getActivity()).setAdapter();

					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
				case 999:
					addText();
					break;
			
			
				default:
					break;
				} 
			}
		});
	}
	
	private void setToggleListeners(final ToggleButton btn,final int num){
		pager =(ViewPager) getActivity().findViewById(R.id.pager);
		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {

						// 2:RB 3:LB 4:LT 5:RT
						if(isChecked){
							if( ((ReportActivity) getActivity()).getLoc() != num){	
								((ReportActivity) getActivity()).setLoc(num);
		
							switch (num) {
							case 2:	
								// show map
								//setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								
							
								
								viewformap = View.inflate(getActivity(), R.layout.report_location_map_select, null);
							
								 mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map2))
						                  .getMap();	
								
								
								mMap.setMyLocationEnabled(true);
								
								latlng = new LatLng(((ReportActivity) getActivity()).getLat(), 
										((ReportActivity) getActivity()).getLng());
				                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 18);
				                mMap.animateCamera(cameraUpdate);
								
				                mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
				                	
									public void onMapLongClick(LatLng point) {
								        if(m!=null) m.remove();
										//String text = "latitude=" + point.latitude + ", longitude=" + point.longitude;
								        m = mMap.addMarker(new MarkerOptions()
								        .position(point));
								        
								        
									}
								});
				                
								
								final AlertDialog d = new AlertDialog.Builder(getActivity())
								.setIcon(android.R.drawable.ic_dialog_info)
								.setTitle("Click and hold the place you want to choose")
								.setView(viewformap)
								.setPositiveButton("OK", null)
								.setNegativeButton("Cancel", null)
								.create();
								
								
								d.setOnShowListener(new DialogInterface.OnShowListener() {
									
									@Override
									public void onShow(DialogInterface dialog) {
										
										Button bPos = d.getButton(AlertDialog.BUTTON_POSITIVE);
										bPos.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {

											if(m==null){
												Toast.makeText(getActivity(), "Please choose the location on the map Or cancel"
														, Toast.LENGTH_LONG).show();	
											}else{	
												
												currentCrime.setLat(m.getPosition().latitude);
												currentCrime.setLon(m.getPosition().longitude);
												
												Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map2);
												if(f != null)
														getFragmentManager().beginTransaction().remove(f).commit();
												
												pager.setCurrentItem(pager.getCurrentItem()+1);
												d.dismiss();

												
											}
											}
										});
										
										
										//negative botton
										Button bNeg = d.getButton(AlertDialog.BUTTON_NEGATIVE);
										bNeg.setOnClickListener(new View.OnClickListener() {
											
											@Override
											public void onClick(View v) {
												
												
												Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.map2);
												if(f != null)
														getFragmentManager().beginTransaction().remove(f).commit();
												
												btn2.setChecked(false);
												btn3.setChecked(false);
												btn4.setChecked(false);
												btn5.setChecked(false);
												
												d.dismiss();

													
											}
										});
										
									}
								});
								d.show();
								
								
								((ReportActivity) getActivity()).setAdapter();


								break;
							case 3:
								
								
								btn2.setChecked(false);
								btn4.setChecked(false);
								btn5.setChecked(false);
								// add address
								
								addText();
								((ReportActivity) getActivity()).setAdapter();
								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;
							
							case 4:
								// set up location null	
								currentCrime.setLocationLatLon(false);
								currentCrime.setisAddress(true);
								currentCrime.setAddress("Unknown");
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn5.setChecked(false);
								((ReportActivity) getActivity()).setAdapter();

								pager.setCurrentItem(pager.getCurrentItem()+1);
								
								break;
							case 5:
								// set up the location from getActivity

								setLocation();
								
								btn2.setChecked(false);
								btn3.setChecked(false);
								btn4.setChecked(false);
								((ReportActivity) getActivity()).setAdapter();

								pager.setCurrentItem(pager.getCurrentItem()+1);

								break;

							default:
								break;
							}
							}
							
						}else if(!isChecked){
							setLocation();
							
							
						}
						
					}


			});
	}
		

	

	
	
	private void addText(){		
		final EditText eText = new EditText(getActivity());
		
		if(currentCrime.getIsAddress()){
			String str = currentCrime.getAddress();
			eText.setText(str, TextView.BufferType.EDITABLE);
		}
		
		new AlertDialog.Builder(getActivity())
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("Input Address")
		.setView(eText)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(eText.getText().toString().length()>0){
					currentCrime.setisAddress(true);
					currentCrime.setAddress(eText.getText().toString());
				}else{
					currentCrime.setisAddress(false);
					currentCrime.setAddress("");
				}
				
				// out the input to toast at the moment
				Toast.makeText(getActivity(), eText.getText().toString()
						, Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				}
		}).show();
	}
	
	
	
}
