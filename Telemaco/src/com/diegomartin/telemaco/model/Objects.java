package com.diegomartin.telemaco.model;

import java.util.ArrayList;

public class Objects{
	private ArrayList<Object> list;
	
	public Objects(){
		this.list = new ArrayList<Object>();
	}
	
	public void add(Object obj){
		this.list.add(obj);
	}
	
	public Object get(int id){
		return this.list.get(id);
	}
	
	public int size(){
		return this.list.size();
	}
	
	public ArrayList<Object> getList(){
		return this.list;
	}
	
	public Object[] getArray(){
		return this.list.toArray();
	}
}
