package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.model.Country;

import android.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CountryActivity extends ListActivity {
	private Country country;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //setContentView(R.layout.main);
	    this.country = (Country) getIntent().getExtras().get(ActionsFacade.EXTRA_CITY);
	
	    this.refresh();
	    
	    ListView lv = getListView();
	    lv.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	if(position == 1) open();
	        	else if(position == 2) open();
	        	else if(position == 3) open();
	        }
	      });
	 }
	
	private void open(){
		String url = "http://es.wikipedia.org/wiki/" + this.country.getName();
		ActionsFacade.getInstance().launchBrowserURL(this, url);
	}
	
	private void refresh(){
	    ArrayList<String> items = getItems();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, items);
	    setListAdapter(adapter);
	}
	
	private ArrayList<String> getItems() {
		ArrayList<String> list = new ArrayList<String>();
		//Añadimos los libros a la lista
		list.add("Country information");
		list.add("Tourism information");
		list.add("Language");
		list.add("Currency info");
		list.add("Plug");
		return list;
	}
}