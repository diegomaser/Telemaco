package com.diegomartin.telemaco.control.sync;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.PlaceControl;
import com.diegomartin.telemaco.control.RESTResources;
import com.diegomartin.telemaco.control.TripControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.persistence.PlaceDAO;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private String accountType;
    private AccountManager accountManager;
    private Context context;
    private Date lastUpdated;
    
    private String user;
    private String password;

    public SyncAdapter(Context c, boolean autoInitialize) {
        super(c, autoInitialize);
        this.context = c;
        this.accountManager = AccountManager.get(this.context);
        this.accountType = this.context.getString(R.string.package_name);
        
    }
    
    public void launchSync(){
		for(Account a : accountManager.getAccountsByType(accountType)) {
			ContentResolver.requestSync(a, this.accountType, new Bundle());
		}
	}

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
    	Log.i("SYNC", "Starting sync process. Last sync:" + this.lastUpdated);

    	AccountManager am = AccountManager.get(this.context);
    	this.user = account.name;
    	this.password = am.getPassword(account);
    	this.lastUpdated = new Date(System.currentTimeMillis());
    	
    	try{
	    	// We sync entity by entity
    		
	    	// 2-way
    		Log.i("SYNC", "Users sync");
    		syncFacebookUser();
    		
    		//Log.i("SYNC", "Trips sync");
    		//syncTrips();
    		
    		//Log.i("SYNC", "CityVisits sync");
    		//syncCityVisits();

    		//Log.i("SYNC", "PlaceVisits sync");
    		//syncPlaceVisits();
    		
    		//Log.i("SYNC", "Transports sync");
    		//syncTransports();

	    	// 1-way
    		//Log.i("SYNC", "Countries sync");
    		//syncCountry();
    		
    		//Log.i("SYNC", "Places sync");
    		//syncPlaces(false);
    		
    		//Log.i("SYNC", "Recommendations sync");
    		//syncPlaces(true);
    		
    		Place p = PlaceControl.read(118);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(147);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(141);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(166);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(163);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(183);
    		p.setRecommended(true);
    		PlaceDAO.update(p);
    		
    		p = PlaceControl.read(185);
    		p.setRecommended(true);
    		PlaceDAO.update(p);

    		p = PlaceControl.read(254);
    		p.setRecommended(true);
    		PlaceDAO.update(p);

    		
    		Log.i("SYNC", "Cities sync");
    		//syncCities();
    		
    		Log.i("SYNC", "Sync finished");
	    } catch (final ParseException e) {
            syncResult.stats.numParseExceptions++;
        } catch (final JSONException e) {
            syncResult.stats.numParseExceptions++;
        }
    }
    
    private void syncTransports() {
		// TODO
	}

	private void syncPlaceVisits() {
		// TODO
	}

	private void syncCountry() {
		// TODO

    	// Country
    	// Currency
    	// Language
    	// Plug		
	}

	private void syncCities(){
		// TODO
    }
    
    private void syncPlaces(boolean recommendations) throws JSONException{
    	String placesURL;
    	ArrayList<City> cities = CityControl.getCities();
    	ArrayList<Place> places = new ArrayList<Place>();
    	
    	for (City city: cities){
        	if (recommendations) placesURL = RESTResources.getInstance(this.context).getRecommendationURL(city);
        	else placesURL = RESTResources.getInstance(this.context).getPlaceURL(city);
            	
        	//String content = RestMethod.get(this.context, placesURL, this.user, this.password);
        	String content = RestMethod.get(this.context, placesURL);
        	JSONArray json = new JSONArray(content);
            
        	for (int i=0;i<json.length();i++){
        		Place place = new Place(json, this.context, i);
        		place.setRecommended(recommendations);
        		places.add(place);
        	}
    	}
    	PlaceControl.createOrUpdate(places);
    }
        
    private void syncTrips() throws JSONException{
    	ArrayList<Trip> trips = (ArrayList<Trip>) TripControl.readAll();
    	
    	String tripURL = RESTResources.getInstance(this.context).getTripURL();
    	for (Trip trip: trips){
    		// Changes up
    		
    		if(trip.isPendingDelete()){
    			String url = tripURL + "delete/" + trip.getId();
    			RestMethod.delete(this.context, url, this.user, this.password);
    			TripControl.delete(trip.getId());
    		}
    		if(trip.isPendingCreate()){
    			String url = tripURL + "";
    			JSONObject obj = new JSONObject(trip.toJSON());
    			RestMethod.post(this.context, url, obj, this.user, this.password);
    			TripControl.setPendingCreate(trip.getId(), false);
    		}
    		else if (trip.isPendingUpdate()){
    			String url = tripURL + "";
    			JSONObject obj = new JSONObject(trip.toJSON());
    			RestMethod.put(this.context, url, obj, this.user, this.password);
    			TripControl.setPendingUpdate(trip.getId(), false);
    		}
    		// Changes down
    		//String url = RESTResources.getInstance(this.context).getPlaceURL();
    		//String content = RestMethod.get(this.context, url, this.user, this.password);
    	}
    }
    
    private void syncCityVisits() throws JSONException{
    	ArrayList<CityVisit> visits = (ArrayList<CityVisit>) CityVisitDAO.read();
    	
    	String tripURL = RESTResources.getInstance(this.context).getTripURL();
    	for (CityVisit visit: visits){
    		// Changes up
    		
    		if(visit.isPendingDelete()){
    			String url = tripURL + "delete/" + visit.getId();
    			RestMethod.delete(this.context, url, this.user, this.password);
    			TripControl.delete(visit.getId());
    		}
    		if(visit.isPendingCreate()){
    			String url = tripURL + "";
    			JSONObject obj = new JSONObject(visit.toJSON());
    			RestMethod.post(this.context, url, obj, this.user, this.password);
    			TripControl.setPendingCreate(visit.getId(), false);
    		}
    		else if (visit.isPendingUpdate()){
    			String url = tripURL + "";
    			JSONObject obj = new JSONObject(visit.toJSON());
    			RestMethod.put(this.context, url, obj, this.user, this.password);
    			TripControl.setPendingUpdate(visit.getId(), false);
    		}
    		// Changes down
    		//String url = RESTResources.getInstance(this.context).getPlaceURL();
    		//String content = RestMethod.get(this.context, url, this.user, this.password);
    	}
    }
    
    private void syncFacebookUser() throws JSONException{
    	SharedPreferences prefs = this.context.getSharedPreferences(this.context.getString(R.string.package_name), Context.MODE_PRIVATE);
    	String accessToken = prefs.getString(ActionsFacade.EXTRA_ACCESS_TOKEN, "");
    	
    	if (accessToken != "") {
    		String url = RESTResources.getInstance(this.context).getUserURL();    	
    		JSONObject obj = new JSONObject();
    		obj.put("access_token", accessToken);
    		RestMethod.put(this.context, url, obj, this.user, this.password);
    	}
    }
}
