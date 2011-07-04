package com.diegomartin.telemaco.control.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.RESTResources;
import com.diegomartin.telemaco.control.TripControl;
import com.diegomartin.telemaco.model.Trip;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private String accountType;
    private AccountManager accountManager;
    private Context context;
    private Date lastUpdated;
    
    private String user;
    private String password;

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
    	this.lastUpdated = new Date(System.currentTimeMillis());
    	AccountManager am = AccountManager.get(this.context);
    	//String user = am.getUserData(account, AccountManager.KEY_ACCOUNT_NAME);
    	this.user = account.name;
    	this.password = am.getPassword(account);
        //String authtoken = null;
		//authtoken = accountManager.blockingGetAuthToken(account, accountType, true /* notifyAuthFailure */);

    	//try{
	    	// We sync entity by entity
	    	// 2-way
    		syncTrips();
	    	// CityVisit
	    	// PlaceVisit
	    	// Transport

	    	// 1-way
	    	// City
	    	// Country
	    	// Currency
	    	// Language
	    	// Plug
	    	// Place
	    	
	    	// Other entities
	    	// Item --> ???
	    	// Note --> ???
	    	// User --> ???
	    /*} catch (OperationCanceledException e) {
	    	
	    }
	    catch (AuthenticatorException e) {
            syncResult.stats.numParseExceptions++;
		} catch (IOException e) {
            syncResult.stats.numIoExceptions++;
		} catch (final AuthenticationException e) {
            //am.invalidateAuthToken(getString(R.string.package_name), authtoken);
        } catch (final ParseException e) {
            syncResult.stats.numParseExceptions++;
        } catch (final JSONException e) {
            syncResult.stats.numParseExceptions++;
        }*/
    }
    
    private void syncTrips(){
    	ArrayList<Trip> trips = (ArrayList<Trip>) TripControl.readNotDeleted().getList();
    	
    	for (Trip trip: trips){
    		// Changes up
    		if(trip.isPendingDelete()){
    			String url = RESTResources.getInstance(this.context).getPlaceURL();
    			//RestMethod.delete(this.context, url, this.user, this.password);
    		}
    		if(trip.isPendingCreate()){
    			
    		}
    		else if (trip.isPendingUpdate()){
    			
    		}
    		// Changes down
    		String url = RESTResources.getInstance(this.context).getPlaceURL();
    		RestMethod.get(this.context, url, this.user, this.password);
    	}
    }
}
