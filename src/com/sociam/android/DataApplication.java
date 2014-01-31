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
/**
 * Extended for Application class. 
 *  
 *  Application class is useful for sharing objects between activities.  
 * 
 * @version 1.0 
 * @author yukki
 */
public class DataApplication extends Application {

	/** SharedPreference */
	private SharedPreferences sp;
	/** List for usertags */
	private ArrayList<Tag> initialTags=null;
	
	/**
	 * Initial set up for application. 
	 * <p>
	 * Set up SharePreference and initial tag list
	 */
	@Override
	public void onCreate() {
		super.onCreate();		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		setInitTags();
	}
	
    /**
     *  Get a list of Tags 
     * @return A list contains all tags 
     */
	public ArrayList<Tag> getInitTags(){
    	return this.initialTags;
    }
	
	/**
	 *  Initial setup for tags for user setting. User setting read from sharedpreference.  
	 *  
	 * @return boolean true  
	 */
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

	
	/**
	 * Helper fucntion for setup the tag list. 
	 * <p>
	 * It reads from the local cvs file and create a list with tags
	 * 
	 * @param list ArrayList<Tag> to hold tag lists 
	 * @param usertagString a String which contains sequence of tags. 
	 */
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
	
	/**
	 * Set Initial tags from local cvs files
	 * <p>
	 * It reads local cvs file 'tag.csv' to make list<Tag>
	 *  
	 * @return List<Tag> contains all tags 
	 */
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
	
	  /**
	   * It sets all tags which are all default setting
	   * 
	   */
	  public void cleanTags(){
		  for(Tag t : initialTags){
			  t.setMsgSetting(false);
			  //Log.v(t.getName()+" "+t.getUserSetting(),"sociam");
		  }
	  }
	

	  /**
	   * Helper function : create today's anonymous ID
	   * 
	   * @return today's anonymous ID String 
	   */
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
	  
	  
	  
    /**
     * Helper function : font setting  'Roboto-Light'
     * @return Typeface object 'Roboto-Light' 
     */
	public Typeface getTypefaceRobothin() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		
		return robothin;
	}

    /**
     * Helper function : font setting 'Roboto-Regular-Italic'
     * @return Typeface object 'Roboto-Regular-Italic' 
     */
	public Typeface getTypefaceRoboRegItalic() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"Roboto-Italic.ttf");
		
		return robothin;
	}

	/**
	 * Helper function : font setting 'RobotoCondensed-Regular'
	 * @return Typeface object 'RobotoCondensed-Regular'
	 */
	public Typeface getTypefaceRoboReg() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"RobotoCondensed-Regular.ttf");
		
		return robothin;
	}
	
    /**
     * Helper function : font setting 'Roboto-Italic'
     * @return Typeface object 'Roboto-Italic'
     */
	public Typeface getTypefaceRoboBoldItalic() {
		Typeface robothin =  Typeface.createFromAsset(getAssets(), 
				"Roboto-BoldItalic.ttf");
		
		return robothin;
	}
	
	
	
	
	
}
