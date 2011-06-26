package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity {
	private Button registerButton;
    @Override
	public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.register);
    	
    	this.registerButton = (Button) findViewById(R.id.registerbutton);
    	
    	registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	register();
            }
        });
    }
    
    public void register(){
    	// TODO: register user in the server and add it to account manager (login)
    }
}
