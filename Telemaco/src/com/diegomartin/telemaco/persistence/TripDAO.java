package com.diegomartin.telemaco.persistence;

import java.util.ArrayList;
import java.util.List;

import com.diegomartin.telemaco.model.Trip;

import android.database.sqlite.SQLiteDatabase;

public class TripDAO {
	public static void create(Trip t){
		SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
		if (db!=null){
			db.execSQL("INSERT INTO Usuarios (codigo, nombre) " + "VALUES (" + 1 + ", '" + "Prueba" +"')");
			db.close();
		}
	}
	
	public static Trip read(int id){
		Trip trip = new Trip();
		return trip;
	}
	
	public static void update(Trip t){
		
	}
	
	public static void delete(Trip t){
		
	}
	
	public static void delete(int id){
		
	}

	public static List<Trip> read() {
		// TODO Auto-generated method stub
		List<Trip> trips = new ArrayList<Trip>();
		return trips;
	}
}
