package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;
import com.diegomartin.telemaco.control.TripControl;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Trip;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class TripListActivity extends ListActivity {
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        this.refresh();
                
        ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  Trip listItem = getItem((int)id);
              openItem(listItem);
          }
        });
        
        registerForContextMenu(lv);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	this.refresh();
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
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = getListAdapter().getItemId(info.position);
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
    	return TripControl.readTrips().getList();
    }
    
    private Trip getItem(int id){
    	return (Trip) TripControl.readTrips().get(id);
    }
    
    private void refresh(){
    	ArrayList<IListItem> items = this.getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
    }
    
    private boolean addItem(){
    	startActivity(new Intent(getApplicationContext(), TripActivity.class));
    	this.refresh();
        return true;
    }
    
    private boolean openItem(Trip t){
    	Intent intent = new Intent(getApplicationContext(), TripTabActivity.class);
		intent.putExtra("id", t.getId());
    	startActivity(intent);
		return true;
    }
    
    private boolean editItem(Trip t){
    	Intent intent = new Intent(getApplicationContext(), TripActivity.class);
		intent.putExtra("id", t.getId());
		intent.putExtra("name", t.getName());
		intent.putExtra("description", t.getDescription());
		intent.putExtra("startDate", t.getStartDate());
		intent.putExtra("endDate", t.getEndDate());
		startActivity(intent);
		this.refresh();
		return true;
    }
    
    private boolean deleteItem(Trip t){
    	TripControl.deleteTrip(t.getId());
		this.refresh();
		return true;
    }
    
    private boolean shareItem(Trip t) {
    	startActivity(ActionsFacade.getInstance().share(t.getName(), t.getDescription()));
		return true;
	}
}