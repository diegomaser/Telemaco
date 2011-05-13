package com.diegomartin.telemaco.model;

import java.sql.Date;

public class CityVisit {
	private Trip trip;
	private City city;
	private Date date;
	
	public CityVisit(){ }

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
