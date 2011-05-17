package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

public class CountryDAO {
	private static final String TABLENAME = "Country";
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects trips = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "start_date", "end_date"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Trip trip = new Trip();
				trip.setId(cursor.getInt(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				trips.add(trip);
			}
		}
		return trips;
	}

	public static Objects read(String string) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects trips = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "start_date", "end_date"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Trip trip = new Trip();
				trip.setId(cursor.getInt(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				trips.add(trip);
			}
		}
		return trips;
	}

}
