package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.control.CountryControl;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Objects;

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

public class CountrySearchActivity extends ListActivity {
	private Objects countries;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    onSearchRequested();
	    this.countries = getItems();
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
		ArrayList<Country> array = (ArrayList<Country>) this.countries.getList();
		ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(this, R.layout.simple_list_item_1, array);
		setListAdapter(adapter);
	}
	
	private Objects getItems() {
    	return CountryControl.readCountries();
    }
	
	private Country getItem(long id){
    	return (Country) this.countries.get(id);
    }
	
	private void startCitySearch(Country country){
		Intent city = new Intent(this, CitySearchActivity.class);
		city.putExtras(getIntent());
		city.putExtra("country", country);
		startActivity(city);
    	finish();
    }
}