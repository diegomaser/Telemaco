package com.diegomartin.telemaco.test.unit;

import android.test.AndroidTestCase;

import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.DatabaseHelper;
import com.diegomartin.telemaco.persistence.TripDAO;
import java.sql.Date;

public class TripTest extends AndroidTestCase {
	
	private Trip trip;
	private long id;
	
	public TripTest() {
		super();
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		
		DatabaseHelper.setContext(getContext());
		DatabaseHelper.getInstance().cleanDatabase();
		
		assertEquals(0, TripDAO.read().size());
    	
		this.trip = new Trip();
    	this.trip.setName("Prueba");
    	this.trip.setDescription("Esta es una descripcion de prueba.");
    	this.trip.setStartDate(Date.valueOf("2011-05-14"));
    	this.trip.setEndDate(Date.valueOf("2011-05-20"));
    	this.id = TripDAO.create(this.trip);
    	this.trip.setId(this.id);
    	
    	assertFalse(this.id == -1);
    	assertEquals(1, TripDAO.read().size());
	}
	
	public void testTripUpdate(){
		this.trip.setName("Prueba Actualizacion");
		this.trip.setDescription("Prueba Actualizacion Descripcion");
		this.trip.setStartDate(Date.valueOf("2010-05-14"));
    	this.trip.setEndDate(Date.valueOf("2010-05-20"));
		
		int rows = TripDAO.update(this.trip);
		
		assertEquals(1, rows);
		
		Trip t = TripDAO.read(this.id);
		
		assertEquals(t.getName(), this.trip.getName());
		assertEquals(t.getDescription(), this.trip.getDescription());
		assertEquals(t.getStartDate(), this.trip.getStartDate());
		assertEquals(t.getEndDate(), this.trip.getEndDate());
	}
	
	public void testTripDelete(){
		int rows = TripDAO.delete(this.id);
		assertEquals(1, rows);
		assertEquals(0, TripDAO.read().size());
	}
	
	public void testTripRead(){
		Trip t = TripDAO.read(this.id);
		
		assertEquals(t.getName(), this.trip.getName());
		assertEquals(t.getDescription(), this.trip.getDescription());
		assertEquals(t.getStartDate(), this.trip.getStartDate());
		assertEquals(t.getEndDate(), this.trip.getEndDate());
	}
        
    public void testTripReadAll(){
    	int size = TripDAO.read().size();
    	
    	TripDAO.create(this.trip);
    	
    	assertEquals(size+1, TripDAO.read().size());
    }

	protected void tearDown() throws Exception{
		super.tearDown();
	}
}