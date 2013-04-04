package com.diegomartin.telemaco.control;

import java.util.ArrayList;
import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Currency;
import com.diegomartin.telemaco.model.Language;
import com.diegomartin.telemaco.model.Plug;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CityDAO;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.persistence.CountryDAO;
import com.diegomartin.telemaco.persistence.CurrencyDAO;
import com.diegomartin.telemaco.persistence.LanguageDAO;
import com.diegomartin.telemaco.persistence.PlugDAO;
import com.diegomartin.telemaco.view.ToastFacade;

public class CityControl {
	private static ArrayList<City> cities; 
	
	// Search operations to add a city
	public static ArrayList<City> searchCities(Context context, Country country) {
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
				ToastFacade.show(context, context.getString(R.string.not_found));
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
					City c = new City(arr, context, i);
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
	
	public static City createCity(Context context, City c){
		City city = new City();
		Country country = new Country();
		Currency currency = new Currency();
		Language language = new Language();
		Plug plug = new Plug();
		
		try{
			String url = RESTResources.getInstance(context).getCityURL(c);
			String content = RestMethod.get(context, url);
			
			try {
				JSONObject json = new JSONObject(content);
				city = new City(json, context);
				CityDAO.createOrUpdate(city);
				
				JSONObject jsonCountry = json.getJSONObject("country");
				JSONObject jsonCurrency = jsonCountry.getJSONObject("currency");
				
				JSONArray jsonPlugs = jsonCountry.getJSONArray("plug");
				JSONArray jsonLanguages = jsonCountry.getJSONArray("languages");
				
				country = new Country(jsonCountry, context);
				CountryDAO.update(country);
				
				currency = new Currency(jsonCurrency, context);
				CurrencyDAO.createOrUpdate(currency);

				for(int i=0;i<jsonPlugs.length();i++){
					plug = new Plug(jsonPlugs, context, i);
					PlugDAO.createOrUpdate(plug);
				}
				
				for(int i=0;i<jsonLanguages.length();i++){
					language = new Language(jsonLanguages, context, i);
					LanguageDAO.createOrUpdate(language);
				}
				
			} catch (JSONException e) {
				ToastFacade.show(context, context.getString(R.string.error_parsing));
			}
		}
		catch(JSONException e){
			ToastFacade.show(context, context.getString(R.string.error_connecting));
		}
		
		return city;
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
		City fullCity = CityControl.createCity(context, city);
		
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
	
	public static ArrayList<City> getCities() {
    	ArrayList<Trip> trips = TripControl.readAll();
    	ArrayList<City> cities = new ArrayList<City>();
    	
    	for (Trip trip: trips){
    		ArrayList<CityVisit> visits = CityControl.readByTrip(trip);
    		for (CityVisit visit: visits){
    			City c = CityControl.read(visit.getCity());
    			cities.add(c);
    		}
    	}
		return cities;
    }
}