package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.model.Place;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PlaceActivity extends Activity {
	private Place place;
    private ListView lv;
    private TextView name;
    private TextView description;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.place);
	    
	    this.place = (Place) getIntent().getExtras().get(ActionsFacade.EXTRA_PLACE);
	
        this.name = (TextView) findViewById(R.id.name);
        this.description = (TextView) findViewById(R.id.description);
        this.lv = (ListView) findViewById(R.id.list);
        
        this.name.setText(this.place.getName());
        this.description.setText(this.place.getDescription());
        this.refresh();

        lv.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	if (position == 0) openWikipedia();
	        	else if (position == 1) openMap();
	        	else if (position == 2) openDirections();
	        	else if (position == 3) openStreetView();
	        }
	      });
	 }
	
	private void openWikipedia(){
		//String url = this.country.getWikipediaURL();
		String url = "http://en.m.wikipedia.org/wiki/" + this.place.getName().replace(' ', '_');
		if (url.length() > 0) startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
	}
	
	private void openMap(){
    	try{
    		startActivity(ActionsFacade.getInstance().launchMaps(this.place.getLat(), this.place.getLng()));
    	}
    	catch(ActivityNotFoundException e){
    		ToastFacade.show(this, getString(R.string.error_maps));
    	}
    }
	
    private void openDirections(){
    	try{
    		startActivity(ActionsFacade.getInstance().launchWalkNavigation(this.place.getLat(), this.place.getLng()));
    	}
    	catch(ActivityNotFoundException e){
    		ToastFacade.show(this, getString(R.string.error_maps));
    	}
    }
    
    private void openStreetView(){
    	try{
    		startActivity(ActionsFacade.getInstance().launchStreetView(this.place.getLat(), this.place.getLng()));
    	}
    	catch(ActivityNotFoundException e){
    		ToastFacade.show(this, getString(R.string.error_maps));
    	}
    }
	
	private void refresh(){
	    ArrayList<String> items = getItems();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	    this.lv.setAdapter(adapter);
	}
	
	private ArrayList<String> getItems() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(getString(R.string.place_info));
		list.add(getString(R.string.map));
		list.add(getString(R.string.directions));
		list.add(getString(R.string.streetview));
		return list;
	}
}
