package com.sociam.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;

public class DataApplication extends Application {
	
	private SharedPreferences sp;
	private ArrayList<Tag> initialTags=null;
	

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		
		setInitTags();
		
	}
	
    public ArrayList<Tag> getInitTags(){
    	return this.initialTags;
    }
	
	public boolean setInitTags() {

		// read tag.csv and build plain tags
		initialTags = getTagsFromLocal();
		
		// is usertags is not null
		if((sp.getString("usertags", null))!=null){
			// add user parameter to each tags
			setInitTags4User(initialTags, (sp.getString("usertags", null)));
			
		}
		return true;
	}

	private void setInitTags4User(ArrayList<Tag> list, String usertagString) {
			
		// user tag setting to maps
		HashMap userMaps = new HashMap<String, String>();
		String[] tagWcat = usertagString.split(":");			
			for(String str : tagWcat){
				String[] ary=str.split("-");
				userMaps.put(ary[0], ary[1]);				
			}
		
		for(Tag t : list){
			if(userMaps.containsKey(t.getName())) 
				t.setUserSetting(true);
		}	
						
	}			
	
	  private ArrayList<Tag> getTagsFromLocal(){
         ArrayList<Tag> list = new ArrayList<Tag>();
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
                          list.add(new Tag(str[0],cate));
                  }

                  br.close();
          } catch (Exception e) {
                  Log.v("sociam", "DataApplication getTagFromLocal problem");

          }
          return list;
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
