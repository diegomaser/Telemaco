package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CountryDAO;

public class CityControl {

	public static Objects readCountries(){
		return CountryDAO.read();
	}
	
	public static Objects searchCountries(String countryName) {
		return CountryDAO.read(countryName);
	}
	
	public static Objects readCities(Country c) {
		// TODO Auto-generated method stub
		Objects objs =  new Objects();
		return objs;
	}
	
	public static Objects searchCities(Country country, String query) {
		// TODO Auto-generated method stub
		Objects objs = new Objects();
		return objs;
	}

	public static void addCitytoTrip(City city, Trip trip) {
		// TODO Auto-generated method stub
	}
}
