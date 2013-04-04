package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.GeoFacade;
import com.diegomartin.telemaco.view.ToastFacade;

public class Place extends IListItem implements Serializable {

	private static final long serialVersionUID = 4585829075994327888L;
	
	private long id;
	private String name;
	private long city;
	private String description;
	private double lat;
	private double lng;
	private String wikipediaURL;
	private boolean recommended;
	
	// Field not corresponding to Place table
	private String cityName;
	private Date visitDate;
	
	public Place(){ }
	
	public Place(JSONArray arr, Context context, int i) {
		try{
			JSONObject json = (JSONObject) arr.get(i);
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.wikipediaURL = json.getString("wikipedia_url");
			this.lat = json.getDouble("lat");
			this.lng = json.getDouble("lng");
			this.city = json.getLong("city_id");
			this.setRecommended(false);
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
	}
	
	public Place(JSONObject json, Context context) {
		try {
			this.id = json.getLong("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.wikipediaURL = json.getString("wikipedia_url");
			this.lat = json.getDouble("lat");
			this.lng = json.getDouble("lng");
			this.city = json.getLong("city_id");
			this.setRecommended(false);
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

	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}

	public boolean isRecommended() {
		return recommended;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public long getCity() {
		return city;
	}

	public void setWikipediaURL(String wikipediaURL) {
		this.wikipediaURL = wikipediaURL;
	}

	public String getWikipediaURL() {
		return wikipediaURL;
	}
	
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getVisitDate() {
		return visitDate;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}
	
	public String getExtra(Context c){
		float distance = GeoFacade.getInstance(c).distanceTo(this.lat, this.lng);
		double m = Math.round(distance / 10) * 10; // round to 10
		double km = Math.round(distance / 100) / 10.0; // round to 0.1
		String extra = "";
		 		
		if (distance>=1000) extra += String.valueOf(km) + " km.";
		else if (distance>=0) extra += String.valueOf(m) + " m.";
		else extra += "??? m.";
		
		if(this.cityName != null) extra += " - " + this.cityName;
		if(this.visitDate != null) extra += " - " + this.visitDate.toLocaleString().split(" ")[0];
		
		return extra;
	}
	
	public int getImage(){
		if (this.recommended) return R.drawable.ic_menu_star;
		else return R.drawable.place;
	}
}