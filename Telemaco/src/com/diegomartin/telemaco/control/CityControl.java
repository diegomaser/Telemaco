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
import com.diegomartin.telemaco.persistence.CityDAO;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.view.ToastFacade;

public class CityControl {

	private static Objects cities; 
	
	public static Objects readCities(Context context, Country country) {
		String url = RESTResources.getInstance(context).getCitySearchURL(country);
		String content = RestMethod.get(context, url);
		
		cities = new Objects();
		try{
			JSONArray arr = new JSONArray(content);
			for(int i=0;i<arr.length();i++){
				City c = new City((JSONObject) arr.get(i), context);
				cities.add(c);
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
		return cities;
	}
	
	public static Objects searchCities(Context context, Country country, String query) {
		String url = RESTResources.getInstance(context).getCitySearchURL(country, query);
		String content = RestMethod.get(context, url);
		cities = new Objects();
		
		try{
			JSONArray arr = new JSONArray(content);
			for(int i=0;i<arr.length();i++){
				City c = new City((JSONObject) arr.get(i), context);
				cities.add(c);
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, e);
		}
		return cities;
	}
	
	public static City getCity(Context context, City city){
		String url = RESTResources.getInstance(context).getCityURL(city);
		String content = RestMethod.get(context, url);
		City c = null;
		try {
			c = new City(new JSONObject(content), context);
		} catch (JSONException e) {
			ToastFacade.show(context, e);
		}
		return c;
	}

	public static void addCityVisit(Context context, City city, Trip trip, Date date) {
		// We create the city with all the information we have in the server
		City fullCity = CityControl.getCity(context, city);
		CityDAO.createOrUpdate(fullCity);
		
		// We create the city visit in our trip
		CityVisit c = new CityVisit();
		c.setDate(date);
		c.setCity(fullCity.getId());
		c.setTrip(trip.getId());
		CityVisitDAO.create(c);
	}
	
	public static Objects readCityVisits(Trip t){
		return CityVisitDAO.readByTrip(t);
	}
	
	public static void deleteCityVisit(CityVisit c){
		CityVisitDAO.delete(c);
	}

	public static City read(long city) {
		return CityDAO.read(city);
	}

	public static Objects readByTrip(Trip t) {
		Objects visits =  CityVisitDAO.readByTrip(t);
		return visits;
	}
}