package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.NoteDAO;

public class NoteControl {

	public static Objects read(Trip trip){
		return NoteDAO.read();
	}
}
