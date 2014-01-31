package com.sociam.android.model;

import java.util.Calendar;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.format.Time;

/**
 * Crime is a model of crime data. Each crime properties
 * 
 * 
 * @author yukki
 *@version 1
 */
public class Crime implements Comparable{
	
	/* who is victim
	 *  tome : something happen to me
	 *  saw : I saw somthing
	 *  help : I need help
	 */
	private String happenwho="NULL"; 
	
	// IDs
	private int crimeID; //hash()
	private String userID;
	private boolean id_code=true; //true = anonymous 
	
	//picture or video
	private int picOn=0; // 0 null, 1 picture, 2 video
	private String filepath;
	private BitmapDrawable pic;
	private Bitmap bitpic;
	
	
	// category
	private String category="Other";
	private int c_code=99; // 0:other, 1:violent 2:Theft, 3: ASB 99:null
	private boolean is_category_text=false;
	private String category_text;
	private boolean cat1def;
	
	

	
	// location
	private boolean location_latlon=false;	
	private boolean isAddress=false;
	private double lat;
	private double lon;
	private String address;

	//distance from users' position
	private Double distancefromhere;
	
	// date and time
	private Time date;
	private Calendar cal;
	private boolean isNow=true;
	private boolean isDateText;
	private String dateText;
	
	//severity
	private int severity=88; // 1 to 4
	
	//up/down thumb
	private int up_thumb=0;
	private int down_thumb=0;
	
	/**
	 * Set the crime id
	 * @param id crime id
	 */
	public void setCrimeID(int id){	
		crimeID=id;
	}
	
	/**
	 * Set User id
	 * @param username username or anonymous id
	 */
	public void setUserID(String username){	
		this.userID=username;
	}
	
	
	/**
	 * Set user id code. 
	 *   
	 * 
	 * @param idcode 'true' means anonymous report. 'false' means pesuedonymous report.
	 */
	public void setidcode(boolean idcode){	
		this.id_code=idcode;
	}
	
	/**
	 * Set picture is available or not
	 * @param id '0' nothing is available, '1' picture available 
	 */
	public void setPicOn(int id){	
		this.picOn=id;
	}
	
	/**
	 * Set the absolute URI of picture. 
	 * @param path URI of the picture
	 */
	public void setFilepath(String path){
		this.filepath=path;
	}
	/**
	 * Set a picture as bitmap and convert to the bitmapdrawable
	 * @param map a bitmap image for this crime
	 */
	public void setBitmap(Bitmap map){
		bitpic=map;
		BitmapDrawable bm = new BitmapDrawable(map);
		this.pic=bm;
	}

	/**
	 * Set crime category as string
	 * @param cat Full category for this crime
	 */
	public void setCategory(String cat){
		this.category=cat;
	}

	/**
	 * Set 'true' if this crime has extra detail for category, default is 'false'.
	 * @param catb 'ture' category has extra detail as text
	 */
	public void setisCategoryText(boolean catb){
		this.is_category_text=catb;
	}

	/**
	 * Set the detail of category
	 * @param text context of extra detail of category
	 */
	public void setCategoryText(String text){
		this.category_text=text;
	}
	
	/**
	 * Helper variable for crime input page.
	 * @param i first category 
	 */
	public void setCategoryCode(int i){
		this.c_code=i;
	}
	/**
	 * Helper variable for crime input page.
	 * @param val already setup the category
	 */
	public void setCat2Def(boolean val){
		this.cat1def=val;
	}
	
	/**
	 * Set the person who report this crime is witness or victim etc.. 
	 * <p>
	 * The parameter can be 4 possibility
	 * 'I saw something', 'I need help' 'something happen to me' or no input 
	 * @param vic 'tome' : somehting happen to me, 'saw': I saw something, 'help':I need help and 'NULL':no input from user.
	 */
	public void setHappenwho(String vic){
		this.happenwho=vic;
	}
	/**
	 *  Returns the person who report this crime is witness or victim etc.. 
	 * @return 'tome' : somehting happen to me, 'saw': I saw something, 'help':I need help and 'NULL':no input from user.
	 */
	public String getHappenwho(){
		return this.happenwho;
	}
	
	/**
	 * Set the location is available with latitude and longitude
	 * @param set 'true' if the location is available with latitude and longitude
	 */
	public void setLocationLatLon(boolean set){
		this.location_latlon=set;
	}
	
	/**
	 * Set latitude of the crime location. 
	 * 
	 * @param lati latitude of the crime location
	 */
	public void setLat(double lati){
		this.lat=lati;
	}
	
	/**
	 * Set longitude of the crime location
	 * @param lont longitude of the crime location
	 */
	public void setLon(double lont){
		this.lon=lont;
	}
	/**
	 * Set the location information has an address 
	 * @param add 'true' if the location information has an address
	 */
	public void setisAddress(boolean add){
		this.isAddress=add;
	}
	
	/**
	 * Set location address context 	
	 * @param str location address
	 */
	public void setAddress(String str){
		this.address=str;
	}
	
	/**
	 * Set distance between the user and the crime location
	 * @param distance miles for distance from the user
	 */
	public void setDistance(double distance){
		this.distancefromhere = distance;
	}
	
	/**
	 * Set the date for the crime (use only for input)
	 * @param date time and date for the crime
	 */
	public void setDate(Time date){
		this.date=date;
	}
	/**
	 * Set 'true' if the date has text description. 'false' is the default value.
	 * @param date 'true' if this crime has description of date
	 */
	public void setIsDateText(boolean date){
		this.isDateText=date;
	}
	
	/**
	 * Set text description of date
	 * @param date text description of text
	 */
	public void setDateText(String date){
		this.dateText=date;
	}
	
	/**
	 *Set if the user choose the incident time as now (Used only report incident) 
	 * @param b 'true' if the incident happens now
	 */
	public void setIsNow(boolean b){
		this.isNow=b;
	}
	
	/**
	 * Set time for the crime.
	 * @param cal time and date of the crime
	 */
	public void setCal(Calendar cal){
		this.cal=cal;
	}
	
	/**
	 * Set severity of the crime 
	 * @param sev severity of this crime
	 */
	public void setSeverity(int sev){
		this.severity=sev;
	}
	
	/**
	 * Set the upvote value. 
	 * @param up value od upvote
	 */
	public void setUpThumb(int up){
		this.up_thumb=up;
	}
	
	/**
	 * Set the downvote value.
	 * @param down value of downvote
	 */
	public void setDownThumb(int down){
		this.down_thumb=down;
	}

	
	/*
	 * Get methods
	 */
	
	/**
	 * Return crime id number
	 * @return number of crime ID 
	 */
	public int getCrimeID(){
		return this.crimeID;
	}
	
	/**
	 * Return reporter's username or anonymous id
	 * @return anonymous id or username
	 */
	public String getUserID(){
		return this.userID;
	}
	
	/**
	 * Return whether this crime is anonymous report or not
	 * @return 'ture' if this crime is anonymous report
	 */
	public boolean getIdCode(){
		return this.id_code;
	}
	
	
	/**
	 * Return whether picture is available or not
	 * @return 'ture' if picture is available
	 */
	public int getPicON(){
		return this.picOn;
	}
	
	/**
	 * Return absolute path to the picture
	 * @return URL or absolute path to the picture
	 */
	public String getFilepath(){
		return this.filepath;
	}
	
	/**
	 *  Return drawable object of the crime picture
	 * @return drawable object of the crime picture
	 */
	public BitmapDrawable getBitmapdrawable(){
		return this.pic;
	}
	
	/**
	 * Return bitmap image of crime picture
	 * @return bitmap image of crime picture
	 */
	public Bitmap getBitmap(){
		return this.bitpic;
	}
	
	
	/**
	 * Return the detail of category
	 * @return the detail of category
	 */
	public String getCategoryText(){
		return this.category_text;
	}
	
	/**
	 * Return if the crimes has extra detail of category or not
	 * @return 'true' if the crime has extra detail of category
	 */
	public boolean getisCategoryText(){
		return this.is_category_text;
	}
	
	/**
	 *  Return context of extra detail of category
	 * @return  context of extra detail of category
	 */
	public String getCategory(){
		return this.category;
	}
	
	/**
	 * Helper variable for crime input page.
	 * @return category code 
	 */
	public int getCategoryCode(){
		return this.c_code;
	}
	
	/**
	 * Helper variable for crime input page.
	 * @return secound category code
	 */
	public boolean getCat2Def(){
		return this.cat1def;
	}
	
	

	/**
	 * Return if this crime has location as latitude and longitude
	 * @return 'true' if this crime has location as latitude and longitude
	 */
	public boolean getLocationLatLng(){
		return this.location_latlon;
	}
	
	/**
	 * Returen if this crime has address or not
	 * @return 'true' is this crime has address
	 */
	public boolean getIsAddress(){
		return this.isAddress;
	}	

	/**
	 * Return latitude of this crime
	 * @return latitude
	 */
	public double getLat(){
		return this.lat;
	}
	
	/**
	 * Return longitude of this crimes
	 * @return longitude 
	 */
	public double getLon(){
		return this.lon;
	}
	
	/**
	 * Return the address of crime 
	 * @return the address of crime
	 */
	public String getAddress(){
		return this.address;
	}
	
	/**
	 *  Return distance between the user and the crime location
	 * @return distance in miles
	 */
	public Double getDistance(){
		return this.distancefromhere;
	}
	
	
	/**
	 * Return the date for the crime (use only for input)
	 * @return date of this crime
	 */
	public Time getDate(){
		return this.date;
	}
	/**
	 * Return if this crime has description of the date
	 * @return 'true' it the description is set
	 */
	public boolean getIsDateText(){
		return this.isDateText;
	}
	
	/**
	 * Return text description of the date
	 * @return date description
	 */
	public String getDateText(){
		return this.dateText;
	}
	
	/**
	 * Return this crime is now or not
	 * @return 'ture' if this crime is now
	 */
	public boolean getIsNow(){
		return this.isNow;
	}
	
	/**
	 * Return the calender object which is set the date and time of this crime
	 * @return date and time of this crime
	 */
	public Calendar getCal(){
		return this.cal;
	}
	
	
	/**
	 * Return the severity of this crime
	 * @return severity of this crime
	 */
	public int getSeverity(){
		return this.severity;
	}
	
	/**
	 * Return the vote value of this crime
	 * @return the number of up vote of this crime
	 */
	public int getUpThumbs(){
		return this.up_thumb;
	}
	
	/**
	 * Return the down vote of this crime 
	 * 
	 * @return the number of donw vote of this crime
	 */
	public int getDownThumb(){
		return this.down_thumb;
	}

	
	/**
	 * compare with distance which is used for Collection.sort
	 */
	@Override
	public int compareTo(Object obj) {
		// sorted by distance
		return this.distancefromhere.compareTo(((Crime) obj).getDistance());
	}

}
