package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Country;

public class CountryDAO {
	private static final String TABLENAME = "Country";
	private static final String WHERE_CLAUSE = "id=?";
	private static final String columns[] = {"id", "name", "description", "wikipedia_url", "wikitravel_url", "plug_frequency", "plug_voltage", "currency", "plug", "languages"};
	
	// TODO: More languages?
	// TODO: More plugs?
	
	public static ArrayList<Country> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Country> countries = new ArrayList<Country>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Country country = new Country();
				country.setId(cursor.getInt(0));
				country.setName(cursor.getString(1));
				country.setDescription(cursor.getString(2));
				country.setWikipediaURL(cursor.getString(3));
				country.setWikitravelURL(cursor.getString(4));
				country.setPlugFrequency(cursor.getString(5));
				country.setPlugVoltage(cursor.getString(6));
				country.setCurrency(cursor.getLong(7));

				ArrayList<Long> plugs = new ArrayList<Long>();
				plugs.add(cursor.getLong(8));
				country.setPlugs(plugs);
				
				ArrayList<Long> languages = new ArrayList<Long>();
				languages.add(cursor.getLong(9));
				country.setLanguages(languages);
				
				countries.add(country);
			}
			cursor.close();
		}
		return countries;
	}

	public static ArrayList<Country> read(String name) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Country> countries = new ArrayList<Country>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "name LIKE ?", new String[] {'%'+name+'%'}, null, null, null);
			while(cursor.moveToNext()){
				Country country = new Country();
				country.setId(cursor.getInt(0));
				country.setName(cursor.getString(1));
				country.setDescription(cursor.getString(2));
				country.setWikipediaURL(cursor.getString(3));
				country.setWikitravelURL(cursor.getString(4));
				country.setPlugFrequency(cursor.getString(5));
				country.setPlugVoltage(cursor.getString(6));
				country.setCurrency(cursor.getLong(7));

				ArrayList<Long> plugs = new ArrayList<Long>();
				plugs.add(cursor.getLong(8));
				country.setPlugs(plugs);
				
				ArrayList<Long> languages = new ArrayList<Long>();
				languages.add(cursor.getLong(9));
				country.setLanguages(languages);

				countries.add(country);
			}
			cursor.close();
		}
		return countries;
	}

	public static Country read(long id) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Country country = new Country();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CLAUSE, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				country.setId(cursor.getLong(0));
				country.setName(cursor.getString(1));
				country.setDescription(cursor.getString(2));
				country.setWikipediaURL(cursor.getString(3));
				country.setWikitravelURL(cursor.getString(4));
				country.setPlugFrequency(cursor.getString(5));
				country.setPlugVoltage(cursor.getString(6));
				country.setCurrency(cursor.getLong(7));
				
				ArrayList<Long> plugs = new ArrayList<Long>();
				plugs.add(cursor.getLong(8));
				country.setPlugs(plugs);
				
				ArrayList<Long> languages = new ArrayList<Long>();
				languages.add(cursor.getLong(9));
				country.setLanguages(languages);
			}
			cursor.close();
		}
		return country;
	}

	public static long update(Country c) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("description", c.getDescription());
			values.put("wikipedia_url", c.getWikipediaURL());
			values.put("wikitravel_url", c.getWikitravelURL());
			values.put("plug_frequency", c.getPlugFrequency());
			values.put("plug_voltage", c.getPlugVoltage());
			values.put("currency", c.getCurrency());
			
			values.put("plug", c.getPlugs().get(0));
			values.put("languages", c.getLanguages().get(0));
			
			id = db.update(TABLENAME, values, "id=?", new String[] {String.valueOf(c.getId())});
			db.close();
		}
		return id;
	}
}
