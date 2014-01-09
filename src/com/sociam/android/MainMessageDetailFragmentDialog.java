package com.sociam.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class MainMessageDetailFragmentDialog extends DialogFragment {

	SharedPreferences sp;
	DataApplication dapp;
	
	public MainMessageDetailFragmentDialog() {
		super();
		 setStyle(STYLE_NO_FRAME, R.style.AppTheme);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dapp = (DataApplication) getActivity().getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());		 

		View view = getActivity().getLayoutInflater().inflate(R.layout.main_msg_detailfdialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		setInterface(view);

		builder.setView(view);		
		
		
		Dialog dialog = builder.create();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return dialog;
	}

	private void setInterface(View view) {
		
		
		
	}
	
	
}
