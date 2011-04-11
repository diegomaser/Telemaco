package com.diegomartin.telemaco.model;

public class Note {
	private String name;
	private String text;
	
	public Note() { }
	
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
}
