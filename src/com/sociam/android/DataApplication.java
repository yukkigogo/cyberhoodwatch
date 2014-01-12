package com.sociam.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sociam.android.model.Tag;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.format.Time;
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
		if((sp.getString("usertag", ""))!=""){
			// add user parameter to each tags
			setInitTags4User(initialTags, (sp.getString("usertag", null)));
			
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
	
	  public void cleanTags(){
		  for(Tag t : initialTags){
			  t.setMsgSetting(false);
			  //Log.v(t.getName()+" "+t.getUserSetting(),"sociam");
		  }
	  }
	

	  //create hashID
      public String getAnonymousID() {
          // setup today's ID
          Time t = new Time();
          t.setToNow();
          String user_id = sp.getString("uuid", "false")
                          +"-"+Integer.toString(t.monthDay)+"-"+Integer.toString(t.month)+"-"
                          +Integer.toString(t.year);
  
  
          Log.w("sociam", "id before hash "+ user_id);
          
          try {
                  MessageDigest md = MessageDigest.getInstance("MD5");
                  md.update(user_id.getBytes());
                  byte[] digest = md.digest();
                  StringBuffer sb = new StringBuffer();
                  for (byte b : digest) {
                          sb.append(Integer.toHexString((int) (b & 0xff)));
                  }
                  user_id = sb.toString();
                  Log.w("sociam", "id after hash "+ user_id);
          } catch (NoSuchAlgorithmException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
          }
          
          return user_id;
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

	
	public Typeface getTypefaceRoboReg() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"RobotoCondensed-Regular.ttf");
		
		return robothin;
	}
	
	public Typeface getTypefaceRoboBoldItalic() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"Roboto-BoldItalic.ttf");
		
		return robothin;
	}
	
	
	
	
	
}
