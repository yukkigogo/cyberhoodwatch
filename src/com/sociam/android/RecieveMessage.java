package com.sociam.android;

import java.util.ArrayList;

import android.text.format.Time;

public class RecieveMessage {

	int id;
	String message;	
	String username;
	int id_code;
	boolean anonymous;
	double lat,lon;
	Time datetime;
	ArrayList<Tag> tags;
	int up_thumb;
	int down_thumb;

	
	
	public RecieveMessage() {
		tags = new ArrayList<Tag>();
		datetime = new Time();
		
	}
	
	public void setID(int i){
		this.id=i;
	}
	public int getID(){
		return this.id;
	}
	
	public void setIdCode(int i){
		this.id_code=i;
	}
	public int getIdCode(){
		return this.id_code;
	}
	public void setMsg(String str){
		this.message=str;
	}
	
	public String getMsg(){
		return this.message;
	}
	
	public void setUser(String usr){
		username = usr;
	}
	
	public String getUser(){
		return this.username;
	}

	public void setTime(Time t){
		datetime=t;
	}
	public Time getTime(){
		return this.datetime;
	}
	
	public void setAnonymity(boolean b){
		this.anonymous=b;
	}

	public boolean getAnonymity(){
		return this.anonymous;
	}
	
	public void setLat(double l){
		this.lat=l;
	}
	public double getLat(){
		return this.lat;
	}
	public void setLng(double l){
		this.lon=l;
	}
	public double getLng(){
		return this.lon;
	}
	
	public void addTag(Tag t){
		tags.add(t);
	}
	public ArrayList<Tag> getTagList(){
		return this.tags;
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
