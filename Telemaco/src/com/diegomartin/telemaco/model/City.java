package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class City extends IListItem implements Serializable{
	private static final long serialVersionUID = -6631093769443813504L;
	
	private long id;
	private String name;
	private String description;
	private int timezone; // TODO: Actualizar en el diagrama de clases
	private int countryId;
	
	public City() {}
	
	public City(JSONObject json) {
		try {
			this.id = json.getInt("id");
			this.name = json.getString("name");
			this.description = json.getString("description");
			this.timezone = json.getInt("timezone");
			this.countryId = json.getInt("country");
		} catch (JSONException e) {
			e.printStackTrace();
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

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public List<Place> getPlaces() {
		return null;
	}
}
