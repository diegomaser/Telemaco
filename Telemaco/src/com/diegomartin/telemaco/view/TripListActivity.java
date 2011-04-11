package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.R.menu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class TripListActivity extends ListActivity {
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        final String[] datos =  new String[]{"Viaje a Madrid",
        								     "Viaje a París",
        								     "Viaje a Londres",
        								     "Viaje a San Petersburgo",
        								     "Añadir nuevo viaje"};
        
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos));
        
        
        ListView lv = getListView();
        //lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              startActivity(new Intent(TripListActivity.this, TripTabActivity.class));
          }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.triplist_menu, menu);
        return true;
    }    
}