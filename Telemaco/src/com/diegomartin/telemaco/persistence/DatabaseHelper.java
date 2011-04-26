package com.diegomartin.telemaco.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "TelemacoDB";
	private final static String CREATE_TABLES = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)";
	private final static String DROP_TABLES = "DROP TABLE IF EXISTS Usuarios";
	private static DatabaseHelper instance;
	
	private Context context;
	
	private DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	public static DatabaseHelper getInstance(){
		if (instance == null) instance = new DatabaseHelper(this.context, DATABASE_NAME, null, 1);
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLES);
	}
}