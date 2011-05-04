package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TripDAO {
	private final static String TABLENAME = "Trip";
	private final static String WHERE_CONDITION = "WHERE id = ?";
	
	public static void create(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("start_date", t.getStartDate().toString());
			values.put("end_date", t.getEndDate().toString());
			db.insert(TABLENAME, null, values);
			db.close();
		}
	}
	
	public static Trip read(int id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Trip trip = new Trip();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "start_date", "end_date"};
			String args[] = {String.valueOf(id)};
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, args, null, null, null);
			if(cursor.moveToNext()){
				trip.setId(cursor.getInt(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
			}
		}
		return trip;
	}
	
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
	
	public static void update(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("start_date", t.getStartDate().toString());
			values.put("end_date", t.getEndDate().toString());
			
			String args[] = {String.valueOf(t.getId())};
			db.update(TABLENAME, values, WHERE_CONDITION, args);
			db.close();
		}
	}
	
	public static void delete(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			String args[] = {String.valueOf(t.getId())};
			db.delete(TABLENAME, WHERE_CONDITION, args);
			db.close();
		}
	}
	
	public static void delete(int id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			String args[] = {String.valueOf(id)};
			db.delete(TABLENAME, WHERE_CONDITION, args);
			db.close();
		}
	}
}