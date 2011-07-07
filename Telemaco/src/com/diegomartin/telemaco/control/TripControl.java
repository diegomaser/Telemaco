package com.diegomartin.telemaco.control;

import java.sql.Date;
import java.util.ArrayList;

import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.TripDAO;

public class TripControl {
	// Common operations
	public static long createTrip(String name, String description, Date startDate, Date endDate){
		Trip trip = new Trip();
		trip.setName(name);
		trip.setDescription(description);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		
		trip.setPendingCreate(false);
		trip.setPendingUpdate(false);
		trip.setPendingDelete(false);
		
		return TripDAO.create(trip);
	}
	
	public static Trip readTrip(long id){
		return TripDAO.read(id);
	}
	
	public static ArrayList<Trip> readAll(){
		return TripDAO.read();
	}
	
	public static void updateTrip(long id, String name, String description, Date startDate, Date endDate){
		Trip trip = new Trip();
		trip.setId(id);
		trip.setName(name);
		trip.setDescription(description);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		
		TripDAO.update(trip);
	}
	
	public static void delete(long id){
		TripDAO.delete(id);
	}
	
	// IU operations
	public static void setPendingCreate(long id, boolean create){
		Trip trip = TripDAO.read(id);
		trip.setPendingCreate(create);
		TripDAO.update(trip);
	}
	
	public static ArrayList<IListItem> readNotDeleted(){
		return TripDAO.readNotDeleted();
	}
	
	public static void setPendingUpdate(long id, boolean update){
		Trip trip = TripDAO.read(id);
		trip.setPendingUpdate(update);
		TripDAO.update(trip);
	}
	
	public static void setPendingDelete(long id){
		Trip trip = TripDAO.read(id);
		trip.setPendingDelete(true);
		TripDAO.update(trip);
	}
}