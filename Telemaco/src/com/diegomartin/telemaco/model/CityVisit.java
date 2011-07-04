package com.diegomartin.telemaco.model;

import java.util.Date;

public class CityVisit {
	private long id;
	private long trip;
	private long city;
	private Date date;
	private boolean pendingCreate;
	private boolean pendingUpdate;
	private boolean pendingDelete;
	
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

	public void setPendingCreate(boolean pendingCreate) {
		this.pendingCreate = pendingCreate;
	}

	public boolean isPendingCreate() {
		return pendingCreate;
	}

	public void setPendingUpdate(boolean pendingUpdate) {
		this.pendingUpdate = pendingUpdate;
	}

	public boolean isPendingUpdate() {
		return pendingUpdate;
	}

	public void setPendingDelete(boolean pendingDelete) {
		this.pendingDelete = pendingDelete;
	}

	public boolean isPendingDelete() {
		return pendingDelete;
	}
}
