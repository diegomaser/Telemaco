package com.diegomartin.telemaco.model;

import java.sql.Date;

public class PlaceVisit {
	private long id;
	private long trip;
	private long place;
	private Date date;
	private int order;
	private boolean pendingCreate;
	private boolean pendingUpdate;
	private boolean pendingDelete;
	
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
