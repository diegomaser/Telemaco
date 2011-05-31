package com.diegomartin.telemaco.persistence;

import java.sql.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.CityVisit;

public class CityVisitDAO {
	private final static String TABLENAME = "CityVisit";
	private final static String WHERE_CONDITION = "id=?";

	public static long create(CityVisit c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("city", c.getCity());
			values.put("trip", c.getTrip());
			values.put("date", c.getDate().toString());
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static CityVisit read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		CityVisit city = new CityVisit();
		
		if (db!=null){
			String columns[] = {"trip", "city", "date"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				city.setId(id);
				city.setTrip(cursor.getLong(0));
				city.setCity(cursor.getLong(1));
				city.setDate(Date.valueOf(cursor.getString(2)));
			}
		}
		return city;
	}
	
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects cities = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "trip", "city", "date"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				CityVisit city = new CityVisit();
				city.setId(cursor.getLong(1));
				city.setTrip(cursor.getLong(2));
				city.setCity(cursor.getLong(3));
				city.setDate(Date.valueOf(cursor.getString(4)));
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
}
