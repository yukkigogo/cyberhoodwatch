package com.sociam.android.model;

import java.lang.reflect.Constructor;

/**
 *This class is a model of each tag.
 *
 *Tag is used for messaging system. 
 *Tag has 2 propaties and 2 settings,  <br>
 * Propaties : one is category and another is name. 
 * for example, a tag has category 'general' and name 'female' <br>
 *  Settings : one is usersettings which shows if the user sets this tag as defult, 
 *  another is msg_setting which is used when the user sends a message
 * 
 * @author yukki
 * @version 1
 *
 */
public class Tag {

	private String name;
	private String category;
	private boolean usersetting; // true:user preference false:not
	private boolean msg_setting; // true:messag on false no
	
	/**
	 * {@link Constructor}
	 * @param name name of this tag
	 * @param category category of this tag
	 */
	public Tag(String name, String category) {
		this.name=name;
		this.category=category;
		this.usersetting=false;
		this.msg_setting=false;
	}

	/**
	 * Set if the user set on as default
	 * @param b 'true' if this tag is ON 
	 */
	public void setUserSetting(boolean b){
		this.usersetting=b;
	}
	
	/**
	 * Return the name of tag
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Return the category of tag
	 * @return category
	 */
	public String getCategory(){
		return this.category;
	}
	
	/**
	 * Return whether the user set as default or not
	 * @return 'ture' if the user set as default
	 */
	public boolean getUserSetting(){
		return this.usersetting;
	}

	/**
	 *  Set whether the user preference with a sending message.
	 *  Default setting is the same as usersettings
	 * @param b 'true' if it is ON
	 */
	public void setMsgSetting(boolean b){
		this.msg_setting = b;
	}
	
	/**
	 * Return wether the user preference with a sending message
	 * @return 'true' if it is ON
	 */
	public boolean getMsgSetting(){
		return this.msg_setting;
	}
	
}
