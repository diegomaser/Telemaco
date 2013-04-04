package com.diegomartin.telemaco.control;

import java.util.Date;
import java.util.Locale;

import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;

public class ContextControl {

	public static String getCountry(){
		Locale defaultLocale = Locale.getDefault();
		String country = defaultLocale.getCountry();
		return country.toLowerCase();
	}
	
	public static String getLanguage(){
		Locale defaultLocale = Locale.getDefault();
		String lang = defaultLocale.getLanguage();
		return lang.toLowerCase();
	}
	
	public static boolean isDay(long lat, long lng, Date time){
		return false;
	}
	
	public static City getUserCity(){
		return new City();
	}
	
	public static Country getUserCountry(){
		return new Country();
	}
}
