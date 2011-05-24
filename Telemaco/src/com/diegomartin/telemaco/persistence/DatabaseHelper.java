package com.diegomartin.telemaco.persistence;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	// TODO: This isn't the best way to ship database with application
	// http://stackoverflow.com/questions/513084/how-to-ship-an-android-application-with-a-database
		
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
		Utils.execute(context, db, "database/createDB.sql");
		Utils.execute(context, db, "database/loadDB.sql");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Utils.execute(context, db, "database/dropDB.sql");
		Utils.execute(context, db, "database/createDB.sql");
		Utils.execute(context, db, "database/loadDB.sql");
	}
	
	public void cleanDatabase(){
		Utils.execute(context, instance.getWritableDatabase(), "database/dropDB.sql");
		Utils.execute(context, instance.getWritableDatabase(), "database/createDB.sql");
		Utils.execute(context, instance.getWritableDatabase(), "database/loadDB.sql");
	}
	
	
	
}