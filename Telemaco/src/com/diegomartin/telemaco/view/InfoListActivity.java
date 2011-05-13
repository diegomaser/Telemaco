package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

public class InfoListActivity extends ExpandableListActivity {

    private ExpandableListAdapter adapter;
    private Bundle trip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.trip = (Bundle) getIntent().getExtras();
        
        // Set up our adapter
        adapter = new MyExpandableListAdapter(InfoListActivity.this);
        setListAdapter(adapter);
        registerForContextMenu(getExpandableListView());
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
        	case R.id.add:
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
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            return true;
        }

        return false;
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
    	Intent addCity = new Intent(getApplicationContext(), CityListActivity.class);
    	addCity.putExtras(this.trip);
    	startActivity(addCity);
    	return true;
    }
}
