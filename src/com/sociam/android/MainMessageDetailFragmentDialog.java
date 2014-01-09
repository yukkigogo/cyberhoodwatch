package com.sociam.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class MainMessageDetailFragmentDialog extends DialogFragment {

	SharedPreferences sp;
	DataApplication dapp;
	
	public MainMessageDetailFragmentDialog() {
		dapp = (DataApplication) getActivity().getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());		 
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.main_msg_detailfdialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		setInterface(view);
		
		return builder.create();
	}

	private void setInterface(View view) {
		
		
		
	}
	
	
}
