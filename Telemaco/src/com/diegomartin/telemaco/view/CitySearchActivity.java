package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import android.R;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

public class CitySearchActivity extends ListActivity {
	//TODO: Add Recent Query Suggestions
	//TODO: Add Custom Suggestions
	
	private Country country;
	private Objects cities;
	private Trip trip;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    onSearchRequested();
	    
	    Bundle extras = getIntent().getExtras();
	    this.country = (Country) extras.get("country");
    	this.trip  = (Trip) extras.get(ActionsFacade.EXTRA_TRIP);
	    this.cities = getItems();
	    
		this.refresh();
		
		ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  City listItem = (City) cities.get(id);
              saveItem(listItem);
          }
        });
	}

	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			this.cities = CityControl.searchCities(this.country, query);
			this.refresh();
	    }
	}
	
	private void refresh(){
		ArrayList<City> array = (ArrayList<City>) this.cities.getList();
		ArrayAdapter<City> adapter = new ArrayAdapter<City>(this, R.layout.simple_list_item_1, array);
		setListAdapter(adapter);
	}
	
	private Objects getItems() {
    	return CityControl.readCities(this.country);
    }

    private void saveItem(City city){
    	CityControl.addCitytoTrip(city, trip);
    	finish();
    }
}
