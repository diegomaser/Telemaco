package com.diegomartin.telemaco.model;

public class Item {
	private long id;
	private long place;
	private String name;
	private String description;
	//private Image image; //TODO: Añadir al diagrama de clases
	
	public Item(){ }
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setPlace(long place) {
		this.place = place;
	}

	public long getPlace() {
		return place;
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
}
