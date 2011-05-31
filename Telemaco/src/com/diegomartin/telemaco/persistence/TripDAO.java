package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TripDAO {
	private final static String TABLENAME = "Trip";
	private final static String WHERE_CONDITION = "id=?";
	
	public static long create(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("start_date", t.getStartDate().toString());
			values.put("end_date", t.getEndDate().toString());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Trip read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Trip trip = new Trip();
		
		if (db!=null){
			String columns[] = {"name", "description", "start_date", "end_date"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				trip.setId(id);
				trip.setName(cursor.getString(0));
				trip.setDescription(cursor.getString(1));
				trip.setStartDate(Date.valueOf(cursor.getString(2)));
				trip.setEndDate(Date.valueOf(cursor.getString(3)));
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
				trip.setId(cursor.getLong(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				trips.add(trip);
			}
		}
		return trips;
	}
	
	public static int update(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("start_date", t.getStartDate().toString());
			values.put("end_date", t.getEndDate().toString());
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(t.getId())});
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
	
	public static int delete(Trip t){
		return delete(t.getId());
	}
}