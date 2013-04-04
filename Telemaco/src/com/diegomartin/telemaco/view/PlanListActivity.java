package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.GeoFacade;
import com.diegomartin.telemaco.control.PlaceControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Place;
import com.diegomartin.telemaco.model.PlaceVisit;
import com.diegomartin.telemaco.model.Trip;

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
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class PlanListActivity extends ListActivity {
	private Trip trip;
    private ArrayList<IListItem> items;
    private ArrayList<PlaceVisit> visits;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
        
        //setContentView(R.layout.main);
        
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  open(id);
          }
        });
        
        this.refresh();
        registerForContextMenu(getListView());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plan_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }  
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.rearrange:
        	return rearrangePlan();
        case R.id.help:
        	return this.help();
        case R.id.update:
        	return this.update();
        case R.id.share:
        	return this.share();
        case R.id.location:
        	return this.updateLocation();
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plan_contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = getListAdapter().getItemId(info.position);

    	switch (item.getItemId()) {
    		case R.id.open:
    			return this.open(menuItem);
			case R.id.delete:
				return this.delete(menuItem);
			//case R.id.edit:
			//return true;
    	}
    	return super.onOptionsItemSelected(item);
	}
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.refresh();
    }
    
    public ArrayList<IListItem> getItems() {
		this.visits = PlaceControl.readByTrip(this.trip);
		this.items = new ArrayList<IListItem>();
		for(PlaceVisit visit: this.visits){
			Place place = PlaceControl.read(visit.getPlace());
			City city = CityControl.read(place.getCity());
			place.setCityName(city.getName());
			place.setVisitDate(visit.getDate());
			this.items.add(place);
		}
		return this.items;
    }
    
    private boolean rearrangePlan() {
    	Intent i = new Intent(getApplicationContext(),PlanRearrangeActivity.class);
    	i.putExtra(ActionsFacade.EXTRA_PLACES, this.items);
    	i.putExtra(ActionsFacade.EXTRA_TRIP, this.trip);
    	startActivity(i);
    	this.refresh();
        return true;
	}
    
    private boolean help(){
    	startActivity(ActionsFacade.getInstance().launchHelp(this));
    	return true;
    }
    
    private boolean update(){
    	ActionsFacade.getInstance().launchSync(this);
    	return true;
    }
    
    private boolean share(){
    	// TODO: This option wasn't intended to share the trip itself, but to share the plan! 
    	String subject = this.trip.getName();
    	String txt = this.trip.getDescription();
    	startActivity(ActionsFacade.getInstance().share(subject, txt));
    	return true;
    }
    
    private void refresh(){
    	ArrayList<IListItem> items = this.getItems();
    	setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }
    
    private boolean updateLocation() {
		GeoFacade.getInstance(this).startPositioning(this);
		this.refresh();
		return true;
	}
    
    private boolean open(long id){
    	Place place = this.getItem(id);
    	Intent intent = new Intent(this, PlaceActivity.class);
		intent.putExtras(getIntent());
		intent.putExtra(ActionsFacade.EXTRA_PLACE, place);
    	startActivity(intent);
    	return true;
    }
    
    private boolean delete(long id){
    	PlaceVisit visit = this.visits.get((int) id);
    	PlaceControl.setPendingDelete(visit.getId());
    	this.refresh();
		return true;
    }
    
    private Place getItem(long id){
    	return (Place) this.items.get((int) id);
    }
}