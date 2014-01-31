package com.sociam.android.model;

import java.util.ArrayList;
import java.util.Calendar;

import android.text.format.Time;

/**
 *This class is model of a reply of a message.
 *Each reply has to have the message id (parent_id)
 * <p>
 * It is used two ways. <br> 
 * 1. it is used when a user open reply dialog and keep input information from the user<br> 
 * 2. When user download the messages, then if a message has reply messages, this class will be instantiate to  hold those information.
 *
 * @author yukki
 * @version 1
 */
public class ReplyMessage implements Comparable{

	private int id;
	private int parent_id;
	private String message;	
	private String username;
	private boolean id_code=true;
	private double lat,lon;
	private Calendar datetime;
	
	
	public ReplyMessage() {
	}
	
	/**
	 * Set this ReplyMessage ID
	 * @param i reply message id
	 */
	public void setID(int i){
		this.id=i;
	}
	
	/**
	 * Return the ReplyMessage ID
	 * @return ReplyMesasge ID
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Set RecieveMessage ID 
	 * <p>
	 * this is id for RecieveMessage ID, which connect to the main message. 
	 * 
	 * @see com.sociam.android.model.RecieveMessage
	 * @param i ID number of RecieveMessage 
	 */
	public void setParenetId(int i){
		this.parent_id=i;
	}
	
	/**
	 * Return the RecieveMessage ID 
	 * @return ID number of RecieveMessage
	 */
	public int getParentId(){
		return this.parent_id;
	}
	
	/**
	 * Set user id code anonymous or username
	 *  
	 * @param i '1' anonymous '0' username
	 */
	public void setIdCode(boolean i){
		this.id_code=i;
	}
	
	/**
	 * Return user id code : anonymous of username
	 * @return '1' anonymous '0' username
	 */
	public boolean getIdCode(){
		return this.id_code;
	}
	
	/**
	 * Set reply message
	 * @param str context of replymessage
	 */
	public void setMsg(String str){
		this.message=str;
	}
	
	/**
	 * Return reply message
	 * @return context of replymessage
	 */
	public String getMsg(){
		return this.message;
	}
	
	/**
	 * Set username or anonymous id
	 * 
	 * @param usr anonymous id or username
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
	 * Set date and time of this reply message
	 * @param cal date and time 
	 */
	public void setTime(Calendar cal){
		datetime=cal;
	}
	
	/**
	 * Return date and time of this reply message
	 * @return date and time of reply message
	 */
	public Calendar getTime(){
		return this.datetime;
	}
	
	/**
	 * Set latitude of this ReplyMessage
	 * @param l latitude of ReplyMessage
	 */
	public void setLat(double l){
		this.lat=l;
	}
	
	/**
	 * Return latitude of this ReplyMessage
	 * @return latitude of ReplyMessage
	 */
	public double getLat(){
		return this.lat;
	}
	
	/**
	 * Set longitude of this ReplyMessage
	 * @param l longitude of this ReplyMessage
	 */
	public void setLng(double l){
		this.lon=l;
	}
	
	/**
	 * Return longitude of this ReplyMessage
	 * @return longitude of this ReplyMessage
	 */
	public double getLng(){
		return this.lon;
	}
	
	
	/**
	 * Compare with time, 
	 */
	@Override
	public int compareTo(Object obj) {
		return this.datetime.compareTo(((ReplyMessage) obj).getTime());
	}
	
}
