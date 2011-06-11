package com.diegomartin.telemaco.model;

import com.diegomartin.telemaco.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class IListItem {
	//private char itemType;

	public abstract long getId();
	public abstract String getName();
	public abstract String getDescription();
		
	public Bitmap getImage(){
		Resources res = Resources.getSystem();
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.maleta2);
		return bmp;
	}
	//public String getCoordenates();
	
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
