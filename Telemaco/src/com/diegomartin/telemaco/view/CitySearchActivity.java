package com.diegomartin.telemaco.view;

import java.sql.Date;
import java.util.ArrayList;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.Trip;

public class CitySearchActivity extends ListActivity {
	
	private Country country;
	private City city;
	private ArrayList<City> cities;
	private Trip trip;

	protected static final int DATE_DIALOG_ID = 0;
    private static final int YEAR_OFFSET = 1900;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    onSearchRequested();
	    
	    Bundle extras = getIntent().getExtras();
	    this.country = (Country) extras.get(ActionsFacade.EXTRA_COUNTRY);
	    this.trip  = (Trip) extras.get(ActionsFacade.EXTRA_TRIP);
	    
	    if (this.trip == null || this.country == null){
        	ToastFacade.show(this, getString(R.string.error_missing));
        }
	    
	    this.cities = getItems();
		this.refresh();
		
		ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  city = (City) cities.get((int) id);
        	  showDialog(DATE_DIALOG_ID);
          }
        });
	}
	
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	Date date = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
            saveItem(city, date);
	    }
    };
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    	case DATE_DIALOG_ID:
	    		Date now = new Date(System.currentTimeMillis());
	    		return new DatePickerDialog(this,
	    									dateSetListener,
	    									now.getYear()+YEAR_OFFSET,
	    									now.getMonth(),
	    									now.getDate());
	    }
	    return null;
	}

	@Override
	protected void onNewIntent(Intent intent) {
	    setIntent(intent);
	    handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			this.cities = CityControl.searchCities(this, this.country, query);
			this.refresh();
	    }
	}
	
	private void refresh(){
		ArrayAdapter<City> adapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, this.cities);
		setListAdapter(adapter);
	}
	
	private ArrayList<City> getItems() {
    	return CityControl.searchCities(this, this.country);
    }

    private void saveItem(City city, Date date){
    	long id = CityControl.createCityVisit(this, city, this.trip, date);
    	CityControl.setPendingCreate(id, true);
    	finish();
    }
}
