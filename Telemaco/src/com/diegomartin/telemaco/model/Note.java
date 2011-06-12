package com.diegomartin.telemaco.model;

import java.io.Serializable;

public class Note extends IListItem implements Serializable {
	private static final long serialVersionUID = -2473527050617676974L;
	
	private long id;
	private long trip;
	private String name;
	private String text;
	
	public Note() { }
	
	public void setTrip(long trip) {
		this.trip = trip;
	}

	public long getTrip() {
		return trip;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	@Override
	public String getDescription() {
		return this.getText();
	}
}
