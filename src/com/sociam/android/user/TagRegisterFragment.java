package com.sociam.android.user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sociam.android.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class TagRegisterFragment extends Fragment {
	
	Typeface robothin;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		view = getActivity().getLayoutInflater().inflate(R.layout.user_tags_frag, null);		
		robothin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf");

		
		setUpButton(getTagsFromLocal());

		
		return view;
	}
	
	private HashMap<String,String> getTagsFromLocal(){
		HashMap<String, String> tags = new HashMap<String, String>();
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
				tags.put(str[0], cate);
			}

			br.close();
		} catch (Exception e) {
			Log.e("sociam", e.getMessage());

		}
		
		
		return tags;
	}
	private void setUpButton(HashMap<String, String> tags){
		Log.e("sociam", "Start reading!");
		
		LinearLayout l_gene = (LinearLayout) view.findViewById(R.id.user_tag_frag_general);
		LinearLayout l_soton = (LinearLayout) view.findViewById(R.id.user_tag_frag_southampton);
		
		for(Map.Entry<String, String> entry: tags.entrySet() ){
			
			//Log.e("sociam", entry.getKey() + "and "+ entry.getValue());
			String text = entry.getKey();
			ToggleButton tb = new ToggleButton(getActivity());
			tb.setTypeface(robothin);
			tb.setButtonDrawable(R.drawable.tag_btn);
			tb.setTextOff(text);
			tb.setTextOn(text);
			
			l_gene.addView(tb);
			
//			if(entry.getValue().equals("general")){
//				l_gene.addView(tb);
//			}else{
//				l_soton.addView(tb);
//			}
			
			
			
			
		}
	}
	
}
