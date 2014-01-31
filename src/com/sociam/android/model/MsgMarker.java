package com.sociam.android.model;

import com.google.android.gms.maps.model.Marker;


/**
 *This class is the object which connect to a marker and receive message. 
 * @author yukki
 *
 */
public class MsgMarker {
	
	private Marker maker;
	private RecieveMessage rm;
	private int index;
	
	/**
	 * Initial setting of the Message marker 
	 * @param m marker object
	 * @param rem RecieveMessage object
	 * @param i index of the array
	 */
	public MsgMarker(Marker m, RecieveMessage rem, int i) {
		maker=m;
		rm=rem;
		index=i;
	}
	
	/**
	 * Return the marker object
	 * @return marker of this MsgMaker
	 * @see com.google.android.gms.maps.model.Marker
	 */
	public Marker getMarker(){
		return this.maker;
	}
	
	/**
	 * Return RecieveMessage object 
	 * 
	 * @return RecieveMessage object
	 *@see com.sociam.android.model.RecieveMessage
	 */
	public RecieveMessage getRecieveMessage(){
		return this.rm;
	}
	
	/**
	 * Return the number of index in the array
	 * @return index number
	 */
	public int getIndex(){
		return this.index;
	}
	
}

