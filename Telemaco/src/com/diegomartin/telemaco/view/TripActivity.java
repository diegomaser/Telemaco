package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TripActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip);
        
    	final Button saveButton = (Button) findViewById(R.id.save);
    	
    	saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	// TODO: guardar datos
                startActivity(new Intent(getApplicationContext(), TripListActivity.class));
            }
        });
	}
}
