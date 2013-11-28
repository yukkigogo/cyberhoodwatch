package com.sociam.android;

public class Tag {

	private String name;
	private String category;
	private boolean usersetting; // true:user preference false:not
	private boolean msg_setting; // true:messag on false no
	
	public Tag(String name, String category) {
		this.name=name;
		this.category=category;
		this.usersetting=false;
		this.msg_setting=false;
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

	public void setMsgSetting(boolean b){
		this.msg_setting = b;
	}
	
	public boolean getMsgSetting(){
		return this.msg_setting;
	}
	
}
