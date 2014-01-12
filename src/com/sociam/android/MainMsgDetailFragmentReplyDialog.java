package com.sociam.android;
import java.util.Calendar;

import com.sociam.android.MainMsgDetailFragmentReplyDialog;
import com.sociam.android.R;
import com.sociam.android.model.RecieveMessage;
import com.sociam.android.model.ReplyMessage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
@SuppressLint("ValidFragment")
public class MainMsgDetailFragmentReplyDialog extends DialogFragment{
	
	int count_num;
	ReplyMessage replymsg;
	Location location;
	RecieveMessage rm;
	DataApplication dapp;
	SharedPreferences sp;
	String username_str;
	
	public MainMsgDetailFragmentReplyDialog(RecieveMessage rm) {
		this.rm=rm;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dapp = (DataApplication) getActivity().getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());	
		
		replymsg = new ReplyMessage();
		replymsg.setParenetId(rm.getID());
		
		
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.main_msg_detail_replymsg_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		setInterface(view);
		
		builder.setView(view);

		//InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        
		return builder.create();
	}

	private void setInterface(View view) {

		
		final TextView count_tx = (TextView) view.findViewById(R.id.mmd_replaymsg_count);
		count_tx.setTypeface(dapp.getTypefaceRobothin());
		
		final TextView username = (TextView) view.findViewById(R.id.mmd_reply_username);
		username.setTypeface(dapp.getTypefaceRobothin());
			
		
        EditText message = (EditText) view.findViewById(R.id.mmd_msg_edittext);                
        message.setTypeface(dapp.getTypefaceRobothin());
        message.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}                
            @Override
            public void afterTextChanged(Editable s) {
                    if(s.length()==0){
                            count_tx.setText("140");
                            
                    }else if(s.length()>0){
                           count_num = 140-s.length();
                            if(count_num<0) count_tx.setTextColor(Color.RED);
                            else count_tx.setTextColor(Color.WHITE);
                            
                            count_tx.setText(Integer.toString(count_num));
                    }
                    replymsg.setMsg(s.toString());
            }


    });


   
    
    ToggleButton user_card = (ToggleButton) view.findViewById(R.id.mmd_reply_anonymous);	
        user_card.setOnCheckedChangeListener(
        		new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if(isChecked){
							username_str =sp.getString("username", null);
							
							if(username_str!=null){
                                username.setText(username_str+" says...");
                                replymsg.setIdCode(false);
                                replymsg.setUser(username_str);									
							}else{
								username.setText("Anonymous says...");
								Toast.makeText(getActivity(), "You Don't Have Username Yet.", 
										Toast.LENGTH_SHORT).show();
								replymsg.setIdCode(true);
								
							}	
						}else{
							replymsg.setIdCode(true);
						}
						
					}
				});
        
        ImageView cancelbtn = (ImageView) view.findViewById(R.id.mmd_reply_cancel);
		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainMsgDetailFragmentReplyDialog.this.getDialog().dismiss();	
			}
		});
        
		ImageView send_msg = (ImageView) view.findViewById(R.id.mmd_reply_send);
		send_msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(count_num==0){
					Toast.makeText(getActivity(), "Please say something", Toast.LENGTH_SHORT).show();
				}else if(count_num>140){
					Toast.makeText(getActivity(), "Too long message", Toast.LENGTH_SHORT).show();
				}else{
				
				
				location = ((MainActivity) getActivity()).getLocation();
				replymsg.setLat(location.getLatitude());
				replymsg.setLng(location.getLongitude());
				Time now = new Time();
				now.setToNow();
				
				Log.v("sociam",dapp.getAnonymousID());
				
				ReplyMessageAsyncTask asyncTask = new ReplyMessageAsyncTask(getActivity(), 
						new ReplyMessageFragmentCallback() {
							
							@Override
							public void onTaskDone() {
								MainMsgDetailFragmentReplyDialog.this.getDialog().dismiss();
							}
						} );
			asyncTask.execute(
					Integer.toString(replymsg.getParentId()),
					replymsg.getIdCode() ? "1" : "0" ,
					replymsg.getIdCode() ? dapp.getAnonymousID() : username_str,
					Double.toString(replymsg.getLat()),
					Double.toString(replymsg.getLng()),
					now.format2445(),
					replymsg.getMsg()
					);
			
				
			}
			}
		});
		
	}
	
	public interface ReplyMessageFragmentCallback{
		public void onTaskDone();
	}
	

}



