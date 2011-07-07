package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Item;

public class ItemDAO {
	private final static String TABLENAME = "Item";
	private final static String WHERE_CONDITION = "id=?";
	
	public static long create(Item t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", t.getName());
			values.put("description", t.getDescription());
			values.put("place", t.getPlace());
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Item read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Item item = new Item();
		
		if (db!=null){
			String columns[] = {"name", "description", "place"};

			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			item.setId(id);
			item.setName(cursor.getString(0));
			item.setDescription(cursor.getString(1));
			item.setPlace(cursor.getLong(2));
		}
		return item;
	}
	
	public static ArrayList<Item> read() {
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		ArrayList<Item> Items = new ArrayList<Item>();
		
		if (db!=null){
			String columns[] = {"id", "name", "description", "place"};
			Cursor cursor = db.query(TABLENAME, columns, null, null, null, null, null);
			while(cursor.moveToNext()){
				Item item = new Item();
				item.setId(cursor.getLong(0));
				item.setName(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				item.setPlace(cursor.getLong(3));
				Items.add(item);
			}
		}
		return Items;
	}
	
	public static int update(Item i){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", i.getName());
			values.put("description", i.getDescription());
			values.put("place", i.getPlace());
			rows = db.update(TABLENAME, values, WHERE_CONDITION, new String[] {String.valueOf(i.getId())});
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
	
	public static int delete(Item p){
		return delete(p.getId());
	}
}
