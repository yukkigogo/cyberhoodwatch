package com.sociam.android.message;


import java.util.ArrayList;
import java.util.HashMap;


import com.sociam.android.R;
import com.sociam.android.SendMessage;
import com.sociam.android.Tag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ToggleButton;

public class MessageSettingTagFragmentDialog extends DialogFragment {
	
	Typeface robothin;
	View view ;
	SendMessage msg;
	ArrayList<Tag> tags ;
	HashMap<Tag,ToggleButton> btns;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.user_tags_frag, null);		
		msg = ((MessageFragmentActivity) getActivity()).getUM();
		//ScrollView scrollView = new ScrollView(getActivity());
		//scrollView.addView(view);
		
		robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");		
		btns = new HashMap<Tag, ToggleButton>();
		tags = ((MessageFragmentActivity) getActivity()).getTags();
		setUpButton(tags);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// need to improve the title
		builder.setTitle("Choose which social group you want to tell!");
		builder.setView(view);
		builder.setNegativeButton("Defult", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//check the tag changed or not
				for(Tag tag:tags){
					
					
					
					if(tag.getMsgSetting()!=tag.getUserSetting()){
						((MessageFragmentActivity) getActivity()).setTagchange(true);
						break;
					}
				}
				

				
				MessageSettingTagFragmentDialog.this.getDialog().dismiss();

				
			}
		});
		
		Log.e("sociam", "____________ how was? "+ ((MessageFragmentActivity) getActivity()).getIsTagChanged());

		
		return builder.create();
	}
	
	
	@Override
	public void onStart() {

		super.onStart();

		AlertDialog d = (AlertDialog)getDialog();
	    if(d != null)
	    {
	        Button negativeButton = (Button) d.getButton(Dialog.BUTTON_NEGATIVE);
	        negativeButton.setOnClickListener(new View.OnClickListener()
	                {
	                    @Override
	                    public void onClick(View v)
	                    {
	        				setDefult(tags, btns);
	        				((MessageFragmentActivity) getActivity()).setTagchange(false);
	                    	Boolean wantToCloseDialog = false;
	                        if(wantToCloseDialog)
	                            dismiss();
	                    }
	                });
	    }
	
	}
	

	private void setDefult(ArrayList<Tag> ary, HashMap<Tag,ToggleButton> btns){
		for(Tag tag: ary){
			if(tag.getUserSetting()){
				ToggleButton btn = btns.get(tag);
				btn.setChecked(true);
				tag.setMsgSetting(true);
			}else{
				ToggleButton btn = btns.get(tag);
				btn.setChecked(false);
				tag.setMsgSetting(false);
			}
			
		}
	}	
	
	
	
	private void setUpButton(ArrayList<Tag> tags){
		Log.e("sociam", "Start reading!");
		
		LinearLayout l_gene = (LinearLayout) view.findViewById(R.id.user_tag_frag_general);
		LinearLayout l_soton = (LinearLayout) view.findViewById(R.id.user_tag_frag_southampton);
		
		LinearLayout row_g = new LinearLayout(getActivity());
		row_g.setOrientation(0);
		row_g.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LinearLayout row_s = new LinearLayout(getActivity());
		row_s.setOrientation(0);
		row_s.setGravity(Gravity.CENTER_HORIZONTAL);

		
		
		int count_g=0;
		int count_row_g=0;
		int count_s=0;
		int count_row_s=0;
		
		for(Tag tag : tags){
			String text = tag.getName();
			
			if(tag.getCategory().equals("general")){	
				
				count_g = count_g + text.length();
				
				if(count_g>20 || count_row_g>2){
					
				//	Log.e("sociam", Integer.toString(count_g));
					l_gene.addView(row_g);
					
					row_g = new LinearLayout(getActivity());
					row_g.setOrientation(0);
					row_g.setGravity(Gravity.CENTER_HORIZONTAL);
					
				
					ToggleButton tb = setToggleButton(tag);
					
					row_g.addView(tb);
					count_g=text.length();
					count_row_g=0;
					
					
				
				}else{	
				//	Log.v("sociam", Integer.toString(count_g));

					ToggleButton tb = setToggleButton(tag);
					row_g.addView(tb);
					++count_row_g; 
					
				}
				
				
			}else{
				
				
				count_s = count_s + text.length();
				

				if(count_s>25 || count_row_s>2){

					l_soton.addView(row_s);
					
					row_s = new LinearLayout(getActivity());
					row_s.setOrientation(0);
					row_s.setGravity(Gravity.CENTER_HORIZONTAL);

					
				
					ToggleButton tb = setToggleButton(tag);
					row_s.addView(tb);
					count_s=text.length();
					count_row_s=0;
					
					
				
				}else{	
				//	Log.e("sociam", "soton row " + text);

					ToggleButton tb = setToggleButton(tag);
					row_s.addView(tb);
					++count_row_s; 
					
				}
					
				
			}		
		}
		
		l_gene.addView(row_g);
		l_soton.addView(row_s);

	}
	
	
	private ToggleButton setToggleButton(final Tag tag){
		
		String text = tag.getName();
		LinearLayout.LayoutParams pane = new LayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pane.leftMargin=10;
		pane.topMargin=10;
		final ToggleButton tb = new ToggleButton(getActivity());
		tb.setTypeface(robothin);
		tb.setBackgroundResource(R.drawable.tag_btn);
		tb.setTextOff(text);
		tb.setTextOn(text);
		tb.setText(text);
		tb.setLayoutParams(pane);
		
		//Log.v("sociam","tag change "+((MessageFragmentActivity) getActivity()).getIsTagChanged());
		if(((MessageFragmentActivity) getActivity()).getIsTagChanged()){	
			if(tag.getMsgSetting()){
				tb.setChecked(true);
				tb.setTextColor(Color.WHITE);
				tag.setMsgSetting(true);
				
			}else{
				tb.setChecked(false);
				tb.setTextColor(Color.WHITE);
				tag.setMsgSetting(false);
			}
		}else{
			if(tag.getUserSetting()){
				tb.setChecked(true);
				tb.setTextColor(Color.WHITE);
				tag.setMsgSetting(true);
				
			}		
		}
		
		tb.setOnCheckedChangeListener(new 
				CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {
						
						if(isChecked){
							tb.setTextColor(Color.WHITE);
							tag.setMsgSetting(true);
						}else{
							tb.setTextColor(Color.BLACK);
							tag.setMsgSetting(false);
						}

						
						
					}
				});
		
		btns.put(tag,tb);
		return tb;
	}
	


		
		

}
