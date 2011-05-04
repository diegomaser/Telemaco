package com.diegomartin.telemaco.control;

import java.util.Date;
import java.util.List;

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
	
	public static List<Trip> readTrips(){
		return TripDAO.read();
	}
	
	public static void deleteTrip(int id){
		TripDAO.delete(id);
	}
}