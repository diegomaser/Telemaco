package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Note;
import com.diegomartin.telemaco.model.Trip;

public class NoteDAO {
	private final static String TABLENAME = "Note";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "trip", "name", "text"};
	
	public static long create(Note n){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", n.getTrip());
			values.put("name", n.getName());
			values.put("text", n.getText());
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Note read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Note trip = new Note();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			trip.setId(cursor.getLong(0));
			trip.setTrip(cursor.getLong(1));
			trip.setName(cursor.getString(2));
			trip.setText(cursor.getString(3));
		}
		return trip;
	}
	
	public static ArrayList<Note> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Note note = new Note();
				note.setId(cursor.getLong(0));
				note.setTrip(cursor.getLong(1));
				note.setName(cursor.getString(2));
				note.setText(cursor.getString(3));
				notes.add(note);
			}
		}
		return notes;
	}
	
	public static int update(Note n){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", n.getTrip());
			values.put("name", n.getName());
			values.put("text", n.getText());
			
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(n.getId())});
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

	public static ArrayList<Note> readByTrip(Trip trip) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Note> notes = new ArrayList<Note>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "trip = ?", new String[] {String.valueOf(trip.getId())}, null, null, null);
			while(cursor.moveToNext()){
				Note note = new Note();
				note.setId(cursor.getLong(0));
				note.setTrip(cursor.getLong(1));
				note.setName(cursor.getString(2));
				note.setText(cursor.getString(3));
				notes.add(note);
			}
		}
		return notes;
	}
}
