package com.sociam.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.sociam.android.model.RecieveMessage;
import com.sociam.android.model.ReplyMessage;
import com.sociam.android.model.Tag;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint("ValidFragment")
public class MainMessageDetailFragmentDialog extends DialogFragment {

	SharedPreferences sp;
	DataApplication dapp;
	MainMessageDetailFragmentAdapter adapter;
	ArrayList<ReplyMessage> replyms;
	RecieveMessage rm;
	
	public MainMessageDetailFragmentDialog(RecieveMessage rem) {
		super();
		this.rm = rem;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setRetainInstance(true);
		dapp = (DataApplication) getActivity().getApplication();
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());		 

		View view = getActivity().getLayoutInflater().inflate(R.layout.main_msg_detailfdialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		replyms = rm.getReplyArrays();
		Collections.sort(replyms);
		
		adapter = new MainMessageDetailFragmentAdapter(getActivity(), 0,replyms);
		
		setInterface(view, adapter);

		builder.setView(view);		
		
		
		Dialog dialog = builder.create();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return dialog;
	}

	@Override
	public void onDestroyView() {
	  if (getDialog() != null && getRetainInstance())
	    getDialog().setOnDismissListener(null);
	  super.onDestroyView();
	}
	
	
	private void setInterface(View view, MainMessageDetailFragmentAdapter adapter) {
		
		TextView mmdfd_auther = (TextView) view.findViewById(R.id.mmdfd_auther);
		mmdfd_auther.setTypeface(dapp.getTypefaceRobothin());
		if(rm.getIdCode()==1) mmdfd_auther.setText("Anonymous says...");
		else mmdfd_auther.setText(rm.getUser() + " says..."); 
			
		ImageView closebtn = (ImageView) view.findViewById(R.id.mmdfd_closebtn);
		closebtn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				MainMessageDetailFragmentDialog.this.getDialog().dismiss();
			}
		});
		
		TextView mmdfd_msg = (TextView) view.findViewById(R.id.mmdfd_message);
		mmdfd_msg.setTypeface(dapp.getTypefaceRobothin());
		mmdfd_msg.setText(rm.getMsg());
		
		TextView tag_gene = (TextView) view.findViewById(R.id.mmdfd_tag_gene);
		tag_gene.setTypeface(dapp.getTypefaceRobothin());
		if(rm.getTagList().size()>0)
		tag_gene.setText(tagToString(rm));
		

		
		TextView num_up = (TextView) view.findViewById(R.id.num_upthumb);
		num_up.setTypeface(dapp.getTypefaceRobothin());
		num_up.setText(Integer.toString(rm.getUpThumb()));
		
		TextView num_down = (TextView) view.findViewById(R.id.num_downthumb);
		num_down.setTypeface(dapp.getTypefaceRobothin());
		num_down.setText(Integer.toString(rm.getDonwThumb()));
		
		 
		 SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
		 SimpleDateFormat datewk = new SimpleDateFormat("E", Locale.UK);
		 TextView time = (TextView) view.findViewById(R.id.text_time);
		 time.setTypeface(dapp.getTypefaceRobothin());
		 time.setText( datewk.format(rm.getTime().getTime())+"  "+time_format.format(rm.getTime().getTime()));
		
		 
		 
		 
		 
		TextView replybtn = (TextView) view.findViewById(R.id.mmdf_replybtn);
		replybtn.setTypeface(dapp.getTypefaceRobothin());
		replybtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainMsgDetailFragmentReplyDialog mmdfr = new MainMsgDetailFragmentReplyDialog(rm);
				mmdfr.show(getFragmentManager(), "sociam");
				
				MainMessageDetailFragmentDialog.this.getDialog().dismiss();
				
			}
		});
		
		TextView votebtn = (TextView) view.findViewById(R.id.mmdf_vote);
		votebtn.setTypeface(dapp.getTypefaceRobothin());
		votebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isAlreadyEvalMsg(rm.getID())){
					Toast.makeText(getActivity(), "You already vote this message", Toast.LENGTH_SHORT).show();
				}else if(isMyMessage(rm.getID())){
					Toast.makeText(getActivity(), "You cannot vote own message", Toast.LENGTH_SHORT).show();
				}else{
				
				EvaluateDialogFragment dialogFragment = new EvaluateDialogFragment(
						Integer.toString(rm.getID()), 1);
				dialogFragment.show(getFragmentManager(), "sociam");
			}
				
			}
		});
		 
		 
		ListView listview = (ListView) view.findViewById(R.id.mmd_listview);
		listview.setAdapter(adapter);
	}
	
	
	
	
	private CharSequence tagToString(RecieveMessage rm2) {
		ArrayList<Tag> tags = rm2.getTagList();
		String str="";
		for(Tag t : tags){
				str = str+ "#"+t.getName()+", ";
		}
		str = str.substring(0,str.length()-2);	
		return str;
	}



	public class MainMessageDetailFragmentAdapter extends ArrayAdapter<ReplyMessage>{
		
		private LayoutInflater inflater;
		
		public MainMessageDetailFragmentAdapter(Context con, int resource, List<ReplyMessage> list) {
			super(con, resource, list);
			inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ReplyMessage rm = getItem(position);
			 if(convertView==null) convertView = inflater.inflate(R.layout.main_msg_detailfd_list_row, null);
			 	
			 TextView username = (TextView) convertView.findViewById(R.id.mmd_reply_username);
			 username.setTypeface(dapp.getTypefaceRobothin());
			 if(rm.getIdCode()) username.setText("Anonymous says...");
			 else username.setText(rm.getUser()+ " says...");
			 
			 TextView msg = (TextView) convertView.findViewById(R.id.mmd_reply_text);
			 msg.setTypeface(dapp.getTypefaceRobothin());
			 msg.setText(rm.getMsg());
			 
			 Calendar cal = rm.getTime();
			 SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
			 
			 TextView time = (TextView) convertView.findViewById(R.id.mmd_repry_time);
			 time.setTypeface(dapp.getTypefaceRobothin());
			 time.setText(time_format.format(cal.getTime()));
			 
			
			 
			 
			return convertView;
		}
		
	}
	

	public boolean isMyMessage(int msg_id){
		Log.e("sociam",sp.getString("message_id", ""));
		String[] list = sp.getString("message_id", "").split(",");
		for(String str : list){
			if(str.equals(Integer.toString(msg_id))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAlreadyEvalMsg(int msg_id){
		String[] list = sp.getString("eval_msg", "").split(",");
		for(String str : list){
			if(str.equals(Integer.toString(msg_id))){
				return true;
			}
		}
		return false;
	}
	
	
	
}
