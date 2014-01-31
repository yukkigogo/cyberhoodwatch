package com.sociam.android.model;

import java.util.ArrayList;
import java.util.Calendar;

import android.text.format.Time;

/**
 * This class is the model of messages from the server.
 * It is used for plot message markers on the map.
 * Each ReciveMessage holds a list of replymessages and a list of tags.
 * 
 * @author yukki
 *@version 1
 */
public class RecieveMessage {

	private int id;
	private String message;	
	private String username;
	private int id_code;
	//private boolean anonymous;
	private double lat,lon;
	private Calendar datetime;
	private ArrayList<Tag> tags;
	private int up_thumb;
	private int down_thumb;
	private ArrayList<ReplyMessage> myreplys;
	
	
	/**
	 * All the parameters will set up via set..() methods
	 * This constructor instantiates arraylists of tags and replymessages 
	 */
	public RecieveMessage() {
		tags = new ArrayList<Tag>();
		myreplys = new ArrayList<ReplyMessage>();
	}
	
	/**
	 * Set to add reply message
	 * @param msg ReplyMessage to add the arraylist
	 */
	public void setReplyMsg(ReplyMessage msg){
		myreplys.add(msg);
	}
	
	/**
	 * Return a list contains reply messages 
	 * 
	 * @see com.sociam.android.model.ReplyMessage 
	 * @return a list of replymessages 
	 */
	public ArrayList<ReplyMessage> getReplyArrays(){
		return this.myreplys;
	}
	
	/**
	 * Set message ID (from DB)
	 * @param i message ID
	 */
	public void setID(int i){
		this.id=i;
	}
	
	/**
	 * Return this message ID
	 * @return message ID
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Set whether this message posted by anonymous id or username
	 * @param i '1' if anonymous '0' if username
	 */
	public void setIdCode(int i){
		this.id_code=i;
	}
	
	/**
	 * Return whether this message posted by anonymous id or username
	 * @return '1' if anonymous '0' if username
	 */
	public int getIdCode(){
		return this.id_code;
	}
	
	/**
	 * Set context of the message 
	 * @param str message
	 */
	public void setMsg(String str){
		this.message=str;
	}
	
	/**
	 * Return the message 
	 * @return message
	 */
	public String getMsg(){
		return this.message;
	}
	
	/**
	 * Set username or anonymous id
	 * @param usr username or anonymous id
	 */
	public void setUser(String usr){
		username = usr;
	}
	
	/**
	 * Return username or anonymous id
	 * @return username or anonymous id
	 */
	public String getUser(){
		return this.username;
	}

	/**
	 * Set message date and time
	 * @param cal date and time
	 */
	public void setTime(Calendar cal){
		datetime=cal;
	}
	
	/**
	 *  Return message date and time
	 * @return date and time 
	 */
	public Calendar getTime(){
		return this.datetime;
	}
	
	/**
	 * Set latitude of message
	 * @param l latitude 
	 */
	public void setLat(double l){
		this.lat=l;
	}
	
	/**
	 * Return the latitude of message
	 * @return latitude
	 */
	public double getLat(){
		return this.lat;
	}
	
	/**
	 * Set longitude of message 
	 * @param l longitude
	 */
	public void setLng(double l){
		this.lon=l;
	}
	
	/**
	 *  Return longitude of message
	 * @return longitude
	 */
	public double getLng(){
		return this.lon;
	}
	
	/**
	 * Add tag object into the message 
	 * @param t tag to be add to the list
	 */
	public void addTag(Tag t){
		tags.add(t);
	}
	
	/**
	 * Return the list of tags
	 * @return tags of this message
	 */
	public ArrayList<Tag> getTagList(){
		return this.tags;
	}
	
	/**
	 * Set the num of upvote
	 * @param i number of upvote 
	 */
	public void setUpThumb(int i){
		this.up_thumb=i;
	}
	
	/**
	 * Return the number of downvote
	 * @return number of downvote 
	 */
	public int getUpThumb(){
		return this.up_thumb;
	}
	
	/**
	 * Set the number of up vote of the message
	 * @param i number of upvote
	 */
	public void setDownThumb(int i){
		this.down_thumb=i;
	}
	
	/**
	 * Return the number of down vote
	 * @return number of downvote
	 */
	public int getDonwThumb(){
		return this.down_thumb;
	}
}
