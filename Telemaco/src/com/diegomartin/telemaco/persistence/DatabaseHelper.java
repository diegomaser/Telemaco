package com.diegomartin.telemaco.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.diegomartin.telemaco.R;

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
		this.execute("database/createDB.sql");
		this.execute("database/loadDB.sql");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.execute("database/dropDB.sql");
		this.execute("database/createDB.sql");
		this.execute("database/loadDB.sql");
	}
	
	public void cleanDatabase(){
		this.execute("database/dropDB.sql");
		this.execute("database/createDB.sql");
		this.execute("database/loadDB.sql");
	}
	
	
	private void execute(String asset) {
		SQLiteDatabase db = instance.getWritableDatabase();
		BufferedReader in = null; 
		try { 
		    in = new BufferedReader(new InputStreamReader(context.getAssets().open(asset)));
		    String line;  
		    while ((line = in.readLine()) != null)
		    	db.execSQL(line); 
		} catch (IOException e) { } finally {
			if (in!=null)
				try { in.close(); }
				catch (IOException e) { }
		}
	}
}