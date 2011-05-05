package com.diegomartin.telemaco.control;

import java.sql.Date;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

import com.diegomartin.telemaco.persistence.TripDAO;

public class TripControl {
	public static void newTrip(String name, String description, Date startDate, Date endDate){
		Trip trip = new Trip();
		trip.setName(name);
		trip.setDescription(description);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		TripDAO.create(trip);
	}
	
	public static void updateTrip(int id, String name, String description, Date startDate, Date endDate){
		Trip trip = new Trip();
		trip.setName(name);
		trip.setDescription(description);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		TripDAO.update(trip);
	}
	
	public static Trip readTrip(int id){
		return TripDAO.read(id);
	}
	
	public static Objects readTrips(){
		return TripDAO.read();
	}
	
	public static void deleteTrip(long id){
		TripDAO.delete(id);
	}
}