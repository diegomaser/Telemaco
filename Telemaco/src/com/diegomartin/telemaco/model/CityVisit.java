package com.diegomartin.telemaco.model;

import java.sql.Date;

public class CityVisit {
	private long id;
	private long trip;
	private long city;
	private Date date;
	
	public CityVisit(){ }

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setTrip(long trip) {
		this.trip = trip;
	}

	public long getTrip() {
		return trip;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public long getCity() {
		return city;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
}
