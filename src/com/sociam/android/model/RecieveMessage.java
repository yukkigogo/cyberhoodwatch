package com.sociam.android.model;

import java.util.ArrayList;
import java.util.Calendar;

import android.text.format.Time;

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
	
	
	public RecieveMessage() {
		tags = new ArrayList<Tag>();
		myreplys = new ArrayList<ReplyMessage>();
	}
	
	public void setReplyMsg(ReplyMessage msg){
		myreplys.add(msg);
	}
	public ArrayList<ReplyMessage> getReplyArrays(){
		return this.myreplys;
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

	public void setTime(Calendar cal){
		datetime=cal;
	}
	public Calendar getTime(){
		return this.datetime;
	}
	
//	public void setAnonymity(boolean b){
//		this.anonymous=b;
//	}
//
//	public boolean getAnonymity(){
//		return this.anonymous;
//	}
	
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
