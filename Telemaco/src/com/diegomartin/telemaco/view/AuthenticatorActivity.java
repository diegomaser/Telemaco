package com.diegomartin.telemaco.view;

import org.apache.http.HttpStatus;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.RESTResources;
import com.diegomartin.telemaco.control.sync.RestMethod;
import com.diegomartin.telemaco.view.RegisterActivity;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {
	private Button loginButton;
	private Button registerButton;
	private EditText username;
	private EditText password;
	
    @Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        this.loginButton = (Button) findViewById(R.id.loginbutton);
        this.registerButton = (Button) findViewById(R.id.registerbutton);
        this.username = (EditText) findViewById(R.id.user_edit);
        this.password = (EditText) findViewById(R.id.password_edit);
        
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	addAccount();
            }
        });
        
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	register();
            }
        });
	}
    
    public void register(){
    	Intent register = new Intent(this, RegisterActivity.class);
    	startActivity(register);
    }
    
    public void addAccount(){
    	String user = this.username.getText().toString().trim().toLowerCase();
		String pwd = this.password.getText().toString().trim();
		    	
    	if(!(user.length() == 0) && !(pwd.length() == 0)){
    		String url = RESTResources.getInstance(this).getTripURL();
        	int statusCode = RestMethod.login(this, url, user, pwd);
        	if (statusCode == HttpStatus.SC_UNAUTHORIZED){
        		ToastFacade.show(this, getString(R.string.bad_password));
        		this.password.setText("");
        	}
        	else{
            	Account account = new Account(user, getString(R.string.package_name));
            	AccountManager am = AccountManager.get(this);

            	am.addAccountExplicitly(account, pwd, null);
            	ContentResolver.setSyncAutomatically(account, getString(R.string.package_name), true);
            	
                final Intent intent = new Intent();
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, user);
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.package_name));
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, pwd);
                setAccountAuthenticatorResult(intent.getExtras());
                setResult(RESULT_OK, intent);
                finish();
        	}
    	}
    	else ToastFacade.show(this, getString(R.string.empty_password));
	}
}
