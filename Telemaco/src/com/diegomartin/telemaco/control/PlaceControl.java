package com.diegomartin.telemaco.control;

import java.util.ArrayList;

import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.PlaceVisit;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.PlaceDAO;
import com.diegomartin.telemaco.persistence.PlaceVisitDAO;

public class PlaceControl {
	public static ArrayList<PlaceVisit> readByCity(long city) {
		// TODO: Problem here, where do I get Places from a City? REST?
		//return PlaceDAO.readByCity(id);
		return new ArrayList<PlaceVisit>();
	}
	
	public static ArrayList<PlaceVisit> readByTrip(Trip t){
		return PlaceVisitDAO.readByTrip(t);
	}

	public static Place read(long id) {
		return PlaceDAO.read(id);
	}
}