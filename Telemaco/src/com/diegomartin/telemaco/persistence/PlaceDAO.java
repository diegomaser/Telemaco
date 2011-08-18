package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Place;

public class PlaceDAO {
	private final static String TABLENAME = "Place";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "name", "description", "lat", "lng", "place_type"};
	
	public static long create(Place t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("lat", t.getLat());
			values.put("lng", t.getLng());
			values.put("place_type", String.valueOf(t.getType()));
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Place read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Place place = new Place();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				place.setId(cursor.getLong(0));
				place.setName(cursor.getString(1));
				place.setDescription(cursor.getString(2));
				place.setLat(cursor.getDouble(3));
				place.setLng(cursor.getDouble(4));
				place.setType(cursor.getString(5).charAt(0));
			}
		}
		return place;
	}
	
	public static ArrayList<Place> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Place> places = new ArrayList<Place>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Place place = new Place();
				place.setId(cursor.getLong(0));
				place.setName(cursor.getString(1));
				place.setDescription(cursor.getString(2));
				place.setLat(cursor.getDouble(3));
				place.setLng(cursor.getDouble(4));
				place.setType(cursor.getString(5).charAt(0));
				places.add(place);
			}
		}
		return places;
	}
	
	public static int update(Place p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", p.getName());
			values.put("description", p.getDescription());
			values.put("lat", p.getLat());
			values.put("lng", p.getLng());
			values.put("place_type", String.valueOf(p.getType()));
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(p.getId())});
			db.close();
		}
		return rows;
	}
	
	public static int delete(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			rows = db.delete(TABLENAME, WHERE_CONDITION, new String[] {String.valueOf(id)});
			db.close();
		}
		return rows;
	}
	
	public static int delete(Place p){
		return delete(p.getId());
	}
}
