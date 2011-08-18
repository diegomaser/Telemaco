package com.diegomartin.telemaco.persistence;

import java.sql.Date;
import java.util.ArrayList;

import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TripDAO {
	private final static String TABLENAME = "Trip";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "name", "description", "start_date", "end_date", "pending_create", "pending_update", "pending_delete"};

	
	public static long create(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("start_date", t.getStartDate().toString());
			values.put("end_date", t.getEndDate().toString());
			
			if (t.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			if (t.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			if (t.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Trip read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Trip trip = new Trip();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				trip.setId(cursor.getLong(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);
				
				trip.setPendingCreate(pendingCreate>0);
				trip.setPendingUpdate(pendingUpdate>0);
				trip.setPendingDelete(pendingDelete>0);
			}
		}
		return trip;
	}
	
	public static ArrayList<Trip> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Trip> trips = new ArrayList<Trip>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Trip trip = new Trip();
				trip.setId(cursor.getLong(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);
				
				trip.setPendingCreate(pendingCreate>0);
				trip.setPendingUpdate(pendingUpdate>0);
				trip.setPendingDelete(pendingDelete>0);

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

			if (t.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			if (t.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			if (t.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);
			
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

	public static ArrayList<IListItem> readNotDeleted() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<IListItem> trips = new ArrayList<IListItem>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "pending_delete=0", null, null, null, null);
			while(cursor.moveToNext()){
				Trip trip = new Trip();
				trip.setId(cursor.getLong(0));
				trip.setName(cursor.getString(1));
				trip.setDescription(cursor.getString(2));
				trip.setStartDate(Date.valueOf(cursor.getString(3)));
				trip.setEndDate(Date.valueOf(cursor.getString(4)));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);
				
				trip.setPendingCreate(pendingCreate>0);
				trip.setPendingUpdate(pendingUpdate>0);
				trip.setPendingDelete(pendingDelete>0);

				trips.add(trip);
			}
		}
		return trips;
	}
}