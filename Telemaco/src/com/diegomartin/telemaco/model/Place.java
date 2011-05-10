package com.diegomartin.telemaco.model;

import java.util.List;

public class Place extends IListItem{
	private long id;
	private String name;
	private String description;
	private double lat;
	private double lng;
	private char type; // puede ser tambien un integer
	private List<Item> items;
	
	public Place(){ }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLng() {
		return lng;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getType() {
		return type;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}
	
}
