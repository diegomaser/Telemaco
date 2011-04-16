package com.diegomartin.telemaco.model;

import java.util.List;

import android.graphics.Bitmap;

public class City extends IListItem {
	private String name;
	private String description;
	private int timezone; // TODO: Actualizar en el diagrama de clases
	private Country country;
	
	public City() {}
	
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

	public void setCountry(Country country) {
		this.country = country;
	}

	public Country getCountry() {
		return country;
	}

	public List<Place> getPlaces() {
		return null;
	}
}
