package com.sociam.android.model;

import com.google.android.gms.maps.model.Marker;

public class MsgMarker {
	
	private Marker maker;
	private RecieveMessage rm;
	private int index;
	
	public MsgMarker(Marker m, RecieveMessage rem, int i) {
		maker=m;
		rm=rem;
		index=i;
	}
	
	public Marker getMarker(){
		return this.maker;
	}
	
	public RecieveMessage getRecieveMessage(){
		return this.rm;
	}
	
	public int getIndex(){
		return this.index;
	}
	
}

