package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.PlaceVisit;
import com.diegomartin.telemaco.model.Trip;

public class PlaceVisitDAO {
	private final static String TABLENAME = "PlaceVisit";
	private final static String WHERE_CONDITION = "id=?";
	
	public static long create(PlaceVisit p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", p.getTrip());
			values.put("place", p.getPlace());
			values.put("date", p.getDate().toString());
			values.put("ordenation", p.getOrder());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static PlaceVisit read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		PlaceVisit placeVisit = new PlaceVisit();
		
		if (db!=null){
			String columns[] = {"place", "trip", "date", "ordenation"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				placeVisit.setId(id);
				placeVisit.setPlace(cursor.getLong(0));
				placeVisit.setTrip(cursor.getLong(1));
				placeVisit.setDate(Date.valueOf(cursor.getString(2)));
				placeVisit.setOrder(cursor.getInt(3));
			}
		}
		return placeVisit;
	}
	
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects trips = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "place", "trip", "date", "ordenation"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				PlaceVisit placeVisit = new PlaceVisit();
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
				trips.add(placeVisit);
			}
		}
		return trips;
	}
	
	public static int update(PlaceVisit p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", p.getTrip());
			values.put("place", p.getPlace());
			values.put("date", p.getDate().toString());
			values.put("ordenation", p.getOrder());
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(p.getId())});
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
	
	public static int delete(PlaceVisit t){
		return delete(t.getId());
	}

	public static Objects readByTrip(Trip t) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects list = new Objects();
		if (db!=null){
			String columns[] = {"id", "place", "trip", "date", "ordenation"};
	
			Cursor cursor = db.query(TABLENAME, columns, "trip=?", new String[] {String.valueOf(t.getId())}, null, null, null);
			while(cursor.moveToNext()){
				PlaceVisit placeVisit = new PlaceVisit();
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
				list.add(placeVisit);
			}
		}
		return list;
	}
}
