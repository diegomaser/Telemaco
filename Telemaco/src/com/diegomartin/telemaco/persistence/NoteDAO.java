package com.diegomartin.telemaco.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Note;

public class NoteDAO {
	private final static String TABLENAME = "Note";
	private final static String WHERE_CONDITION = "id=?";
	
	public static long create(Note t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", t.getTrip());
			values.put("name", t.getName());
			values.put("text", t.getText());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Note read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Note trip = new Note();
		
		if (db!=null){
			String columns[] = {"trip", "name", "text"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if(cursor.moveToNext()){
				trip.setId(id);
				trip.setTrip(cursor.getLong(0));
				trip.setName(cursor.getString(1));
				trip.setText(cursor.getString(2));
			}
		}
		return trip;
	}
	
	public static Objects read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Objects trips = new Objects();
		
		if (db!=null){
			String columns[] = {"id", "trip", "name", "text"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Note trip = new Note();
				trip.setId(cursor.getLong(0));
				trip.setTrip(cursor.getLong(1));
				trip.setName(cursor.getString(2));
				trip.setText(cursor.getString(3));
				trips.add(trip);
			}
		}
		return trips;
	}
	
	public static int update(Note t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", t.getTrip());
			values.put("name", t.getName());
			values.put("text", t.getText());
			
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
	
	public static int delete(Note t){
		return delete(t.getId());
	}
}
