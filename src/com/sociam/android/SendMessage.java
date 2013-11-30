package com.sociam.android;

import java.util.ArrayList;

public class SendMessage {
	
	int id;
	
	String message;
	
	String username;
	boolean anonymous=true;
	double lat,lon;
	
	ArrayList<Tag> tags;
	
	
	
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
		this.anonymous=b;
	}
	public boolean getAnonymous(){
		return this.anonymous;
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

	

	

	public void setMsg(String str){
		this.message=str;
	}

	public String getMsg(){
		return this.message;
	}

}



