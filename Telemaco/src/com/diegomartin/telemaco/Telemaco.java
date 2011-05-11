package com.diegomartin.telemaco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.diegomartin.telemaco.persistence.DatabaseHelper;
import com.diegomartin.telemaco.view.TripListActivity;

public class Telemaco extends Activity {
		
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Init of databasehelper
        DatabaseHelper.setContext(getApplicationContext());
        
    	final Button loginbutton = (Button) findViewById(R.id.loginbutton);
    	
    	loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
            	Intent intent = new Intent(getApplicationContext(), TripListActivity.class);
            	//Iniciamos la nueva actividad
                startActivity(intent);
            }
        });
	}
}
