package com.diegomartin.telemaco.view;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.RESTResources;
import com.diegomartin.telemaco.control.sync.RestMethod;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
	
	private EditText username;
	private EditText password;
	private EditText password2;
	private Button registerButton;
	
	
    @Override
	public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.register);
    	
    	this.registerButton = (Button) findViewById(R.id.registerbutton);
    	this.username = (EditText) findViewById(R.id.user_edit);
        this.password = (EditText) findViewById(R.id.password_edit);
        this.password2 = (EditText) findViewById(R.id.password_edit2);
    	
    	registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	register();
            }
        });
    }
    
    public void register(){
    	String user = this.username.getText().toString().trim().toLowerCase();
		String pwd = this.password.getText().toString().trim();
		String pwd2 = this.password2.getText().toString().trim();
		
		if(!(user.length() == 0) && !(pwd.length() == 0) && !(pwd2.length() == 0)){
			if (pwd.equals(pwd2)){
				try{
						String url = RESTResources.getInstance(this).getRegistrationURL();
			    		JSONObject obj = new JSONObject();
			    		try {
							obj.put("username", user);
				    		obj.put("password", pwd);
						} catch (JSONException e) {
							ToastFacade.show(this, getString(R.string.error_sending));
						}
						
						int statusCode = RestMethod.post(this, url, obj);
			    		
			    		if (statusCode == HttpStatus.SC_CONFLICT){
			        		ToastFacade.show(this, getString(R.string.user_exists));
			        		this.username.setText("");
			    		}
			    		else{
			    			ToastFacade.show(this, getString(R.string.user_created));
			            	finish();
			    		}
				}
				catch (JSONException e){
					ToastFacade.show(this, getString(R.string.error_connecting));
				}
			}
			else ToastFacade.show(this, getString(R.string.different_passwords)); 
    	}
    	else ToastFacade.show(this, getString(R.string.empty_password));
    }
}
