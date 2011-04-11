package com.diegomartin.telemaco.model;

public class Item {
	private String name;
	private String description;
	//private Image image; //TODO: Añadir al diagrama de clases
	
	public Item(){ }
	
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
