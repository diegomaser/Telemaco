package com.diegomartin.telemaco.view;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.control.ActionsFacade;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FacebookActivity extends Activity {
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        WebView webview = new WebView(this);	        
	        webview.setWebViewClient(new MyWebViewClient());
	        	        
	        webview.getSettings().setJavaScriptEnabled(true);   
	        webview.getSettings().setSupportZoom(true);      
	        webview.getSettings().setBuiltInZoomControls(true);
	        
	        String uri = "https://www.facebook.com/dialog/oauth?client_id=167808633292329&redirect_uri=http://www.facebook.com/connect/login_success.html&display=touch&response_type=token&scope=user_about_me,friends_about_me,user_checkins,friends_checkins,user_hometown,friends_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,friends_location,user_relationships,friends_relationships,user_relationship_details,friends_relationship_details,offline_access,user_birthday,friends_birthday,user_groups,friends_groups";

	        setContentView(webview);
	        webview.loadUrl(uri);
		}
		
		private void processURL(String url){
			int start = url.indexOf("access_token=") + 13;
			int end = url.indexOf("&");
			String accessToken = url.substring(start, end);
			
			SharedPreferences prefs = getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);
			prefs.edit().putString(ActionsFacade.EXTRA_ACCESS_TOKEN, accessToken).commit();
		}
		
		private class MyWebViewClient extends WebViewClient {
		    @Override
		    public void onLoadResource(WebView view, String url) {
		    	//view.loadUrl(url);
		    	
		    	if (url.startsWith("http://www.facebook.com/connect/login_success.html")){
        			processURL(url);
        			finish();
        		}
		    }
		    
		    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		        ToastFacade.show(view.getContext(), "Error "+errorCode + ". " + description);
		        finish();
		    }
		}
}
