package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.NoteDAO;

public class NoteControl {
	public static Objects readByTrip(Trip trip){
		return NoteDAO.readByTrip(trip);
	}
}
