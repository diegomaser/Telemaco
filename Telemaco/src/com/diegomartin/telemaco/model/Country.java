package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.view.ToastFacade;

public class Country implements Serializable {
	private static final long serialVersionUID = -3486635525120846575L;
	
	private long id;
	private String name;
	private String description;
	private String wikipediaURL;
	private String wikitravelURL;
	private String plugFrequency;
	private String plugVoltage;
	
	private long currency;
	private ArrayList<Long> plugs;
	private ArrayList<Long> languages;
	
	public Country(){ }
	
	public Country(JSONObject json, Context context) {
		try {
			this.plugs = new ArrayList<Long>();
			this.languages = new ArrayList<Long>();
			
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.wikipediaURL = json.getString("wikipedia_url");
			this.wikitravelURL = json.getString("wikitravel_url");
			this.plugFrequency = json.getString("plug_frequency");
			this.plugVoltage = json.getString("plug_voltage");
			
			JSONObject curr = json.getJSONObject("currency");
			this.currency = curr.getLong("id");
			
			JSONArray arr = json.getJSONArray("plug");
			for(int i=0;i<arr.length();i++){
				JSONObject obj = (JSONObject) arr.get(i);
				this.plugs.add(obj.getLong("id"));
			}
			
			JSONArray arr2 = json.getJSONArray("languages");
			for(int i=0;i<arr2.length();i++){
				JSONObject obj = (JSONObject) arr2.get(i);
				this.languages.add(obj.getLong("id"));
			}
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

	public void setWikipediaURL(String wikipediaURL) {
		this.wikipediaURL = wikipediaURL;
	}

	public String getWikipediaURL() {
		return wikipediaURL;
	}

	public void setWikitravelURL(String wikitravelURL) {
		this.wikitravelURL = wikitravelURL;
	}

	public String getWikitravelURL() {
		return wikitravelURL;
	}

	public void setLanguages(ArrayList<Long> languages) {
		this.languages = languages;
	}

	public ArrayList<Long> getLanguages() {
		return languages;
	}

	public void setPlugs(ArrayList<Long> plugs) {
		this.plugs = plugs;
	}

	public ArrayList<Long> getPlugs() {
		return plugs;
	}

	public void setCurrency(long currency) {
		this.currency = currency;
	}

	public long getCurrency() {
		return currency;
	}
	
	public String toString(){
		return this.name;
	}

	public void setPlugFrequency(String plugFrequency) {
		this.plugFrequency = plugFrequency;
	}

	public String getPlugFrequency() {
		return plugFrequency;
	}

	public void setPlugVoltage(String plugVoltage) {
		this.plugVoltage = plugVoltage;
	}

	public String getPlugVoltage() {
		return plugVoltage;
	}
}
