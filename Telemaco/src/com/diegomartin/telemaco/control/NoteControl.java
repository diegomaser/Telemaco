package com.diegomartin.telemaco.control;

import java.util.ArrayList;

import com.diegomartin.telemaco.model.Note;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.NoteDAO;

public class NoteControl {
	public static ArrayList<Note> readByTrip(Trip trip){
		return NoteDAO.readByTrip(trip);
	}
	
	public static void newNote(String name, String text, long trip){
		Note n = new Note();
		n.setName(name);
		n.setText(text);
		n.setTrip(trip);
		NoteDAO.create(n);
	}
	
	public static void updateNote(long id, String name, String text, long trip){
		Note n = new Note();
		n.setId(id);
		n.setName(name);
		n.setText(text);
		n.setTrip(trip);
		NoteDAO.update(n);
	}
}
