package com.diegomartin.telemaco.control;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CityDAO;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.view.ToastFacade;

public class CityControl {
	private static ArrayList<City> cities; 
	
	// Search operations to add a city
	public static ArrayList<City> readCities(Context context, Country country) {
		cities = new ArrayList<City>();
		try{
			String url = RESTResources.getInstance(context).getCitySearchURL(country);
			String content = RestMethod.get(context, url);
			
			try{
				JSONArray arr = new JSONArray(content);
				for(int i=0;i<arr.length();i++){
					City c = new City(arr, context, i);
					cities.add(c);
				}
			}
			catch(JSONException e){
				ToastFacade.show(context, context.getString(R.string.error_parsing));
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, context.getString(R.string.error_connecting));
		}
		
		return cities;
	}
	
	public static ArrayList<City> searchCities(Context context, Country country, String query) {
		cities = new ArrayList<City>();
		try{
			String url = RESTResources.getInstance(context).getCitySearchURL(country, query);
			String content = RestMethod.get(context, url);

			try{
				JSONArray arr = new JSONArray(content);
				for(int i=0;i<arr.length();i++){
					City c = new City((JSONObject) arr.get(i), context);
					cities.add(c);
				}
			}
			catch(JSONException e){
				ToastFacade.show(context, context.getString(R.string.not_found));
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, context.getString(R.string.error_connecting));
		}
		return cities;
	}
	
	public static City getCity(Context context, City city){
		City c = new City();
		try{
			String url = RESTResources.getInstance(context).getCityURL(city);
			String content = RestMethod.get(context, url);
			
			try {
				c = new City(new JSONObject(content), context);
			} catch (JSONException e) {
				ToastFacade.show(context, context.getString(R.string.error_parsing));
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, context.getString(R.string.error_connecting));
		}
		return c;
	}
	
	// Common operations
	public static City read(long city) {
		return CityDAO.read(city);
	}
	
	public static void deleteCityVisit(CityVisit c){
		CityVisitDAO.delete(c);
	}
	
	// UI operations
	public static long createCityVisit(Context context, City city, Trip trip, Date date) {
		// We create the city with all the information we have in the server
		City fullCity = CityControl.getCity(context, city);
		CityDAO.createOrUpdate(fullCity);
		
		// We create the city visit in our trip
		CityVisit c = new CityVisit();
		c.setDate(date);
		c.setCity(fullCity.getId());
		c.setTrip(trip.getId());
		return CityVisitDAO.create(c);
	}
	
	public static ArrayList<CityVisit> readByTrip(Trip t) {
		return CityVisitDAO.readByTrip(t);
	}
	
	public static void setPendingCreate(long id, boolean create){
		CityVisit city = CityVisitDAO.read(id);
		city.setPendingCreate(create);
		CityVisitDAO.update(city);
	}
		
	public static void setPendingUpdate(long id, boolean update){
		CityVisit city = CityVisitDAO.read(id);
		city.setPendingUpdate(update);
		CityVisitDAO.update(city);
	}
	
	public static void setPendingDelete(long id){
		CityVisit city = CityVisitDAO.read(id);
		city.setPendingDelete(true);
		CityVisitDAO.update(city);
	}
}