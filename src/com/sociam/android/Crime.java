package com.sociam.android;

public class Crime {
	
	// IDs
	private int crimeID; //hash()
	private String userID;
	private boolean id_code=true; //true = anonymous 
	
	//picture or video
	private int picOn=0; // 0 null, 1 picture, 2 video
	private String filepath;
	
	// category
	private String category="";
	private boolean is_category_text=false;
	private String category_text;
	
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
	private String date;
	
	//severity
	private int severity; // 1 to 7
	
	
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
	
	
	public void setCategory(String cat){
		this.category=cat;
	}

	public void setisCategoryText(boolean catb){
		this.is_category_text=catb;
	}

	public void setCategoryText(String text){
		this.category_text=text;
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
	public void setDate(String date){
		this.date=date;
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
	
	
	// category
	public String getCategoryText(){
		return this.category_text;
	}
	
	public boolean getisCategory(){
		return this.is_category_text;
	}
	
	public String getCategory(){
		return this.category;
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
	public String getDate(){
		return this.date;
	}
	
	// severity
	public int getSeverity(){
		return this.severity;
	}
	

}
