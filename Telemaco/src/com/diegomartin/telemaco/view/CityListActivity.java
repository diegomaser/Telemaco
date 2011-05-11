package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.CityControl;
import com.diegomartin.telemaco.model.City;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View;

public class CityListActivity extends ListActivity {
	//TODO: Important: Add city searcher!!
	
     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
                
        ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  City listItem = getItem(id);
              saveItem(listItem);
          }
        });
        
        registerForContextMenu(lv);
    }

	private ArrayList<IListItem> getItems() {
    	return CityControl.readCities().getList();
    }
    
    private City getItem(long id){
    	return (City) CityControl.readCities().get(id);
    }
    
    private void refresh(){
    	ArrayList<IListItem> items = this.getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }

    private void saveItem(City city){
    	Trip trip  = (Trip) getIntent().getExtras().get(ActionsFacade.EXTRA_TRIP);
    	CityControl.addCitytoTrip(city, trip);
    	finish();
    }
}