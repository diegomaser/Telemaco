package com.diegomartin.telemaco.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;

public class CityDAO {
	public static String TABLENAME = "City";
	
	public static Objects read(Country country) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects cities = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "country", "timezone"};
			Cursor cursor = db.query(TABLENAME, columns, "country = ?", new String[] {String.valueOf(country.getId())}, null, null, null);
			while(cursor.moveToNext()){
				City c = new City();
				c.setId(cursor.getInt(0));
				c.setName(cursor.getString(1));
				c.setDescription(cursor.getString(2));
				c.setCountryId(cursor.getInt(3));
				c.setTimezone(cursor.getInt(4));				
				cities.add(c);
			}
		}
		return cities;
	}

	public static Objects read(Country country, String string) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects cities = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "country", "timezone"};
			Cursor cursor = db.query(TABLENAME, columns, "country = ? AND name LIKE ?", new String[] {String.valueOf(country.getId()), '%'+country.getName()+'%'}, null, null, null);
			while(cursor.moveToNext()){
				City c = new City();
				c.setId(cursor.getInt(0));
				c.setName(cursor.getString(1));
				c.setDescription(cursor.getString(2));
				c.setCountryId(cursor.getInt(3));
				c.setTimezone(cursor.getInt(4));
				cities.add(c);
			}
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
			values.put("timezone", c.getTimezone());
			values.put("countryId", c.getCountryId());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
}