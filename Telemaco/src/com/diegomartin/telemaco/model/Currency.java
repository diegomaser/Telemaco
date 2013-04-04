package com.diegomartin.telemaco.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;

public class Currency {
	private long id;
	private String name;
	private String code;
	private double rate;
	
	public Currency() { }
	
	public Currency(JSONObject json, Context context) {
		try {
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.code = json.getString("code");
			this.rate = json.getDouble("rate");
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
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
}