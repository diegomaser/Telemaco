package com.diegomartin.telemaco.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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
    }
}
