package com.diegomartin.telemaco.model;

import java.io.Serializable;
import java.util.List;

public class Country implements Serializable {
	private static final long serialVersionUID = -3486635525120846575L;
	
	private String name;
	private String description;
	
	private List<Language> languages;
	private Plug plug;
	private Currency currency;
	
	public Country(){ }

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

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setPlug(Plug plug) {
		this.plug = plug;
	}

	public Plug getPlug() {
		return plug;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Currency getCurrency() {
		return currency;
	}
}
