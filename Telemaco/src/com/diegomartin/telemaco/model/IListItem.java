package com.diegomartin.telemaco.model;

import android.content.Context;

import com.diegomartin.telemaco.R;

public abstract class IListItem {
	//private char itemType;

	public abstract long getId();
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getExtra(Context c);
		
	public int getImage(){
		return R.drawable.maleta3;
	}
	
	public String getEntityName(){
		return this.getClass().getSimpleName();
	}
	
	public IListItem getSubobject(IListItem obj){
		return this.getClass().cast(obj);
	}
	
	public String toString(){
		return this.getName();
	}
}
