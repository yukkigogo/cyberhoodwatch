package com.sociam.android;

import java.util.HashMap;

import android.app.Application;

public class DataApplication extends Application {
	
	private HashMap<String, String> tagMap;
	
	@Override
	public void onCreate() {
		super.onCreate();
		// make user 
		String usertags = "female-general:student-general:susu-southampton";
		
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
	
	
	
}
