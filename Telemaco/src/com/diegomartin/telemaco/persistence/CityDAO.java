package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;

public class CityDAO {
	private static final String TABLENAME = "City";
	private static final String WHERE_CONDITION = "id=?";
	private static final String columns[] = {"id", "name", "description", "wikipedia_url", "wikitravel_url", "country", "timezone", "lat", "lng"};
	
	public static ArrayList<City> readByCountry(Country country) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<City> cities = new ArrayList<City>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "country = ?", new String[] {String.valueOf(country.getId())}, null, null, null);
			while(cursor.moveToNext()){
				City c = new City();
				c.setId(cursor.getInt(0));
				c.setName(cursor.getString(1));
				c.setDescription(cursor.getString(2));
				c.setWikipediaURL(cursor.getString(3));
				c.setWikitravelURL(cursor.getString(4));
				c.setCountry(cursor.getInt(5));
				c.setTimezone(cursor.getInt(6));
				c.setLat(cursor.getDouble(7));
				c.setLng(cursor.getDouble(8));
				cities.add(c);
			}
			cursor.close();
		}
		return cities;
	}

	public static ArrayList<City> readByCountry(Country country, String string) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<City> cities = new ArrayList<City>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "country = ? AND name LIKE ?", new String[] {String.valueOf(country.getId()), '%'+country.getName()+'%'}, null, null, null);
			while(cursor.moveToNext()){
				City c = new City();
				c.setId(cursor.getInt(0));
				c.setName(cursor.getString(1));
				c.setDescription(cursor.getString(2));
				c.setWikipediaURL(cursor.getString(3));
				c.setWikitravelURL(cursor.getString(4));
				c.setCountry(cursor.getInt(5));
				c.setTimezone(cursor.getInt(6));
				c.setLat(cursor.getDouble(7));
				c.setLng(cursor.getDouble(8));
				cities.add(c);
			}
			cursor.close();
		}
		return cities;
	}

	public static long create(City c) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("id", c.getId());
			values.put("name", c.getName());
			values.put("description", c.getDescription());
			values.put("wikipedia_url", c.getWikipediaURL());
			values.put("wikitravel_url", c.getWikitravelURL());
			values.put("timezone", c.getTimezone());
			values.put("country", c.getCountry());
			values.put("lat", c.getLat());
			values.put("lng", c.getLng());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static long update(City c) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("description", c.getDescription());
			values.put("wikipedia_url", c.getWikipediaURL());
			values.put("wikitravel_url", c.getWikitravelURL());
			values.put("timezone", c.getTimezone());
			values.put("country", c.getCountry());
			values.put("lat", c.getLat());
			values.put("lng", c.getLng());
			id = db.update(TABLENAME, values, "id=?", new String[] {String.valueOf(c.getId())});
			db.close();
		}
		return id;
	}

	public static City read(long city) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		City c = new City();

		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(city)}, null, null, null);
			if(cursor.moveToNext()){
				c.setId(cursor.getLong(0));
				c.setName(cursor.getString(1));
				c.setDescription(cursor.getString(2));
				c.setWikipediaURL(cursor.getString(3));
				c.setWikitravelURL(cursor.getString(4));
				c.setCountry(cursor.getInt(5));
				c.setTimezone(cursor.getInt(6));
				c.setLat(cursor.getDouble(7));
				c.setLng(cursor.getDouble(8));
			}
			cursor.close();
		}
		return c;
	}

	public static void createOrUpdate(City city) {
		City c = read(city.getId());
		if (c.getName()!=null) update(city);
		else create(city);
	}
}