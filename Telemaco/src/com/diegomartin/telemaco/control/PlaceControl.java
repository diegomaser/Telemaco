package com.diegomartin.telemaco.control;

import java.sql.Date;
import java.util.ArrayList;

import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.PlaceVisit;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.PlaceDAO;
import com.diegomartin.telemaco.persistence.PlaceVisitDAO;

public class PlaceControl {
	public static Place read(long id) {
		return PlaceDAO.read(id);
	}
	
	public static ArrayList<Place> readByCity(long city) {		
		return PlaceDAO.readByCity(city);
	}
	
	public static ArrayList<PlaceVisit> readByTrip(Trip t){
		return PlaceVisitDAO.readByTrip(t);
	}

	public static void createOrUpdate(ArrayList<Place> places) {
		PlaceDAO.createOrUpdate(places);
	}
	
	public static void createVisit(Place place, Trip trip, Date date){
		PlaceVisit p = new PlaceVisit();
		p.setPlace(place.getId());
		p.setTrip(trip.getId());
		p.setDate(date);
		p.setOrder(-1);
		PlaceVisitDAO.create(p);
	}

	public static void setPendingDelete(long id) {
		PlaceVisit p = PlaceVisitDAO.read(id);
		p.setPendingDelete(true);
		PlaceVisitDAO.update(p);
	}

	public static void updateVisit(long placeId, long tripId, int order) {
		PlaceVisit visit = PlaceVisitDAO.read(placeId, tripId);
		visit.setOrder(order);
		PlaceVisitDAO.update(visit);
	}
}