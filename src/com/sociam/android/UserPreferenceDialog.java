package com.sociam.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserPreferenceDialog extends DialogFragment {

		DataApplication dapp;
		SharedPreferences sp;
		
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.preference_user_main, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			dapp = (DataApplication) getActivity().getApplication();
			sp = PreferenceManager.getDefaultSharedPreferences(getActivity());		 
			setInterface(view);

			builder.setView(view);
			
			return builder.create();
	}
		
	private void setInterface(View view){
		
		TextView title = (TextView) view.findViewById(R.id.prof_title);
		title.setTypeface(dapp.getTypefaceRoboReg());
		
		TextView user_title = (TextView) view.findViewById(R.id.prof_username_title);
		user_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView user = (TextView) view.findViewById(R.id.prof_username);
		user.setTypeface(dapp.getTypefaceRobothin());
		
		TextView hero_title = (TextView) view.findViewById(R.id.prof_herostatus_txt);
		hero_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView hero = (TextView) view.findViewById(R.id.prof_hero_status);
		hero.setTypeface(dapp.getTypefaceRobothin());
		
		TextView point = (TextView) view.findViewById(R.id.point);
		point.setTypeface(dapp.getTypefaceRoboReg());
		
		TextView point_title = (TextView) view.findViewById(R.id.point_txt);
		point_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView email_title = (TextView) view.findViewById(R.id.prof_email_title);
		email_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView email = (TextView) view.findViewById(R.id.prof_email);
		email.setTypeface(dapp.getTypefaceRobothin());
		
	}

	//unused class
	private void setIcon(ImageView icon) {
		
		switch (sp.getInt("icon", 1)) {
		case 0:
			icon.setImageResource(R.drawable.icon1);			
			break;
		case 1:			
			icon.setImageResource(R.drawable.icon2);			
			break;
		case 2:
			icon.setImageResource(R.drawable.icon3);			
			break;
		case 3:
			icon.setImageResource(R.drawable.icon4);			
			break;	
		case 4:
			icon.setImageResource(R.drawable.icon5);			
			break;	
		case 5:
			icon.setImageResource(R.drawable.icon6);			
			break;
		default:
			break;
		}
		
	}
	
}
