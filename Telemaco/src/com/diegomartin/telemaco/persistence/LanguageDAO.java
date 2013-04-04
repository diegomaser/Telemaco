package com.diegomartin.telemaco.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diegomartin.telemaco.model.Language;

public class LanguageDAO {
	private final static String TABLENAME = "Language";
	private final static String WHERE_CONDITION = "id=?";
	private final static String columns[] = {"id", "name", "code"};

	public static long create(Language c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		long id = -1;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("code", c.getCode());
			
			id = db.insert(TABLENAME, null, values);
			db.close();
		}
		return id;
	}
	
	public static Language read(long id){
		SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
		Language city = new Language();
		
		if (db!=null){
			Cursor cursor = db.query(TABLENAME, columns, WHERE_CONDITION, new String[] {String.valueOf(id)}, null, null, null);
			if (cursor.moveToNext()){
				city.setId(cursor.getLong(0));
				city.setName(cursor.getString(1));
				city.setCode(cursor.getString(2));
			}
			cursor.close();
		}
		return city;
	}
	
	public static int update(Language c){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		int rows = 0;
		if (db!=null){
			ContentValues values = new ContentValues();
			values.put("name", c.getName());
			values.put("code", c.getCode());
			
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
	
	public static int delete(Language c){
		return delete(c.getId());
	}
	
	public static void createOrUpdate(Language language) {
		Language l = read(language.getId());
		if (l.getName()!=null) update(language);
		else create(language);
	}
}