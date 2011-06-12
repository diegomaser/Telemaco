package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.PlaceVisitDAO;

public class PlaceControl {
	public static Objects readByCity(long city) {
		// TODO: Problem here, where do I get Places from a City? REST?
		//return PlaceDAO.readByCity(id);
		return new Objects();
	}
	
	public static Objects readByTrip(Trip t){
		return PlaceVisitDAO.readByTrip(t);
	}
}