package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.TripControl;
import com.diegomartin.telemaco.model.IListItem;
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
        
        ArrayList<IListItem> items = getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
                
        ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              startActivity(new Intent(TripListActivity.this, TripTabActivity.class));
          }
        });
        
        registerForContextMenu(lv);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	ArrayList<IListItem> items = getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
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
	        	startActivity(new Intent(getApplicationContext(),TripActivity.class));
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info= (AdapterContextMenuInfo) item.getMenuInfo();
    	long menuItem = getListAdapter().getItemId(info.position);
    	
    	switch (item.getItemId()) {
    		case R.id.open:
	        	startActivity(new Intent(getApplicationContext(),TripActivity.class));
    			return true;
			case R.id.delete:
				TripControl.deleteTrip(menuItem);
				return true;
    	}
    	return super.onOptionsItemSelected(item);
	}
    
    
    public ArrayList<IListItem> getItems() {
    	return (ArrayList<IListItem>) TripControl.readTrips().getList();
		/*ArrayList<IListItem> MiLista = new ArrayList<IListItem>();
		
		// Creamos los objetos
		IListItem trip1 = new Trip();
		IListItem trip2 = new Trip();
		IListItem trip3 = new Trip();
		IListItem trip4 = new Trip();
		IListItem trip5 = new Trip();
		
		trip1.setName("Viaje a Madrid");
		trip2.setName("Viaje a Barcelona");
		trip3.setName("Viaje a San Petersburgo");
		trip4.setName("Viaje a París");
		trip5.setName("Viaje a Kortrijk");
		
		trip1.setDescription("Esto es una prueba");
		trip2.setDescription("Esto es otra prueba");
		trip3.setDescription("Otra prueba más");
		trip4.setDescription("Otra descripción");
		trip5.setDescription("Más pruebas");
		
		//Añadimos los libros a la lista
		MiLista.add(trip1);
		MiLista.add(trip2);
		MiLista.add(trip3);
		MiLista.add(trip4);
		MiLista.add(trip5);

		return MiLista;*/
    }
}