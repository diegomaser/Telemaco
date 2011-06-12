package com.diegomartin.telemaco.view;

import java.util.ArrayList;
import com.diegomartin.telemaco.R;

import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.CountryControl;
import com.diegomartin.telemaco.control.NoteControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Note;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.CityVisitDAO;
import com.diegomartin.telemaco.persistence.NoteDAO;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class InfoListActivity extends ListActivity {
    private Trip trip;
    private ArrayList<Object> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
        ListView lv = getListView();
        this.refresh();
        
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  IListItem listItem = getItem(id);
        	  open(listItem);
          }
        });
        
        registerForContextMenu(lv);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.new_city:
        		return this.addCity();
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
        inflater.inflate(R.menu.info_contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = getListAdapter().getItemId(info.position);
    	
    	switch (item.getItemId()) {
			case R.id.delete:
				return delete(menuItem);
			//case R.id.share:
			//	return share(menuItem);
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    private boolean delete(long id){
    	Object obj = this.items.get((int) id);
    	if(obj instanceof CityVisit) CityVisitDAO.delete((CityVisit)obj);
    	else if(obj instanceof Note) NoteDAO.delete((Note)obj);
		return true;
    }
    
    private boolean open(IListItem obj){
    	Intent intent = null;
    	
    	if(obj instanceof City){
        	intent = new Intent(this, CityActivity.class);
        	intent.putExtra(ActionsFacade.EXTRA_CITY, (City)obj);
    	}
    	else if(obj instanceof Country){
        	intent = new Intent(this, CountryActivity.class);
        	intent.putExtra(ActionsFacade.EXTRA_COUNTRY, (Country)obj);
    	}
    	else if(obj instanceof Note){
        	intent = new Intent(this, NoteActivity.class);
        	intent.putExtra(ActionsFacade.EXTRA_NOTE, (Note)obj);
    	}
    	
    	startActivity(intent);
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
    
    private boolean addCity(){
    	Intent addCity = new Intent(this, CountrySearchActivity.class);
    	addCity.putExtra(ActionsFacade.EXTRA_TRIP, this.trip);
    	startActivity(addCity);
    	this.refresh();
    	return true;
    }
    
	private ArrayList<IListItem> getItems() {
		ArrayList<IListItem> list = new ArrayList<IListItem>();
		items = new ArrayList<Object>();
		Objects l = CityControl.readCityVisits(this.trip);
		for(int i=0;i<l.size();i++){
			CityVisit visit = (CityVisit) l.get(i);
			items.add(visit);
			items.add(visit);
			City city = CityControl.read(visit.getCity());
			Country country = CountryControl.read(city.getCountryId());
			list.add(city);
			list.add(country);
		}
		ArrayList<Note> notes = (ArrayList<Note>) NoteControl.readByTrip(this.trip).getList(); 
		list.addAll(notes);
		return list;
    }
    
    private IListItem getItem(long id){
    	ListAdapter l = getListAdapter();
    	IListItem o = (IListItem) l.getItem((int) id);
    	return o;
    }
    
    private void refresh(){
    	ArrayList<IListItem> items = this.getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }
}
