package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.persistence.CountryDAO;

public class CountryControl {

	public static Country read(long id) {
		return CountryDAO.read(id);
	}

	public static Objects readCountries(){
		return CountryDAO.read();
	}

	public static Objects searchCountries(String countryName) {
		return CountryDAO.read(countryName);
	}

}
