package com.diegomartin.telemaco.model;

public class Currency {
	private String name;
	private String code;
	private double rate;
	
	public Currency() { }
	
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
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
}
