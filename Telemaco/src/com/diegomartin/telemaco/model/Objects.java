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
	
	public Object get(long id){
		return this.list.get((int) id);
	}
	
	public int size(){
		return this.list.size();
	}
	
	public ArrayList<?> getList(){
		return this.list;
	}
	
	public Object[] getArray(){
		return this.list.toArray();
	}
}
