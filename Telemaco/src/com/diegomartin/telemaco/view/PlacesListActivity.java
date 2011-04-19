package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;

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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        final String[] datos =  new String[]{"Lugar 1",
        								     "Lugar 2",
        								     "Lugar 3",
        								     "Lugar 4",
        								     "Lugar 5",
        								     "Lugar 6",
        								     "Lugar 7",
        								     "Lugar 8",
        								     "Lugar 9",
        								     "Lugar 10",
        								     "Lugar 11",
        								     "Lugar 12"};
        
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos));
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
        	return true;
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
}
