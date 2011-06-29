package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.TripControl;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;
import com.diegomartin.telemaco.persistence.DatabaseHelper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class TripListActivity extends Activity {
	private ListView lv;
	private Button add; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.trip_list);
        DatabaseHelper.setContext(this);
        
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType(getString(R.string.package_name));
        
        if (accounts.length == 0){
        	Intent login = new Intent(this, AuthenticatorActivity.class);
        	startActivity(login);
        }
        else{
        	for(Account a: accounts){
        		ContentResolver.setIsSyncable(a, getString(R.string.package_name), 1);        		
        	}

            this.lv = (ListView) findViewById(R.id.list);
            this.lv.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	  Trip listItem = getItem(id);
                  openItem(listItem);
              }
            });
            
            this.add = (Button) findViewById(R.id.add);
            this.add.setOnClickListener(new OnClickListener() {
              public void onClick(View view) {
            	  addItem();
              }
            });
            
            this.refresh();
            registerForContextMenu(this.lv);
        }
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.onCreate(null);
    	//this.refresh();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.triplist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.triplist_contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
	        case R.id.add:
	        	return this.addItem();
	        case R.id.help:
	        	return this.help();
	        case R.id.update:
	        	return this.update();
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = this.lv.getAdapter().getItemId(info.position);
    	Trip listItem = this.getItem((int)menuItem);
    	
    	switch (item.getItemId()) {
    		case R.id.open:
    			return openItem(listItem);
    		case R.id.edit:
    			return editItem(listItem);
			case R.id.delete:
				return deleteItem(listItem);
			case R.id.share:
				return shareItem(listItem);
    	}
    	return super.onOptionsItemSelected(item);
	}

	private ArrayList<IListItem> getItems() {
    	return (ArrayList<IListItem>) TripControl.readTrips().getList();
    }
    
    private Trip getItem(long id){
    	return (Trip) TripControl.readTrip(id);
    }
    
    private void refresh(){
    	ArrayList<IListItem> items = this.getItems();
    	if (this.lv != null) this.lv.setAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }
    
    private boolean addItem(){
    	startActivity(new Intent(getApplicationContext(), TripActivity.class));
    	this.refresh();
        return true;
    }
    
    private boolean openItem(Trip trip){
    	Intent intent = new Intent(getApplicationContext(), TripTabActivity.class);
		intent.putExtra(ActionsFacade.EXTRA_TRIP, trip);
    	startActivity(intent);
		return true;
    }
    
    private boolean editItem(Trip trip){
    	Intent intent = new Intent(getApplicationContext(), TripActivity.class);
		intent.putExtra(ActionsFacade.EXTRA_TRIP, trip);
		startActivity(intent);
		this.refresh();
		return true;
    }
    
    private boolean deleteItem(Trip trip){
    	TripControl.deleteTrip(trip.getId());
		this.refresh();
		return true;
    }
    
    private boolean shareItem(Trip trip) {
    	startActivity(ActionsFacade.getInstance().share(trip.getName(), trip.getDescription()));
		return true;
	}
    
    private boolean help(){
    	startActivity(ActionsFacade.getInstance().launchHelp(this));
    	return true;
    }
    
    private boolean update(){
    	ActionsFacade.getInstance().launchSync(getApplicationContext());
    	return true;
    }
}