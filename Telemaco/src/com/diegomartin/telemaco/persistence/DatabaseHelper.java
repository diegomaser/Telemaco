package com.diegomartin.telemaco.persistence;

import com.diegomartin.telemaco.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static String CREATE_TABLES = "CREATE TABLE Trip (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, start_date TEXT, end_date TEXT);";
	private final static String DROP_TABLES = "DROP TABLE IF EXISTS Trip";
	
	private static DatabaseHelper instance;
	private static Context context;
	
	private DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version); 
	}
	
	public static DatabaseHelper getInstance(){
		if (instance == null) instance = new DatabaseHelper(context, context.getString(R.string.dbname), null, 1);
		return instance;
	}
	
	public static void setContext(Context c){
		context = c; 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLES);
		db.execSQL(CREATE_TABLES);
	}
	
	public void cleanDatabase(){
		instance.getWritableDatabase().execSQL(DROP_TABLES);
		instance.getWritableDatabase().execSQL(CREATE_TABLES);
	}
}