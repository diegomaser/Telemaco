package com.diegomartin.telemaco.model;

import java.util.ArrayList;

public class Objects{
	private ArrayList<IListItem> list;
	
	public Objects(){
		this.list = new ArrayList<IListItem>();
	}
	
	public void add(IListItem obj){
		this.list.add(obj);
	}
	
	public Object get(int id){
		return this.list.get(id);
	}
	
	public int size(){
		return this.list.size();
	}
	
	public ArrayList<IListItem> getList(){
		return this.list;
	}
	
	public Object[] getArray(){
		return this.list.toArray();
	}
}
