package com.diegomartin.telemaco.control.sync;

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
import android.util.Log;

import java.io.IOException;
import java.util.Date;

/**
 * SyncAdapter implementation for syncing sample SyncAdapter contacts to the
 * platform ContactOperations provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "Sync";
    private static final String accountType = "com.diegomartin.telemaco";
    private static final String user = "User";
    private final AccountManager accountManager;
    private final Context context;
    private Date lastUpdated;

    public SyncAdapter(Context c, boolean autoInitialize) {
        super(c, autoInitialize);
        context = c;
        accountManager = AccountManager.get(context);
    }
    
    public void launchSync(){
		Account account =  null;
		for(Account a : accountManager.getAccountsByType(accountType)) {
		    if (accountManager.getUserData(a, user) != null) {
		        account = a;
		        break;
		    }
		}
		Bundle extras = new Bundle();
		//extras.putString(EXTRA_mystuff, myvalue);
		ContentResolver.requestSync(account, accountType, extras);
	}

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //List<User> users;
        //List<Status> statuses;
        String authtoken = null;
        
        try {
        	Log.i("SyncAdapter", "");
			authtoken = accountManager.blockingGetAuthToken(account, accountType, true /* notifyAuthFailure */);
			// changes 
		} catch (OperationCanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*try {
            // use the account manager to request the credentials
            
            
            // fetch updates from the sample service over the cloud
            users = NetworkUtilities.fetchFriendUpdates(account, authtoken, mLastUpdated);
            // update the last synced date.
            mLastUpdated = new Date();
            // update platform contacts.
            Log.d(TAG, "Calling contactManager's sync contacts");
            ContactManager.syncContacts(mContext, account.name, users);
            // fetch and update status messages for all the synced users.
            statuses = NetworkUtilities.fetchFriendStatuses(account, authtoken);
            ContactManager.insertStatuses(mContext, account.name, statuses);
        } catch (final AuthenticatorException e) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "AuthenticatorException", e);
        } catch (final OperationCanceledException e) {
            Log.e(TAG, "OperationCanceledExcetpion", e);
        } catch (final IOException e) {
            Log.e(TAG, "IOException", e);
            syncResult.stats.numIoExceptions++;
        } catch (final AuthenticationException e) {
            mAccountManager.invalidateAuthToken(Constants.ACCOUNT_TYPE, authtoken);
            syncResult.stats.numAuthExceptions++;
            Log.e(TAG, "AuthenticationException", e);
        } catch (final ParseException e) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "ParseException", e);
        } catch (final JSONException e) {
            syncResult.stats.numParseExceptions++;
            Log.e(TAG, "JSONException", e);
        }*/
    }
}
