package com.diegomartin.telemaco.persistence;

import java.sql.Date;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.PlaceVisit;
import com.diegomartin.telemaco.model.Trip;

public class PlaceVisitDAO {
	private final static String TABLENAME = "PlaceVisit";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "place", "trip", "date", "ordenation", "pending_create", "pending_update", "pending_delete"};
	
	public static long create(PlaceVisit p){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("trip", p.getTrip());
			values.put("place", p.getPlace());
			values.put("date", p.getDate().toString());
			values.put("ordenation", p.getOrder());
			
			if(p.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			
			if(p.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			
			if(p.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);

			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static PlaceVisit read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		PlaceVisit placeVisit = new PlaceVisit();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);

				placeVisit.setPendingCreate(pendingCreate>0);
				placeVisit.setPendingUpdate(pendingUpdate>0);
				placeVisit.setPendingDelete(pendingDelete>0);
			}
			cursor.close();
		}
		return placeVisit;
	}
	
	public static PlaceVisit read(long placeId, long tripId){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		PlaceVisit placeVisit = new PlaceVisit();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "place=? AND trip=?", new String[] {String.valueOf(placeId), String.valueOf(tripId)}, null, null, null);
			if (cursor.moveToNext()){
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);

				placeVisit.setPendingCreate(pendingCreate>0);
				placeVisit.setPendingUpdate(pendingUpdate>0);
				placeVisit.setPendingDelete(pendingDelete>0);
			}
			cursor.close();
		}
		return placeVisit;
	}
	
	public static ArrayList<PlaceVisit> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<PlaceVisit> trips = new ArrayList<PlaceVisit>();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, "ordenation ASC");
			while(cursor.moveToNext()){
				PlaceVisit placeVisit = new PlaceVisit();
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
								
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);

				placeVisit.setPendingCreate(pendingCreate>0);
				placeVisit.setPendingUpdate(pendingUpdate>0);
				placeVisit.setPendingDelete(pendingDelete>0);

				trips.add(placeVisit);
			}
			cursor.close();
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
			
			if(p.isPendingCreate()) values.put("pending_create", 1);
			else values.put("pending_create", 0);
			
			if(p.isPendingUpdate()) values.put("pending_update", 1);
			else values.put("pending_update", 0);
			
			if(p.isPendingDelete()) values.put("pending_delete", 1);
			else values.put("pending_delete", 0);
			
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

	public static ArrayList<PlaceVisit> readByTrip(Trip t) {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<PlaceVisit> list = new ArrayList<PlaceVisit>();
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, "trip=? AND pending_delete=0", new String[] {String.valueOf(t.getId())}, null, null, "ordenation ASC");
			while(cursor.moveToNext()){
				PlaceVisit placeVisit = new PlaceVisit();
				placeVisit.setId(cursor.getLong(0));
				placeVisit.setPlace(cursor.getLong(1));
				placeVisit.setTrip(cursor.getLong(2));
				placeVisit.setDate(Date.valueOf(cursor.getString(3)));
				placeVisit.setOrder(cursor.getInt(4));
				
				int pendingCreate = cursor.getInt(5);
				int pendingUpdate = cursor.getInt(6);
				int pendingDelete= cursor.getInt(7);
				
				placeVisit.setPendingCreate(pendingCreate>0);
				placeVisit.setPendingUpdate(pendingUpdate>0);
				placeVisit.setPendingDelete(pendingDelete>0);
				
				list.add(placeVisit);
			}
			cursor.close();
		}
		return list;
	}
}
