package com.diegomartin.telemaco.model;

public class Language {
	private long id;
	private String name;
	private String code;
	
	public Language(){ }
	
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
