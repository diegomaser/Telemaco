package com.diegomartin.telemaco.model;

import java.util.List;

public class Trip extends IListItem {
	private String name;
	private String description;
	private List<City> cities;
	private List<Transport> transports;
	private List<Place> places;
	private List<Note> notes;
	
	public Trip() {}

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

	public void setCities(List<City> cities) {
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
	}
}
