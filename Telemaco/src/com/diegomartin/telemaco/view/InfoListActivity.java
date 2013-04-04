package com.diegomartin.telemaco.view;

import java.util.ArrayList;
import com.diegomartin.telemaco.R;

import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.control.CountryControl;
import com.diegomartin.telemaco.control.GeoFacade;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.CityVisit;
import com.diegomartin.telemaco.model.Country;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class InfoListActivity extends Activity {
	private ListView lv;
	private Button add; 
	
    private Trip trip;
    private ArrayList<Object> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_list);

        this.trip = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
        
        if (this.trip == null){
        	ToastFacade.show(this, getString(R.string.error_missing));
        }
        
        this.lv = (ListView) findViewById(R.id.list);
        this.add = (Button) findViewById(R.id.add);
        this.refresh();
        
        this.lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  openCity(id);
          }
        });
        
        this.add.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
          	  addCity();
            }
          });
        
        registerForContextMenu(this.lv);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.refresh();
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
	        case R.id.location:
	        	return this.updateLocation();
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
    	long menuItem = this.lv.getAdapter().getItemId(info.position);
    	
    	switch (item.getItemId()) {
    		case R.id.showcity:
          		return openCity(menuItem);
    		case R.id.showcountry:
    			return openCountry(menuItem);
			case R.id.delete:
				return delete(menuItem);
			//case R.id.share:
			//	return share(menuItem);
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    private boolean openCity(long id){
    	CityVisit visit = (CityVisit) this.items.get((int) id);
    	City city = CityControl.read(visit.getCity());
	  	Intent intent = new Intent(this, CityActivity.class);
  		intent.putExtra(ActionsFacade.EXTRA_CITY, city);
  		startActivity(intent);
  		return true;
    }
    
    private boolean openCountry(long id){
    	CityVisit visit = (CityVisit) this.items.get((int) id);
    	City city = CityControl.read(visit.getCity());
    	Country country = CountryControl.read(city.getCountry());
    	Intent intent = new Intent(this, CountryActivity.class);
    	intent.putExtra(ActionsFacade.EXTRA_COUNTRY, country);
    	startActivity(intent);
    	return true;
    }
    
    private boolean delete(long id){
    	Object obj = this.items.get((int) id);
    	CityControl.setPendingDelete(((CityVisit) obj).getId());
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
    
    private boolean addCity(){
    	Intent addCity = new Intent(this, CountrySearchActivity.class);
    	addCity.putExtra(ActionsFacade.EXTRA_TRIP, this.trip);
    	startActivity(addCity);
    	this.refresh();
    	return true;
    }
    
	private ArrayList<IListItem> getItems() {
		ArrayList<IListItem> list = new ArrayList<IListItem>();
		this.items = new ArrayList<Object>();
		ArrayList<CityVisit> l = CityControl.readByTrip(this.trip);
		for(int i=0;i<l.size();i++){
			CityVisit visit = (CityVisit) l.get(i);
			this.items.add(visit);
			
			City city = CityControl.read(visit.getCity());
			city.setVisitDate(visit.getDate());
			
			Country country = CountryControl.read(city.getCountry());
			city.setCountryName(country.getName());
			
			list.add(city);
		}
		return list;
    }
    
    private void refresh(){
    	ArrayList<IListItem> i = this.getItems();
    	this.lv.setAdapter(new ListItemAdapter(this, R.layout.list_item, i));
    }
    
    private boolean updateLocation() {
		GeoFacade.getInstance(this).startPositioning(this);
		this.refresh();
		return true;
	}
}