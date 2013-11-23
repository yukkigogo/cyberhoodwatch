package com.sociam.android.user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sociam.android.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ToggleButton;

public class TagRegisterFragmentDialog extends DialogFragment {
	
	Typeface robothin;
	View view ;

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.user_tags_frag, null);		
		
		//ScrollView scrollView = new ScrollView(getActivity());
		//scrollView.addView(view);
		
		robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");		
		
		setUpButton(getTagsFromLocal());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setView(view);
		builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TagRegisterFragmentDialog.this.getDialog().dismiss();
				
			}
		});
			
			
		return builder.create();
	}
	
	

		
	
	private ArrayList<Tag> getTagsFromLocal(){
		ArrayList<Tag> tags = new ArrayList<Tag>();
		String FILENAME = "tag.csv";
		
		try {
			FileInputStream in = getActivity().openFileInput(FILENAME);
			InputStreamReader ins = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(ins);
			String currentLine;
			
			while((currentLine=br.readLine())!=null){
				String str[] = currentLine.split(",");
				String cate;
				if(str[1]!=null) cate=str[1];
				else cate="";
				tags.add(new Tag(str[0],cate));
			}

			br.close();
		} catch (Exception e) {
			Log.e("sociam", e.getMessage());

		}
		
		
		return tags;
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
					Log.e("sociam", Integer.toString(count_g));
					l_gene.addView(row_g);
					
					row_g = new LinearLayout(getActivity());
					row_g.setOrientation(0);
					row_g.setGravity(Gravity.CENTER_HORIZONTAL);
					
				
					//Log.e("sociam", entry.getKey() + "and "+ entry.getValue());
					ToggleButton tb = setToggleButton(text);
					
					row_g.addView(tb);
					count_g=text.length();
					count_row_g=0;
				
				}else{	
					Log.v("sociam", Integer.toString(count_g));

					ToggleButton tb = setToggleButton(text);
					row_g.addView(tb);
					++count_row_g; 
					
				}
				
				
			}else{
				
				
				count_s = count_s + text.length();
				
				Log.v("sociam", "soton " + text);

				if(count_s>25 || count_row_s>2){
					Log.e("sociam", "soton " + text);

					l_soton.addView(row_s);
					
					row_s = new LinearLayout(getActivity());
					row_s.setOrientation(0);
					row_s.setGravity(Gravity.CENTER_HORIZONTAL);
					
				
					ToggleButton tb = setToggleButton(text);
					
					row_s.addView(tb);
					count_s=text.length();
					count_row_s=0;
				
				}else{	
					Log.e("sociam", "soton row " + text);

					ToggleButton tb = setToggleButton(text);
					row_s.addView(tb);
					++count_row_s; 
					
				}
					
				
			}		
		}
		
		l_gene.addView(row_g);
		l_soton.addView(row_s);

	}
	
	
	private ToggleButton setToggleButton(String text){
		LinearLayout.LayoutParams pane = new LayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		ToggleButton tb = new ToggleButton(getActivity());
		tb.setTypeface(robothin);
		tb.setButtonDrawable(R.drawable.tag_btn);
		tb.setTextOff(text);
		tb.setTextOn(text);
		tb.setText(text);
		tb.setLayoutParams(pane);
		
		return tb;
	}
}
