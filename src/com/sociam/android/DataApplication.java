package com.sociam.android;

import java.util.HashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class DataApplication extends Application {
	
	private HashMap<String, String> tagMap;
	SharedPreferences sp;
	
	

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		if((sp.getString("username", null))!=null)
		setTagMap(sp.getString("usertags", null));	
		
	}
	
	public void setTagMap(String usertags){
		if(usertags!=null){
		tagMap = new HashMap<String, String>();
		
		if(usertags!=null){
			String[] tagWcat = usertags.split(":");
			for(String str:tagWcat){
				String[] ary=str.split("-");
				tagMap.put(ary[0], ary[1]);				
			}
		}
		}
	}
		
	
	public HashMap<String,String> getTagMap4User(){
		return this.tagMap;
	}

	public Typeface getTypefaceRobothin() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		
		return robothin;
	}


	public Typeface getTypefaceRoboRegItalic() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"Roboto-Italic.ttf");
		
		return robothin;
	}

	
}
