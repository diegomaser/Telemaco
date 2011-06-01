package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Transport;

public class TransportDAO {
	private final static String TABLENAME = "Transport";
	private final static String WHERE_CONDITION = "id=?";
	
	public static long create(Transport t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", t.getTrip());
			values.put("origin", t.getOrigin());
			values.put("destination", t.getDestination());
			values.put("place", t.getPlace());
			values.put("date", t.getDate().toString());
			values.put("code", t.getCode());
			values.put("reservation", t.getReservation());
			values.put("type", String.valueOf(t.getType()));
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Transport read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Transport transport = new Transport();
		
		if (db!=null){
			String columns[] = {"trip", "origin", "destination", "place", "date", "code", "reservation", "type"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				transport.setId(id);
				transport.setTrip(cursor.getLong(0));
				transport.setOrigin(cursor.getLong(1));
				transport.setDestination(cursor.getLong(2));
				transport.setPlace(cursor.getString(3));
				transport.setDate(Date.valueOf(cursor.getString(4)));
				transport.setCode(cursor.getString(5));
				transport.setReservation(cursor.getString(6));
				transport.setType(cursor.getString(7).charAt(0));
			}
		}
		return transport;
	}
	
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects trips = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "trip", "origin", "destination", "place", "date", "code", "reservation", "type"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Transport transport = new Transport();
				transport.setId(cursor.getLong(0));
				transport.setTrip(cursor.getLong(1));
				transport.setOrigin(cursor.getLong(2));
				transport.setDestination(cursor.getLong(3));
				transport.setPlace(cursor.getString(4));
				transport.setDate(Date.valueOf(cursor.getString(5)));
				transport.setCode(cursor.getString(6));
				transport.setReservation(cursor.getString(7));
				transport.setType(cursor.getString(8).charAt(0));
				trips.add(transport);
			}
		}
		return trips;
	}
	
	public static int update(Transport t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", t.getTrip());
			values.put("origin", t.getOrigin());
			values.put("destination", t.getDestination());
			values.put("place", t.getPlace());
			values.put("date", t.getDate().toString());
			values.put("code", t.getCode());
			values.put("reservation", t.getReservation());
			values.put("transp_type", String.valueOf(t.getType()));

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
	
	public static int delete(Transport t){
		return delete(t.getId());
	}
}
