package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CountryControl;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Trip;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CountrySearchActivity extends ListActivity {
	private ArrayList<Country> countries;
	private Trip trip;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    onSearchRequested();
	    this.countries = getItems();
	    
	    Bundle extras = getIntent().getExtras();
	    this.trip  = (Trip) extras.get(ActionsFacade.EXTRA_TRIP);
	    
	    if (this.trip == null){
        	ToastFacade.show(this, getString(R.string.error_missing));
        }

		this.refresh();
		
		ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  Country listItem = getItem(id);
              startCitySearch(listItem);
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
			this.countries = CountryControl.searchCountries(query);
			this.refresh();
	    }
	}
	
	private void refresh(){
		ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(this, android.R.layout.simple_list_item_1, this.countries);
		setListAdapter(adapter);
	}
	
	private ArrayList<Country> getItems() {
    	return CountryControl.readCountries();
    }
	
	private Country getItem(long id){
    	return (Country) this.countries.get((int) id);
    }
	
	private void startCitySearch(Country country){
		Intent city = new Intent(this, CitySearchActivity.class);
		city.putExtras(getIntent());
		city.putExtra(ActionsFacade.EXTRA_COUNTRY, country);
		city.putExtra(ActionsFacade.EXTRA_TRIP, this.trip);
		startActivity(city);
    	finish();
    }
}