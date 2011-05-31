package com.diegomartin.telemaco.model;

import java.sql.Date;

public class PlaceVisit {
	private long id;
	private long trip;
	private long place;
	private Date date;
	private int order;
	
	public PlaceVisit(){ }
	
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

	public void setPlace(long place) {
		this.place = place;
	}

	public long getPlace() {
		return place;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
