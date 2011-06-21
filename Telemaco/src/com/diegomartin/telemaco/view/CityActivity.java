package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.model.City;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CityActivity extends ListActivity{
    private City city;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        this.city = (City) getIntent().getExtras().get(ActionsFacade.EXTRA_CITY);

        this.refresh();
        
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if(position == 0) open();
            }
          });
     }
    
    private void open(){
    	String url = "http://es.wikipedia.org/wiki/" + this.city.getName();
    	startActivity(ActionsFacade.getInstance().launchBrowserURL(this, url));
    }
    
    private void refresh(){
        ArrayList<String> items = getItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    private ArrayList<String> getItems() {
		ArrayList<String> list = new ArrayList<String>();
		//Añadimos los libros a la lista
		list.add(getString(R.string.city_info));
		list.add(getString(R.string.tourism_info));
		list.add(getString(R.string.map));
		return list;
    }
}
