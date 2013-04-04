package com.diegomartin.telemaco.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.view.ToastFacade;

public class Language {
	private long id;
	private String name;
	private String code;
	
	public Language(){ }
	
	public Language(JSONArray arr, Context context, int i) {
		try{
			JSONObject json = (JSONObject) arr.get(i);
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.code = json.getString("code");
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}
	
	public Language(JSONObject obj, Context context){
		try {
			this.id = obj.getLong("id");
			this.name = obj.getString("name");
			this.code = obj.getString("code");
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
