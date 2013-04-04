package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.model.City;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CityActivity extends Activity{
    private City city;
    private ListView lv;
    private TextView name;
    private TextView description;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.city);
        
        this.city = (City) getIntent().getExtras().get(ActionsFacade.EXTRA_CITY);
        
        this.name = (TextView) findViewById(R.id.name);
        this.description = (TextView) findViewById(R.id.description);
        this.lv = (ListView) findViewById(R.id.list);
        
        this.name.setText(this.city.getName());
        this.description.setText(this.city.getDescription());
        this.refresh();
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if (position == 0) openWikipedia();
            	else if (position == 1) openWikitravel();
            	else if (position == 2) openMap();
            	else if (position == 3) openDirections();
            }
          });
     }
    
    private void openWikipedia(){
    	//String url = this.city.getWikipediaURL();
    	String url = "http://en.m.wikipedia.org/wiki/" + this.city.getName().replace(' ', '_');
    	if (url.length() > 0) startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
    }
    
    private void openWikitravel(){
    	//String url = this.city.getWikitravelURL();
    	String url = "http://m.wikitravel.org/en/" + this.city.getName().replace(' ', '_');
    	if (url.length() > 0) startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
    }
    
    private void openMap(){
    	try{
    		startActivity(ActionsFacade.getInstance().launchMaps(this.city.getLat(), this.city.getLng()));
    	}
    	catch(ActivityNotFoundException e){
    		ToastFacade.show(this, getString(R.string.error_maps));
    	}
    }
    
    private void openDirections(){
    	try{
    		startActivity(ActionsFacade.getInstance().launchCarNavigation(this.city.getLat(), this.city.getLng()));
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
		//Añadimos los items a la lista
		list.add(getString(R.string.city_info));
		list.add(getString(R.string.tourism_info));
		list.add(getString(R.string.map));
		list.add(getString(R.string.directions));
		return list;
    }
}
