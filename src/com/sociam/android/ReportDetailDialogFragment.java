package com.sociam.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.sociam.android.model.Crime;

// don't user this class now
@SuppressLint({ "ValidFragment", "CutPasteId" })
public class ReportDetailDialogFragment extends DialogFragment {

	Crime crime;
	SharedPreferences sp;
	DataApplication dapp;
	Context con;
	public ReportDetailDialogFragment(Crime crime, Context con) {
		this.crime = crime;
		this.con = con;
		sp = PreferenceManager.getDefaultSharedPreferences(con);		 
		dapp = (DataApplication) con.getApplicationContext();
	}
	
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		setRetainInstance(true);
//		AlertDialog.Builder builder = new AlertDialog.Builder(con);
//		View view = getActivity().getLayoutInflater().inflate(R.layout.main_report_detail, null);
//		
//		view = setDetails(crime, view);
//		
//		builder.setView(view);		
//		return builder.create();
//	}
//


	
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
			if(crime.getCategory().indexOf("ASB")>-1) category.setText(" Anti Social Behaviour");
			else category.setText(crime.getCategory());
		}else if(crime.getCategory().indexOf("-" ) > -1){
			String str[] = crime.getCategory().split("-");
			if(str[0].indexOf("ASB") >-1) category.setText(" Anti Social Behaviour" +" - " + str[1]);
			else category.setText(str[0]+" - "+str[1]);
		}
		layout.addView(category);

		
		// detail of category
		if(crime.getisCategoryText()) {
			TextView sub_cate = new TextView(getActivity());
			sub_cate.setTypeface(dapp.getTypefaceRobothin());
			sub_cate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			sub_cate.setLayoutParams(pane);
			
			sub_cate.setText("   "+crime.getCategoryText());
			layout.addView(sub_cate);
		}
		
		if(crime.getIsDateText()){
			TextView sub_time = new TextView(getActivity());
			sub_time.setTypeface(dapp.getTypefaceRobothin());
			sub_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			sub_time.setLayoutParams(pane);
			
			sub_time.setText("   "+crime.getDateText());
			layout.addView(sub_time);
			
		}
		
		
		// seriousness 
		 String seriousness=null;
		 switch (crime.getSeverity()){
		 case 1: 
			 seriousness = " Not Serious";
			 break;
		 case 2:
		 	seriousness =" Serious";
			 break;
		 case 3:
			 seriousness =" Very Serious";
			 break;
		 case 4:
			 seriousness =" Extremely Serious";				 
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
				ReportDetailDialogFragment.this.getDialog().dismiss();
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
            			 (Integer.toString(crime.getCrimeID()), 1);
            	 dialog.show(getActivity().getSupportFragmentManager(), "sociam");
             }
				
			}
		});
		
		
		return view;
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
