package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Country;

public class CountryDAO {
	private static final String TABLENAME = "Country";
	private static final String WHERE_CLAUSE = "id=?";
	private static final String columns[] = {"id", "name", "description"};
	
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
				countries.add(country);
			}
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
				countries.add(country);
			}
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
			}
		}
		return country;
	}

}
