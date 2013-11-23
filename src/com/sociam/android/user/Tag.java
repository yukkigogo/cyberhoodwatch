package com.sociam.android.user;

public class Tag {

	private String name;
	private String category;
	private boolean usersetting;
	
	public Tag(String name, String category) {
		this.name=name;
		this.category=category;
	}

	
	public void setUserSetting(boolean b){
		this.usersetting=b;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public String getCategory(){
		return this.category;
	}
	
	public boolean getUserSetting(){
		return this.usersetting;
	}

}
