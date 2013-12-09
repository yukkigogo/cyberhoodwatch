package com.sociam.android;

import java.util.HashMap;

import android.app.Application;
import android.graphics.Typeface;

public class DataApplication extends Application {
	
	private HashMap<String, String> tagMap;
	
	
	

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// set location
		//setMyLocationManager();
		
		// make user 
		String usertags = "female-general:student-general:susu-southampton";
		setTagMap(usertags);	
		
	}
	
	public void setTagMap(String usertags){
		tagMap = new HashMap<String, String>();
		
		if(usertags!=null){
			String[] tagWcat = usertags.split(":");
			for(String str:tagWcat){
				String[] ary=str.split("-");
				tagMap.put(ary[0], ary[1]);				
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
