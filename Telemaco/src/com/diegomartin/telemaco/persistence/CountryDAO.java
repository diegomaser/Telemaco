package com.diegomartin.telemaco.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;

public class CountryDAO {
	private static final String TABLENAME = "Country";
	private static final String WHERE_CLAUSE = "id=?";
	private static final String columns[] = {"id", "name", "description"};
	
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects countries = new Objects();
		
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

	public static Objects read(String name) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects countries = new Objects();
		
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
