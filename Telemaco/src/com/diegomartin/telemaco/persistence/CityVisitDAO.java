package com.diegomartin.telemaco.persistence;

import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Trip;

public class CityVisitDAO {
	private final static String TABLENAME = "CityVisit";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "trip", "city", "date", "pending_create", "pending_update", "pending_delete"};

	public static long create(CityVisit c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("city", c.getCity());
			values.put("trip", c.getTrip());
			values.put("date", c.getDate().toString());
			
			if(c.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			
			if(c.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			
			if(c.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static CityVisit read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		CityVisit city = new CityVisit();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				city.setId(cursor.getLong(0));
				city.setTrip(cursor.getLong(1));
				city.setCity(cursor.getLong(2));
				city.setDate(Date.valueOf(cursor.getString(3)));
				
				int pendingCreate = cursor.getInt(4);
				int pendingUpdate = cursor.getInt(5);
				int pendingDelete= cursor.getInt(6);
				
				city.setPendingCreate(pendingCreate>0);
				city.setPendingUpdate(pendingUpdate>0);
				city.setPendingDelete(pendingDelete>0);
			}
		}
		return city;
	}
	
	public static ArrayList<CityVisit> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<CityVisit> cities = new ArrayList<CityVisit>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				CityVisit city = new CityVisit();
				city.setId(cursor.getLong(0));
				city.setTrip(cursor.getLong(1));
				city.setCity(cursor.getLong(2));
				city.setDate(Date.valueOf(cursor.getString(3)));
				
				int pendingCreate = cursor.getInt(4);
				int pendingUpdate = cursor.getInt(5);
				int pendingDelete= cursor.getInt(6);
				
				city.setPendingCreate(pendingCreate>0);
				city.setPendingUpdate(pendingUpdate>0);
				city.setPendingDelete(pendingDelete>0);

				cities.add(city);
			}
		}
		return cities;
	}
	
	public static int update(CityVisit c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("city", c.getCity());
			values.put("trip", c.getTrip());
			values.put("date", c.getDate().toString());
			
			if(c.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			
			if(c.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			
			if(c.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(c.getId())});
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
	
	public static int delete(CityVisit c){
		return delete(c.getId());
	}

	public static ArrayList<CityVisit> readByTrip(Trip t) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<CityVisit> cities = new ArrayList<CityVisit>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "trip=? AND pending_delete=0", new String[] {String.valueOf(t.getId())}, null, null, null);
			while(cursor.moveToNext()){
				CityVisit visit = new CityVisit();
				visit.setId(cursor.getLong(0));
				visit.setTrip(cursor.getLong(1));
				visit.setCity(cursor.getLong(2));
				visit.setDate(Date.valueOf(cursor.getString(3)));
				
				int pendingCreate = cursor.getInt(4);
				int pendingUpdate = cursor.getInt(5);
				int pendingDelete= cursor.getInt(6);
				
				visit.setPendingCreate(pendingCreate>0);
				visit.setPendingUpdate(pendingUpdate>0);
				visit.setPendingDelete(pendingDelete>0);

				cities.add(visit);
			}
		}
		return cities;
	}
}
