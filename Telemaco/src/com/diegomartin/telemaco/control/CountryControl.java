package com.diegomartin.telemaco.control;

import java.util.ArrayList;

import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Currency;
import com.diegomartin.telemaco.model.Language;
import com.diegomartin.telemaco.model.Plug;
import com.diegomartin.telemaco.persistence.CountryDAO;
import com.diegomartin.telemaco.persistence.CurrencyDAO;
import com.diegomartin.telemaco.persistence.LanguageDAO;
import com.diegomartin.telemaco.persistence.PlugDAO;

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
	
	public static ArrayList<Plug> getPlugs(Country country) {
		ArrayList<Long> ids = country.getPlugs();
		ArrayList<Plug> plugs = new ArrayList<Plug>();
		
		for (long id: ids){
			Plug plug = PlugDAO.read(id);
			plugs.add(plug);
		}
		return plugs;
	}
	
	public static Currency getCurrency(Country country){
		long id = country.getCurrency();
		Currency currency = CurrencyDAO.read(id);
		return currency;
	}
	
	public static ArrayList<Language> getLanguages(Country country){
		ArrayList<Long> ids = country.getLanguages();
		ArrayList<Language> languages = new ArrayList<Language>();
		
		for (long id: ids){
			Language language = LanguageDAO.read(id);
			languages.add(language);
		}
		return languages;
	}
}
