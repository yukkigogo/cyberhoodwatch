package com.sociam.android;

import java.lang.reflect.Array;
import java.util.ArrayList;


import android.text.format.Time;

public class SendMessage {
	
	int id;
	
	String message;
	
	String username;
	boolean pesudo=false;
	double lat,lon;
	
	ArrayList<Tag> tags;
	private Time date;
	
	private int up_thumb=0;
	private int down_thumb=0;
	

	
	
	public SendMessage() {
		tags = new ArrayList<Tag>();
	}
	
	public void setMsgId(int i){
		this.id=i;
	}
	public int getMsgId(){
		return this.id;
	}
	
	public void setUserName(String user){
		this.username=user;
	}
	public String getUserName(){
		return this.username;
	}
	public void setAnonymous(boolean b){
		this.pesudo=b;
	}
	public boolean getAnonymous(){
		return this.pesudo;
	}
	public void setLatLon(double lat,double lon){
		this.lat=lat;
		this.lon=lon;
	}
	public double getLat(){
		return this.lat;
	}
	public double getLon(){
		return this.lon;
	}
	
	public ArrayList<Tag> getTagList(){
		return this.tags;
	}
	public void setTime(Time t){
		this.date=t;
	}
	public Time getTime(){
		return this.date;
	}
	public void setUpThumb(int i){
		this.up_thumb=i;
	}
	public int getUpThumb(){
		return this.up_thumb;
	}
	public void setDownThumb(int i){
		this.down_thumb=i;
	}
	public int getDonwThumb(){
		return this.down_thumb;
	}



}



