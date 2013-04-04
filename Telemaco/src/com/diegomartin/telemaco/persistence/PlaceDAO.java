package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.diegomartin.telemaco.model.Place;

public class PlaceDAO {
	private final static String TABLENAME = "Place";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "name", "city", "description", "lat", "lng", "wikipedia_url", "recommended"};
	
	public static long create(Place t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = create(t, db);
		db.close();
		return id;
	}
	
	public static long create(Place t, SQLiteDatabase db){
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("id", t.getId());
			values.put("name", t.getName());
			values.put("city", t.getCity());
			values.put("description", t.getDescription());
			values.put("lat", t.getLat());
			values.put("lng", t.getLng());
			values.put("wikipedia_url", t.getWikipediaURL());
			
			if (t.isRecommended()) values.put("recommended", 1);
			else values.put("recommended", 0);
			
			id = db.insert(TABLENAME, null, values);
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
				place.setCity(cursor.getLong(2));
				place.setDescription(cursor.getString(3));
				place.setLat(cursor.getDouble(4));
				place.setLng(cursor.getDouble(5));
				place.setWikipediaURL(cursor.getString(6));
				
				int recommended = cursor.getInt(7);
				place.setRecommended(recommended > 0);
			}
			cursor.close();
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
				place.setCity(cursor.getLong(2));
				place.setDescription(cursor.getString(3));
				place.setLat(cursor.getDouble(4));
				place.setLng(cursor.getDouble(5));
				place.setWikipediaURL(cursor.getString(6));
				
				int recommended = cursor.getInt(7);
				place.setRecommended(recommended > 0);
				places.add(place);
			}
			cursor.close();
		}
		return places;
	}
	
	public static ArrayList<Place> readByCity(long cityId) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Place> places = new ArrayList<Place>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "city=?", new String[] {String.valueOf(cityId)}, null, null, null);
			while(cursor.moveToNext()){
				Place place = new Place();
				place.setId(cursor.getLong(0));
				place.setName(cursor.getString(1));
				place.setCity(cursor.getLong(2));
				place.setDescription(cursor.getString(3));
				place.setLat(cursor.getDouble(4));
				place.setLng(cursor.getDouble(5));
				place.setWikipediaURL(cursor.getString(6));
				
				int recommended = cursor.getInt(7);
				place.setRecommended(recommended > 0);
				places.add(place);
			}
			cursor.close();
		}
		return places;
	}
	
	public static int update(Place p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = update(p, db);
		db.close();
		return rows;
	}
	
	public static int update(Place p, SQLiteDatabase db){
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", p.getName());
			values.put("city", p.getCity());
			values.put("description", p.getDescription());
			values.put("lat", p.getLat());
			values.put("lng", p.getLng());
			values.put("wikipedia_url", p.getWikipediaURL());
			
			if (p.isRecommended()) values.put("recommended", 1);
			else values.put("recommended", 0);
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(p.getId())});
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
	
	public static void createOrUpdate(ArrayList<Place> places){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		try{
			db.beginTransaction();
			for (Place place: places){
				Place p = read(place.getId());
				if (p.getName()!=null) update(place, db);
				else create(place, db);
			}
			db.setTransactionSuccessful();
		}
		catch (Exception e){
			Log.e("PlaceDAO createOrUpdate", e.getMessage());
		}
		finally{
			db.endTransaction();
			db.close();
		}
	}
	
	public static void createOrUpdate(Place place) {
		Place p = read(place.getId());
		if (p.getName()!=null) update(place);
		else create(place);
	}
}