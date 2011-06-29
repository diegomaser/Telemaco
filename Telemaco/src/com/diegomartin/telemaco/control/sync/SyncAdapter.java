package com.diegomartin.telemaco.control.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.diegomartin.telemaco.R;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private String accountType;
    private AccountManager accountManager;
    private Context context;
    //private Date lastUpdated;

    public SyncAdapter(Context c, boolean autoInitialize) {
        super(c, autoInitialize);
        this.context = c;
        this.accountManager = AccountManager.get(this.context);
        this.accountType = this.context.getString(R.string.package_name);
    }
    
    public void launchSync(){
		for(Account a : accountManager.getAccountsByType(accountType)) {
			ContentResolver.requestSync(a, this.accountType, new Bundle());
		}
	}

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
    	AccountManager am = AccountManager.get(this.context);
    	//String user = am.getUserData(account, AccountManager.KEY_ACCOUNT_NAME);
    	String user = account.name;
    	String password = am.getPassword(account);

    	//this.lastUpdated = new Date(System.currentTimeMillis());
        //String authtoken = null;
		//authtoken = accountManager.blockingGetAuthToken(account, accountType, true /* notifyAuthFailure */);
    }
}
