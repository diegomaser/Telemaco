package com.diegomartin.telemaco.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;

public class CountryDAO {
	private static final String TABLENAME = "Country";
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects countries = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "name", "description"};
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
			String columns[] = {"id", "name", "description"};
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

}
