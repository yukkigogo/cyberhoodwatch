package com.sociam.android;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserSetupFragmentDialog extends DialogFragment{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = getActivity().getLayoutInflater().inflate(R.layout.register_user, null);
		
		Typeface robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");
		
		TextView tx1 = (TextView) view.findViewById(R.id.reg_text1);
		tx1.setTypeface(robothin);
		TextView tx2 = (TextView) view.findViewById(R.id.reg_text2);
		tx2.setTypeface(robothin);
		TextView tx3 = (TextView) view.findViewById(R.id.reg_text3);
		tx3.setTypeface(robothin);
		TextView tx4 = (TextView) view.findViewById(R.id.reg_text4);
		tx4.setTypeface(robothin);
		
		
		
		EditText username = (EditText) view.findViewById(R.id.reg_username);
		username.setTypeface(robothin);
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				//KOKOKARA TMR
				//UserAvaiableAsyncTask asyncTask = new UserAvaiableAsyncTask();
				
			}
		});
		
		
	
		
		return super.onCreateDialog(savedInstanceState);
	}
	
	

	public interface FragmentUserSetupCallBack{
		public void onTaskDone(int avaiable);
	}

	
}
