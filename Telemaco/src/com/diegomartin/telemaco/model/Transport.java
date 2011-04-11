package com.diegomartin.telemaco.model;

import java.util.Date;

public class Transport {
	private String name;
	private String description;
	private Date date;
	private City origin;
	private City destination;
	
	public Transport() { }

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

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setOrigin(City origin) {
		this.origin = origin;
	}

	public City getOrigin() {
		return origin;
	}

	public void setDestination(City destination) {
		this.destination = destination;
	}

	public City getDestination() {
		return destination;
	}
}
