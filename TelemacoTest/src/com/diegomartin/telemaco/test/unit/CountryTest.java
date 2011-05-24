package com.diegomartin.telemaco.test.unit;

import com.diegomartin.telemaco.persistence.CountryDAO;
import com.diegomartin.telemaco.persistence.DatabaseHelper;

import android.test.AndroidTestCase;

public class CountryTest extends AndroidTestCase {

	public CountryTest() {
		super();
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		DatabaseHelper.setContext(getContext());
		DatabaseHelper.getInstance().cleanDatabase();
	}
	
	public void testRead(){
		int rows = CountryDAO.read().size();
		assertEquals(193, rows);
	}
	
	public void testSearch(){
		int rows = CountryDAO.read("Bel").size();
		assertEquals(3, rows);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
}
