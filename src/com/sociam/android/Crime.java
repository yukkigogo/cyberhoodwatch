package com.sociam.android;

public class Crime {
	
	private int crimeID;
	private String category;
	private String suspects;
	private String text;
	private String userID;
	private boolean id_code;
	private double lat;
	private double lon;
	private String date;
	private String filepath;
	
	public void setCrimeID(int id){	
		crimeID=id;
	}
	
	public void setLat(double lati){
		this.lat=lati;
	}
	
	public void setLon(double lont){
		this.lon=lont;
	}
	
	public void setDate(String date){
		this.date=date;
	}
	
	public void setFilepath(String path){
		this.filepath=path;
	}
	
	public int getCrimeID(){
		return this.crimeID;
	}
	
	public double getLat(){
		return this.lat;
	}
	
	public double getLon(){
		return this.lon;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getFilepath(){
		return this.filepath;
	}
}
