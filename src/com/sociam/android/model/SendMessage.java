package com.sociam.android.model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
/**
 * This class is the model of a new message.
 * 
 * <p>
 * When the user open MessageActivity, this class will be instanciated.
 * This contains all information of a new message.
 * If the user won't send the message the object will be discard. 
 * 
 * @author yukki
 *@version 1
 */
public class SendMessage {
	
	private int id;
	
	private String message;
	
	private String username;
	private boolean anonymous=true;
	private double lat,lon;
	
	private ArrayList<Tag> tags;
	
	
	/**
	 * {@link Constructor} 
	 * Initialise the class <p>
	 * It instanciate a new arraylist of tags. 
	 */
	public SendMessage() {
		tags = new ArrayList<Tag>();
	}
	
//	public void setMsgId(int i){
//		this.id=i;
//	}
//	public int getMsgId(){
//		return this.id;
//	}
	
	/**
	 * Set username or anonymous id
	 * @param user username or anonymous id
	 */
	public void setUserName(String user){
		this.username=user;
	}
	
	/**
	 * Return username or anonymous id
	 * @return username or anonymous id
	 */
	public String getUserName(){
		return this.username;
	}
	
	/**
	 * Set whether anonymous or not default is 'true'
	 * @param b 'ture':anonymous and 'false':username 
	 */
	public void setAnonymous(boolean b){
		this.anonymous=b;
	}
	
	/**
	 * Return whether anonymous or not
	 * @return 'ture': anonymous 'false':username
	 */
	public boolean getAnonymous(){
		return this.anonymous;
	}
	
	/**
	 * Set SendMessage latitude and longitude
	 * @param lat latitude of this message
	 * @param lon longitude of this message
	 */
	public void setLatLon(double lat,double lon){
		this.lat=lat;
		this.lon=lon;
	}
	
	/**
	 * Return latitude of this message
	 * @return latitude of this message
	 */
	public double getLat(){
		return this.lat;
	}
	
	/**
	 * Reutrn longitude of this message
	 * @return longitude of this message
	 */
	public double getLon(){
		return this.lon;
	}
	
	/**
	 * Return the arraylist of tags for this message
	 * @return arraylist of tags
	 */
	public ArrayList<Tag> getTagList(){
		return this.tags;
	}

	

	
	/**
	 * Set reply message
	 * @param str reply message
	 */
	public void setMsg(String str){
		this.message=str;
	}

	/**
	 * Reply reply message
	 * @return reply message
	 */
	public String getMsg(){
		return this.message;
	}

}



