package com.diegomartin.telemaco.model;

import java.util.Date;

public class Transport {
	private long id;
	
	private String place;
	private String code;
	private String reservation;
	private char type;
	
	private Date date;
	private long trip;
	private long origin;
	private long destination;
	
	public Transport() { }

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPlace() {
		return place;
	}

	public long getId() {
		return id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setOrigin(long origin) {
		this.origin = origin;
	}

	public long getOrigin() {
		return origin;
	}

	public void setDestination(long destination) {
		this.destination = destination;
	}

	public long getDestination() {
		return destination;
	}

	public void setTrip(long trip) {
		this.trip = trip;
	}

	public long getTrip() {
		return trip;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getReservation() {
		return reservation;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getType() {
		return type;
	}
}
