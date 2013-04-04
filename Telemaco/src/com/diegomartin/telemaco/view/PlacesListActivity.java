package com.diegomartin.telemaco.view;

import java.sql.Date;
import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.GeoFacade;
import com.diegomartin.telemaco.control.PlaceControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.Trip;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class PlacesListActivity extends ListActivity {
    private Trip trip;
    private ArrayList<IListItem> items;
    private Place place;
        
	protected static final int DATE_DIALOG_ID = 0;
    private static final int YEAR_OFFSET = 1900;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);

        this.refresh();
                
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  open(id);
          }
        });
        
        registerForContextMenu(getListView());
    }
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    	Date date = new Date(year-YEAR_OFFSET, monthOfYear, dayOfMonth);
            add(place, date);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.places_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.help:
        		return this.help();
        	case R.id.update:
        		return this.update();
        	case R.id.location:
        		return this.updateLocation();
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.places_contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = getListAdapter().getItemId(info.position);
    	
    	switch (item.getItemId()) {
    		case R.id.open:
    			this.open(menuItem);
			case R.id.add:
				this.place = this.getItem(menuItem);
				showDialog(DATE_DIALOG_ID);
    	}
    	return super.onOptionsItemSelected(item);
	}
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.refresh();
    }
    
    public ArrayList<IListItem> getItems() {
		ArrayList<CityVisit> cities = CityControl.readByTrip(this.trip);
		this.items = new ArrayList<IListItem>();
		
		for(int i=0;i<cities.size();i++){
			CityVisit visit = (CityVisit) cities.get(i);
			ArrayList<Place> places = PlaceControl.readByCity(visit.getCity());
			
			for (Place p: places){
				City c = CityControl.read(p.getCity());
				p.setCityName(c.getName());
				this.items.add(p);
			}
		}
		return this.items;
    }
    
    private void open(long id){
    	Place place = this.getItem(id);
    	Intent intent = new Intent(this, PlaceActivity.class);
		intent.putExtras(getIntent());
		intent.putExtra(ActionsFacade.EXTRA_PLACE, place);
    	startActivity(intent);
    }
    
    private void add(Place p, Date date){
    	PlaceControl.createVisit(p, this.trip, date);
    }
    
    private boolean help(){
    	startActivity(ActionsFacade.getInstance().launchHelp(this));
    	return true;
    }
    
    private boolean update(){
    	ActionsFacade.getInstance().launchSync(getApplicationContext());
    	return true;
    }
    
    private void refresh(){
        ArrayList<IListItem> items = this.getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }
    
    private Place getItem(long id){
    	return (Place) this.items.get((int) id);
    }
    
    private boolean updateLocation() {
		GeoFacade.getInstance(this).startPositioning(this);
		this.refresh();
		return true;
	}
}