package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

public class Trip extends IListItem implements Serializable {
	private static final long serialVersionUID = -5034304037509612937L;
	
	private long id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	
	private boolean pendingDelete;
	private boolean pendingUpdate;
	private boolean pendingCreate;
	private java.util.Date lastUpdated;
	
	/*private List<City> cities;
	private List<Transport> transports;
	private List<Place> places;
	private List<Note> notes;*/
	
	public Trip() {
		this.id = 0;
		this.name = "";
		this.description = "";
		final Calendar c = Calendar.getInstance();
		this.startDate = new Date(c.getTimeInMillis());
		this.endDate = new Date(c.getTimeInMillis());
		this.pendingDelete = false;
		this.pendingUpdate = false;
		this.pendingCreate = false;
	}

	public void setId(long id){
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	/*public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setTransports(List<Transport> transports) {
		this.transports = transports;
	}

	public List<Transport> getTransports() {
		return transports;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Note> getNotes() {
		return notes;
	}*/

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void setPendingDelete(boolean pendingDelete) {
		this.pendingDelete = pendingDelete;
	}

	public boolean isPendingDelete() {
		return pendingDelete;
	}

	public void setPendingUpdate(boolean pendingUpdate) {
		this.pendingUpdate = pendingUpdate;
	}

	public boolean isPendingUpdate() {
		return pendingUpdate;
	}

	public void setPendingCreate(boolean pendingCreate) {
		this.pendingCreate = pendingCreate;
	}

	public boolean isPendingCreate() {
		return pendingCreate;
	}

	public void setLastUpdated(java.util.Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public java.util.Date getLastUpdated() {
		return lastUpdated;
	}

	public String toJSON() throws JSONException{
		  JSONObject obj = new JSONObject();
		  obj.put("id", this.getId());
		  obj.put("name", this.getName());
		  obj.put("description", this.getDescription());
		  obj.put("startDate", this.getStartDate().toString());
		  obj.put("endDate", this.getEndDate().toString());
		  return obj.toString();
	}
}