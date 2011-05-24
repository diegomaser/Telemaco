package com.diegomartin.telemaco.control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.diegomartin.telemaco.control.sync.Processor;
import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
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
		// TODO: set url
		RestMethod.get("http://", new Processor(){
			public void response(String content) throws JSONException {
				cities = new Objects();
				JSONArray arr = new JSONArray(content);
				for(int i=0;i<arr.length();i++){
					City c = new City((JSONObject) arr.get(i));
					cities.add(c);
				}
			}
		});
		return cities;
	}
	
	public static Objects searchCities(Context context, Country c, String query) {
		// TODO: set url
		RestMethod.get("http://", new Processor(){
			public void response(String content) throws JSONException {
				cities = new Objects();
				JSONArray arr = new JSONArray(content);
				for(int i=0;i<arr.length();i++){
					City c = new City((JSONObject) arr.get(i));
					cities.add(c);
				}
			}
		});
		return cities;
	}

	public static void addCitytoTrip(City city, Trip trip) {
		// TODO Auto-generated method stub
	}
}
