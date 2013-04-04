package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.GeoFacade;
import com.diegomartin.telemaco.view.ToastFacade;

public class City extends IListItem implements Serializable{
	private static final long serialVersionUID = -6631093769443813504L;
	
	private long id;
	private String name;
	private String description;
	private String wikipediaURL;
	private String wikitravelURL;
	private int timezone;
	private long country;
	private double lat;
	private double lng;
	
	// Fields not corresponding to City table
	private String countryName;
	private Date visitDate;
	
	public City() {}
	
	public City(JSONArray arr, Context context, int i) {
		try{
			JSONObject json = (JSONObject) arr.get(i);
			this.id = json.getLong("id");
			this.name = json.getString("name");
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}
	
	public City(JSONObject json, Context context) {
		try {
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.wikipediaURL = json.getString("wikipedia_url");
			this.wikitravelURL = json.getString("wikitravel_url");
			this.timezone = json.getInt("timezone");
			this.lat = json.getDouble("lat");
			this.lng = json.getDouble("lng");
			
			JSONObject c = json.getJSONObject("country");
			this.country = c.getLong("id");
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setCountry(long countryId) {
		this.country = countryId;
	}

	public long getCountry() {
		return this.country;
	}

	public List<Place> getPlaces() {
		return null;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLng() {
		return lng;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setVisitDate(Date date) {
		this.visitDate = date;
	}

	public Date getVisitDate() {
		return visitDate;
	}
	
	public String getExtra(Context c){
		float distance = GeoFacade.getInstance(c).distanceTo(this.lat, this.lng);
		double m = Math.round(distance / 10) * 10; // round to 10
		double km = Math.round(distance / 100) / 10.0; // round to 0.1
		String extra = "";
		 		
		if (distance>=1000) extra += String.valueOf(km) + " km.";
		else if (distance>=0) extra += String.valueOf(m) + " m.";
		else extra += "??? m.";
		
		if(this.countryName != null) extra += " - " + this.countryName;
		if(this.visitDate != null) extra += " - " + this.visitDate.toLocaleString().split(" ")[0];

		return extra;
	}
	
	public int getImage(){
		//return R.drawable.city;
		return R.drawable.ic_menu_mapmode;
	}
}