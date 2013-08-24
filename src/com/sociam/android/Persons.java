package com.sociam.android;

public class Persons {

	private int type; // 1:pects 2:victim
	private int num;
	private String age;
	private String gender;
	private String ethics;
	private String dresscolour;
	private String Text;
	private boolean isText;
	
	
	public void setNum(int id){	
		this.num=id;
	}
	
	public void setAge(String id){	
		this.age=id;
	}

	public void setGender(String id){
		this.gender=id;
	}
	
	public void setEthics(String id){	
		this.ethics=id;
	}

	public void setDressCol(String col){
		this.dresscolour=col;
	}
	
	public void setText(String tex){
		this.Text=tex;
	}
	public void setisText(boolean s){
		this.isText=s;
	}


	
	public int getNum(){
		return this.num;
	}
	
	public String getAge(){
		return this.age;
	}
	
	public String getGender(){
		return this.gender;
	}
	
	public String getEthics(){
		return this.ethics;
	}
	
	public String getDressCol(){
		return this.dresscolour;
	}
	
	public String getText(){
		return this.Text;
	}
	public boolean getisText(){
		return this.isText;
	}
	
	
}
