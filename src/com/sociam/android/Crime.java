package com.sociam.android;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.format.Time;

public class Crime {
	
	// IDs
	private int crimeID; //hash()
	private String userID;
	private boolean id_code=true; //true/1 = anonymous 
	
	//picture or video
	private int picOn=0; // 0 null, 1 picture, 2 video
	private String filepath;
	private BitmapDrawable pic;
	
	
	// category
	private String category="";
	private int c_code=99; // 0:other, 1:violent 2:Theft, 3: ASB 99:null
	private boolean is_category_text=false;
	private String category_text;
	private boolean cat1def;
	
	// suspects
	private boolean uSeeSuspects;
	
	private Persons suspects;
	
	// victims 
	private boolean uVictim;
	private Persons victimes;
	
	
	// location
	private boolean location_latlon=true;	
	private boolean isAddress=false;
	private double lat;
	private double lon;
	private String address;

	// date and time
	private Time date;
	private boolean isNow=true;
	private boolean isDateText;
	private String dateText;
	
	//severity
	private int severity=88; // 1 to 4
	
	
	public void setCrimeID(int id){	
		crimeID=id;
	}
	
	public void userID(String id){	
		this.userID=id;
	}
	
	public void setidcode(boolean id){	
		this.id_code=id;
	}
	
	public void setPicOn(int id){	
		this.picOn=id;
	}
	
	public void setFilepath(String path){
		this.filepath=path;
	}
	public void setBitmap(Bitmap map){
		BitmapDrawable bm = new BitmapDrawable(map);
		this.pic=bm;
	}

	
	
	
	public void setCategory(String cat){
		this.category=cat;
	}

	public void setisCategoryText(boolean catb){
		this.is_category_text=catb;
	}

	public void setCategoryText(String text){
		this.category_text=text;
	}
	public void setCategoryCode(int i){
		this.c_code=i;
	}
	public void setCat2Def(boolean val){
		this.cat1def=val;
	}
	
	// suspects
	public void setSuspects(Persons sus){
		this.suspects=sus;
	}
	
	public void setUseeSuspects(boolean id){	
		this.uSeeSuspects=id;
	}

	
	// victim	
	public void setUvictim(boolean vic){
		this.uVictim=vic;
	}
	
	public void setVictim(Persons vics){
		this.victimes=vics;
	}
	
	// location
	public void setLocationLatLon(boolean set){
		this.location_latlon=set;
	}
	
	public void setLat(double lati){
		this.lat=lati;
	}
	
	public void setLon(double lont){
		this.lon=lont;
	}
	
	public void setisAddress(boolean add){
		this.isAddress=add;
	}
	
	public void setAddress(String str){
		this.address=str;
	}
	
	// date
	public void setDate(Time date){
		this.date=date;
	}
	public void setIsDateText(boolean date){
		this.isDateText=date;
	}
	public void setDateText(String date){
		this.dateText=date;
	}
	public void setIsNow(boolean b){
		this.isNow=b;
	}
	
	//severity
	public void setSeverity(int sev){
		this.severity=sev;
	}

	/*
	 * Get methods
	 */
	
	//ids 
	public int getCrimeID(){
		return this.crimeID;
	}
	
	public String getUserID(){
		return this.userID;
	}
	
	
	public boolean getIdCode(){
		return this.id_code;
	}
	
	
	// picture
	public int getPicON(){
		return this.picOn;
	}
	
	public String getFilepath(){
		return this.filepath;
	}
	public BitmapDrawable getBitmapdrawable(){
		return this.pic;
	}
	
	
	
	// category
	public String getCategoryText(){
		return this.category_text;
	}
	
	public boolean getisCategoryText(){
		return this.is_category_text;
	}
	
	public String getCategory(){
		return this.category;
	}
	public int getCategoryCode(){
		return this.c_code;
	}
	public boolean getCat2Def(){
		return this.cat1def;
	}
	
	// suspects
	public Persons getSuspects(){
		return this.suspects;
	}
	
	public boolean getUseeSuspects(){
		return this.uSeeSuspects;
	}
	
	// victims
	public boolean getUVictim(){
		return this.uVictim;
	}
	public Persons getVictim(){
		return this.victimes;
	}
	

	//location
	public boolean getLocationLatLng(){
		return this.location_latlon;
	}
	
	public boolean getIsAddress(){
		return this.isAddress;
	}	

	public double getLat(){
		return this.lat;
	}
	
	public double getLon(){
		return this.lon;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	// date and time
	public Time getDate(){
		return this.date;
	}
	public boolean getIsDateText(){
		return this.isDateText;
	}
	public String getDateText(){
		return this.dateText;
	}
	public boolean getIsNow(){
		return this.isNow;
	}
	
	// severity
	public int getSeverity(){
		return this.severity;
	}
	

}
