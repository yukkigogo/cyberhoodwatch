package com.sociam.android;


import com.sociam.android.model.Tag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * This class (extending DialogFregment) is used to show Tag for UserSetting
 * <p>
 * This class is called by UserPreferenceDialog class
 * 
 * @author yukki
 *@version 1
 */
public class UserPreferenceTagDialog extends DialogFragment{

	DataApplication dapp;
	SharedPreferences sp;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.preference_taglist, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		dapp = (DataApplication) getActivity().getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());		 
		setInterface(view);
		builder.setTitle("User Tags");
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UserPreferenceTagDialog.this.getDialog().dismiss();
			}
		});
		
		builder.setView(view);
		return builder.create();
	}

	private void setInterface(View view) {
		
		TextView title_gene = (TextView) view.findViewById(R.id.pref_tag_title1);
		title_gene.setTypeface(dapp.getTypefaceRobothin());
		TextView title_soton = (TextView) view.findViewById(R.id.pref_tag_title2);
		title_soton.setTypeface(dapp.getTypefaceRobothin());
		
		LinearLayout layout_gene = (LinearLayout) view.findViewById(R.id.pref_tag_general_layout);		
		LinearLayout layout_soton = (LinearLayout) view.findViewById(R.id.pref_tag_southampton_layout);
		for(Tag tag : dapp.getInitTags()){			
			if(tag.getUserSetting()){
				if(tag.getCategory().equals("general")){
					layout_gene.addView(getTag(tag.getName()));
				}else{
					layout_soton.addView(getTag(tag.getName()));
				}
			}
		}
	}
	
	private TextView getTag(String tag){
		TextView textView = new TextView(getActivity());
		textView.setTypeface(dapp.getTypefaceRobothin());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		textView.setText(tag);
		
		return textView;
	}
}
