package com.sociam.android.message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.SendMessage;
import com.sociam.android.Tag;


import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Toast;
import android.widget.ToggleButton;


// main activity for message
public class MessageFragmentActivity extends FragmentActivity{
	
	
	SharedPreferences sp; 
	
	double lat,lon;
	SendMessage um;
	
	ArrayList<Tag> tags ;
	HashMap<String,String> tagMap; 
	boolean isTagChanged = false;

	ToggleButton tagsToggle; 

	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		um = new SendMessage();
		lat = getIntent().getExtras().getDouble("lat");
		lon = getIntent().getExtras().getDouble("lon");
		um.setLatLon(lat, lon);
		
		setContentView(R.layout.message_main);
				
		sp = PreferenceManager.getDefaultSharedPreferences(this);		 
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		tags = setTagsForUser(getTagsFromLocal());
		
		tagsToggle = (ToggleButton) findViewById(R.id.tags);
		setToggle(tagsToggle);
	}
	
	
	private void setToggle(final ToggleButton toggle){
		toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				toggle.setChecked(isTagChanged);
				MessageSettingTagFragmentDialog dialog = new MessageSettingTagFragmentDialog();
				dialog.show(getSupportFragmentManager(), "sociam");
				
			
			}
		});
	}
	
	public void setTagchange(boolean checked){
		tagsToggle.setChecked(checked);
	}

	
	private ArrayList<Tag> setTagsForUser(ArrayList<Tag> tags){
		
		//String usertags = sp.getString("tags",null);
		//		String usertags = "female-general:student-general:susu-southampton";
//		tagMap = new HashMap<String, String>();
//		
//		if(usertags!=null){
//			String[] tagWcat = usertags.split(":");
//			for(String str:tagWcat){
//				String[] ary=str.split("-");
//				tagMap.put(ary[0], ary[1]);				
//			}
			
		DataApplication dapp = (DataApplication)this.getApplication();
		tagMap = dapp.getTagMap4User();
		
		for(Tag tag : tags){
			if(tagMap.containsKey(tag.getName())){				
				if(tag.getCategory().equals(tagMap.get(tag.getName()))){
					tag.setUserSetting(true);
					tag.setMsgSetting(true);
				}		
			}
		}
		
//		}
		
		
		return tags;
	}
	
	private ArrayList<Tag> getTagsFromLocal(){
		tags = new ArrayList<Tag>();
		String FILENAME = "tag.csv";
		
		try {
			FileInputStream in = openFileInput(FILENAME);
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
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()){
		case android.R.id.home:
			finish();			
			break;
			
		case R.id.msg_send:
			
			if(um.getMsg().length()>0){
				if(um.getMsg().length()<=140){
					//	Log.e("sociam","show tag index num : "+ um.getTime().format2445());
				
				Time rightnow = new Time();
				rightnow.setToNow();
				MessageUpAsyncTask up = new MessageUpAsyncTask(this, 
					new MessageFragmentCallBack() {
				
				@Override
				public void onTaskDone() {
					finish();
					
					
				}
			});
			String username,id_code;
			if(um.getAnonymous()){ 
				username="anonymous";
				id_code="0";
			}else{ 
				username = um.getUserName();
				id_code="1";
			}
			
			String tagString = getTagString(tags);

			String lat = Double.toString(um.getLat());
			String lon = Double.toString(um.getLon());


			
			up.execute(
					username,
					id_code,
					lat,
					lon,
					rightnow.format2445(),
					um.getMsg(),
					tagString
					);
			
				}else{
					Toast.makeText(this, "your text is over limit..", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(this, "Please add your message..", Toast.LENGTH_LONG).show();
			}
			
			break;
		
		}	
		
		return super.onOptionsItemSelected(item);
	}
	
	
	private String getTagString(ArrayList<Tag> tagList) {
		String tagString="";
		if(tagList.size()>0){
			for(Tag tag:tagList){
				if(tag.getMsgSetting()){
					tagString = tagString+tag.getName()+"-"+tag.getCategory()+":";
				}
			}
		}
		
		
		return tagString;
	}


	// helper funtion to pass the objects
	public double getLat(){
		return this.lat;
	}
	public double getLon(){
		return this.lon;
	}
	
	public SendMessage getUM(){
		return this.um;
	}

	public ArrayList<Tag> getTags(){
		return this.tags;
	}


	public void setIsTagChanged(boolean b){
		this.isTagChanged=b;
	}
	public boolean getIsTagChanged(){
		return this.isTagChanged;
	}
	
	
	public interface MessageFragmentCallBack{
		public void onTaskDone();
	}


}
