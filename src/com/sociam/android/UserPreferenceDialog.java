package com.sociam.android;

import com.sociam.android.message.MessageSettingTagFragmentDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferenceDialog extends DialogFragment {

		DataApplication dapp;
		SharedPreferences sp;
		MessageSettingTagFragmentDialog mstfd;
		
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
		user.setText(sp.getString("username", ""));
		
		TextView hero_title = (TextView) view.findViewById(R.id.prof_herostatus_txt);
		hero_title.setTypeface(dapp.getTypefaceRobothin());
		
		Integer point_int = sp.getInt("hero_point", 0);
		
		TextView hero = (TextView) view.findViewById(R.id.prof_hero_status);
		hero.setTypeface(dapp.getTypefaceRobothin());
		/*
		 * hero status 
		 * 100+ cyberhood officer
		 * 50+  cyberhood guardian
		 * 30+  cyberhood watcher
		 * >  cyberhood resident 
		 */
		
		
		if(point_int>99){
			hero.setText("Cyberhood Officer");
		} else if(point_int<=99 && point_int>=50){
			hero.setText("CyberHood Gurdian");
		} else if(point_int<50 && point_int>=30){
			hero.setText("Cyberhood Watcher");
		}else{
			hero.setText("Cyberhood Resident");
		}
		
		TextView point = (TextView) view.findViewById(R.id.point);
		point.setTypeface(dapp.getTypefaceRoboBoldItalic());
		point.setText(Integer.toString(point_int));
		
		TextView point_title = (TextView) view.findViewById(R.id.point_txt);
		point_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView email_title = (TextView) view.findViewById(R.id.prof_email_title);
		email_title.setTypeface(dapp.getTypefaceRobothin());
		
		TextView email = (TextView) view.findViewById(R.id.prof_email);
		email.setTypeface(dapp.getTypefaceRobothin());
		
		if((sp.getString("email", "")).equals("")){
			email.setText("not registered yet");
		}else{
			email.setText(sp.getString("email", ""));
		}
		
		
		// Button interfaces
		
		Button tag = (Button) view.findViewById(R.id.prof_tag_button);	
		tag.setTypeface(dapp.getTypefaceRobothin());
		tag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				if((sp.getString("usertag", null)).equals("")){
					Toast.makeText(getActivity(), "you did not set up tags yet", Toast.LENGTH_SHORT).show();
				}else{
					UserPreferenceTagDialog dialog = new UserPreferenceTagDialog();
					dialog.show(getActivity().getSupportFragmentManager(), "sociam");
					
				}	
				
			}
		});
		
//		Button edit = (Button) view.findViewById(R.id.prof_edit_btn);
//		edit.setTypeface(dapp.getTypefaceRobothin());
//		edit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getActivity(), "connect web page for editting", Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		
		ImageView close = (ImageView) view.findViewById(R.id.pref_close_btn);
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserPreferenceDialog.this.getDialog().dismiss();
			}
		});
		
		
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
