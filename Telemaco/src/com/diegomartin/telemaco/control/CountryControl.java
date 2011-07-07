package com.diegomartin.telemaco.control;

import java.util.ArrayList;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.persistence.CountryDAO;

public class CountryControl {

	public static Country read(long id) {
		return CountryDAO.read(id);
	}

	public static ArrayList<Country> readCountries(){
		return CountryDAO.read();
	}

	public static ArrayList<Country> searchCountries(String countryName) {
		return CountryDAO.read(countryName);
	}

}
