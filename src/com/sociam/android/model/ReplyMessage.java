package com.sociam.android.model;

import java.util.ArrayList;
import java.util.Calendar;

import android.text.format.Time;

public class ReplyMessage implements Comparable{

	private int id;
	private int parent_id;
	private String message;	
	private String username;
	private int id_code;
	private double lat,lon;
	private Calendar datetime;

	
	
	public ReplyMessage() {
	}
	
	public void setID(int i){
		this.id=i;
	}
	public int getID(){
		return this.id;
	}
	
	public void setParenetId(int i){
		this.parent_id=i;
	}
	
	public int getParentId(){
		return this.parent_id;
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

	public void setTime(Calendar cal){
		datetime=cal;
	}
	public Calendar getTime(){
		return this.datetime;
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
	
	@Override
	public int compareTo(Object obj) {
		return this.datetime.compareTo(((ReplyMessage) obj).getTime());
	}
	
}
