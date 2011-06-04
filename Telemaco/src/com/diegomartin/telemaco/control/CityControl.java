package com.diegomartin.telemaco.control;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.persistence.CountryDAO;

public class CityControl {

	private static Objects cities; 
	
	public static Objects readCountries(){
		return CountryDAO.read();
	}
	
	public static Objects searchCountries(String countryName) {
		return CountryDAO.read(countryName);
	}
	
	public static Objects readCities(Context context, Country country) {
		String url = RESTResources.getInstance(context).getCitySearchURL(country);
		String content = RestMethod.get(url);
		
		cities = new Objects();
		try{
			JSONArray arr = new JSONArray(content);
			for(int i=0;i<arr.length();i++){
				City c = new City((JSONObject) arr.get(i));
				cities.add(c);
			}
		}
		catch(JSONException e){ }
		return cities;
	}
	
	public static Objects searchCities(Context context, Country country, String query) {
		String url = RESTResources.getInstance(context).getCitySearchURL(country, query);
		String content = RestMethod.get(url);
		cities = new Objects();
		
		try{
			JSONArray arr = new JSONArray(content);
			for(int i=0;i<arr.length();i++){
				City c = new City((JSONObject) arr.get(i));
				cities.add(c);
			}
		}
		catch(JSONException e){ }
		return cities;
	}

	public static void addCityVisit(City city, Trip trip, Date date) {
		CityVisit c = new CityVisit();
		c.setDate(date);
		c.setCity(city.getId());
		c.setTrip(trip.getId());
		CityVisitDAO.create(c);
	}
	
	public static Objects readCityVisits(Trip t){
		return CityVisitDAO.readByTrip(t);
	}
	
	public static void deleteCityVisit(CityVisit c){
		CityVisitDAO.delete(c);
	}
}
