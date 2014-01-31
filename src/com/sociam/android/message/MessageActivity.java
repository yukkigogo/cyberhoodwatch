package com.sociam.android.message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.model.SendMessage;
import com.sociam.android.model.Tag;

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
/**
 * This is the main activity for sending a message.
 * 
 * @author yukki
 *
 */

// main activity for message
public class MessageActivity extends FragmentActivity{
	
	SharedPreferences sp; 
	
	double lat,lon;
	SendMessage um;
	
	ArrayList<Tag> tags ;
//	HashMap<String,String> tagMap; 
	boolean isTagChanged = false;
	boolean alreadyRegister;
	
	ToggleButton tagsToggle; 
	DataApplication dapp;
	
	/**
	 * Method is excuted when the user click the add message button.
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		um = new SendMessage();
		lat = getIntent().getExtras().getDouble("lat");
		lon = getIntent().getExtras().getDouble("lon");
		um.setLatLon(lat, lon);
		
		setContentView(R.layout.message_main);
				
		sp = PreferenceManager.getDefaultSharedPreferences(this);		 
		dapp = (DataApplication) getApplication();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		tags = dapp.getInitTags();
//		if((sp.getString("username", null))!=null) tags = setTagsForUser(getTagsFromLocal());
		
		tagsToggle = (ToggleButton) findViewById(R.id.tags);
		setToggle(tagsToggle);
		
		
	}
	

	/**
	 * Set the id and tag buttons.
	 * @param toggle
	 */
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
	
	/**
	 * Helper function is used by MessgeFragment. 
	 * Change the tag toggle button ON
	 * @param checked 'true' : ON, 'false' : OFF 
	 */
	public void setTagchange(boolean checked){
		tagsToggle.setChecked(checked);
	}

	
	
	
	
	
	/**
	 * Set menu layout in the screen 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.message, menu);
		return true;
	}

	/**
	 * Method control the menu in action ber
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		switch (item.getItemId()){
		case android.R.id.home:
			dapp.cleanTags();
			finish();			
			break;
			
		case R.id.msg_send:
			
			
			if(um.getMsg()!=null){
			if(  um.getMsg().length()>0 ){
				if(um.getMsg().length()<=140){
					//	Log.e("sociam","show tag index num : "+ um.getTime().format2445());
				
				Time rightnow = new Time();
				rightnow.setToNow();
				MessageUpAsyncTask up = new MessageUpAsyncTask(this, 
					new MessageFragmentCallBack() {
				
				@Override
				public void onTaskDone() {
					dapp.cleanTags();
					finish();
					
					
				}
			});
			String username,id_code;
			if(um.getAnonymous()){ 
				username=getID();
				id_code="1";
			}else{ 
				username = um.getUserName();
				id_code="0";
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

	
	
	private String getID() {
		// setup today's ID
		Time t = new Time();
		t.setToNow();
		String user_id ="";
		String  raw_user= sp.getString("uuid", null);
				
		if(raw_user !=null){		
				user_id= raw_user+"-"+Integer.toString(t.monthDay)+"-"+Integer.toString(t.month)+"-"
				+Integer.toString(t.year);
		}else{
			Log.e("sociam", "id error shows false");

		}
	
		
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
			e.printStackTrace();
		}
		
		return user_id;
		
	}

	

	/**
	 * Return user location - latitude
	 * @return latitude
	 */
	public double getLat(){
		return this.lat;
	}
	/**
	 * Return user location - longitude
	 * @return longitude
	 */
	public double getLon(){
		return this.lon;
	}
	
	/**
	 * Return SendMessage Object
	 * @return SendMessage 
	 */
	public SendMessage getUM(){
		return this.um;
	}

	/**
	 * Return a list of all tags which contains user input. 
	 * @return a list of tags
	 */
	public ArrayList<Tag> getTags(){
		return this.tags;
	}

	/**
	 * Set if the user changed tag setting from default
	 * @param b 'true' if changed
	 */
	public void setIsTagChanged(boolean b){
		this.isTagChanged=b;
	}
	/**
	 * Return whether the user changed the tag setting.
	 * @return 'true' : if the user changed the tag setting
	 */
	public boolean getIsTagChanged(){
		return this.isTagChanged;
	}
	
	
	/**
	 * callback Interface from UpAsyncTask
	 * @author yukki
	 *
	 */
	public interface MessageFragmentCallBack{
		public void onTaskDone();
	}


}
