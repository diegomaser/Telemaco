package com.diegomartin.telemaco.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;

public class Plug {
	private long id;
	private String name;
	private String description;
	private String image; // url
	
	public Plug(){ }
	
	public Plug(JSONArray arr, Context context, int i) {
		try{
			JSONObject json = (JSONObject) arr.get(i);
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.image = json.getString("image");
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}
	
	public Plug(JSONObject obj, Context context){
		try {
			this.id = obj.getLong("id");
			this.name = obj.getString("name");
			this.description = obj.getString("description");
			this.image = obj.getString("image");
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	
	public String getImage(){
		return this.image;
	}
}
