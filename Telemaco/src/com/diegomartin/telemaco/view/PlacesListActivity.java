package com.diegomartin.telemaco.view;

import java.util.ArrayList;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.model.IListItem;
import com.diegomartin.telemaco.model.Objects;
import com.diegomartin.telemaco.model.Place;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PlacesListActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        ArrayList<IListItem> items = getItems();
        setListAdapter(new ListItemAdapter(this, R.layout.list_item, items));
        
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
    
    public ArrayList<IListItem> getItems() {
		ArrayList<IListItem> MiLista = new ArrayList<IListItem>();
		
		// Creamos los objetos
		IListItem trip1 = new Place();
		IListItem trip2 = new Place();
		IListItem trip3 = new Place();
		IListItem trip4 = new Place();
		IListItem trip5 = new Place();
		IListItem trip6 = new Place();
		IListItem trip7 = new Place();
		IListItem trip8 = new Place();
		IListItem trip9 = new Place();
		IListItem trip10 = new Place();
		
		trip1.setName("Lugar 1");
		trip2.setName("Lugar 2");
		trip3.setName("Lugar 3");
		trip4.setName("Lugar 4");
		trip5.setName("Lugar 5");
		trip6.setName("Lugar 6");
		trip7.setName("Lugar 7");
		trip8.setName("Lugar 8");
		trip9.setName("Lugar 9");
		trip10.setName("Lugar 10");
		
		trip1.setDescription("Esto es una prueba");
		trip2.setDescription("Esto es otra prueba");
		trip3.setDescription("Otra prueba más");
		trip4.setDescription("Otra descripción");
		trip5.setDescription("Más pruebas");
		trip6.setDescription("Más pruebas");
		trip7.setDescription("Más pruebas");
		trip8.setDescription("Más pruebas");
		trip9.setDescription("Más pruebas");
		trip10.setDescription("Más pruebas");
		
		//Añadimos los libros a la lista
		MiLista.add(trip1);
		MiLista.add(trip2);
		MiLista.add(trip3);
		MiLista.add(trip4);
		MiLista.add(trip5);
		MiLista.add(trip6);
		MiLista.add(trip7);
		MiLista.add(trip8);
		MiLista.add(trip9);
		MiLista.add(trip10);

		return MiLista;
    }
}
