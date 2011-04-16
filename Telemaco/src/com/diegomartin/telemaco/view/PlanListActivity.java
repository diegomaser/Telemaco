package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class PlanListActivity extends ListActivity {
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
        //GeoFacade.getInstance().startPositioning(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.plan_menu, menu);
        return true;
    }    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.rearrange:
        	startActivity(new Intent(getApplicationContext(),PlanRearrangeActivity.class));
            return true;
        case R.id.help:
        	//Location l = GeoFacade.getInstance().getLocation();
        	//startActivity(ActionsFacade.getInstance().launchMaps(l.getLatitude(), l.getLongitude()));
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
