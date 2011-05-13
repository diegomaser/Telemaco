package com.diegomartin.telemaco.model;

import java.sql.Date;

public class PlaceVisit {
	private Trip trip;
	private Place place;
	private Date date;
	private int order;
	
	public PlaceVisit(){ }

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Place getPlace() {
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
