package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.PlaceControl;
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
import android.widget.AdapterView.AdapterContextMenuInfo;

//TODO: Add buttons to main layout to add places and transport
public class PlanListActivity extends ListActivity {
	private Trip trip;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
        
        //setContentView(R.layout.main);
        
        ArrayList<IListItem> items = getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
        
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
    			return true;
			case R.id.delete:
				return true;
    	}
    	return super.onOptionsItemSelected(item);
	}
    
    public ArrayList<IListItem> getItems() {
		ArrayList<PlaceVisit> visits = PlaceControl.readByTrip(this.trip);
		ArrayList<IListItem> places= new ArrayList<IListItem>();
		for(PlaceVisit visit: visits){
			Place place = PlaceControl.read(visit.getPlace());
			places.add(place);
		}
		return places;
    }
    
    private boolean rearrangePlan() {
    	Intent i = new Intent(getApplicationContext(),PlanRearrangeActivity.class);
    	// TODO: What do I pass to the RearrangeActivity?
    	//i.putExtra(name, value);
    	startActivity(i);
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
}