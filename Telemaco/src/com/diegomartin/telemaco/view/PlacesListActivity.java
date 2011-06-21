package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.PlaceControl;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PlacesListActivity extends ListActivity {
    private Trip trip;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
        
        ArrayList<IListItem> items = getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
        
        registerForContextMenu(getListView());
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
        case R.id.rearrange:
            return true;
        case R.id.help:
        	return this.help();
        case R.id.update:
        	return this.update();
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
    			return true;
			case R.id.delete:
				return true;
    	}
    	return super.onOptionsItemSelected(item);
	}
    
    public ArrayList<IListItem> getItems() {
		ArrayList<IListItem> MiLista = new ArrayList<IListItem>();
		Objects cities = CityControl.readByTrip(this.trip);
		for(int i=0;i<cities.size();i++){
			CityVisit visit = (CityVisit) cities.get(i);
			ArrayList<IListItem> items = (ArrayList<IListItem>) PlaceControl.readByCity(visit.getCity()).getList();
			MiLista.addAll(items);
		}
		return MiLista;
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
    	if (items.size()>0) setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    	else{
    		String[] list = {getString(R.string.new_place)};
    		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    	}
    }
}
