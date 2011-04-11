package com.diegomartin.telemaco.model;

import java.util.List;

public class User {
	private String username;
	private City city;
	private List<Trip> trips;
	
	public User(){ }
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public City getCity() {
		return city;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public List<Trip> getTrips() {
		return trips;
	}
}
