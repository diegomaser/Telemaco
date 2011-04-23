package com.diegomartin.telemaco.model;

import android.database.sqlite.SQLiteDatabase;

public class TripDAO {
	public static void create(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			db.execSQL("INSERT INTO Usuarios (codigo, nombre) " + "VALUES (" + 1 + ", '" + "Prueba" +"')");
			db.close();
		}
	}
	
	public static void read(int id){
		
	}
	
	public static void update(Trip t){
		
	}
	
	public static void delete(Trip t){
		
	}
	
	public static void delete(int id){
		
	}
}
