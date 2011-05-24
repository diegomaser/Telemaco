package com.diegomartin.telemaco.test.unit;

import android.test.AndroidTestCase;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.persistence.CityDAO;
import com.diegomartin.telemaco.persistence.DatabaseHelper;

public class CityTest extends AndroidTestCase {

	private Country country;

	public CityTest() {
		super();
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		DatabaseHelper.setContext(getContext());
		DatabaseHelper.getInstance().cleanDatabase();
		
		this.country = new Country();
		this.country.setName("Spain");
	}
	
	public void testRead(){
		int rows = CityDAO.read(this.country).size();
		assertEquals(3, rows);
	}
	
	public void testSearch(){
		int rows = CityDAO.read(this.country, "Mad").size();
		assertEquals(1, rows);
	}
	
	protected void tearDown() throws Exception{
		super.tearDown();
	}
}
