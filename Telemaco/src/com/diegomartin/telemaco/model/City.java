package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.view.ToastFacade;

public class City extends IListItem implements Serializable{
	private static final long serialVersionUID = -6631093769443813504L;
	
	private long id;
	private String name;
	private String description;
	private int timezone; // TODO: Actualizar en el diagrama de clases
	private int country;
	
	public City() {}
	
	public City(JSONObject json, Context c) {
		try {
			this.id = json.getInt("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.timezone = json.getInt("timezone");
			this.country = json.getInt("country");
		} catch (JSONException e) {
			ToastFacade.show(c, e);
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

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setCountry(int countryId) {
		this.country = countryId;
	}

	public int getCountry() {
		return country;
	}

	public List<Place> getPlaces() {
		return null;
	}
}
