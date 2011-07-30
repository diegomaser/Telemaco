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
	        webview.setWebViewClient(new WebViewClient() {
	        	@Override
	        	public void onLoadResource(WebView view, String url) {
	        		if (url.startsWith("http://www.facebook.com/connect/login_success.html")){
	        			processURL(url);
	        			finish();
	        		}
	        		else view.loadUrl(url);
	        	}
	        });
	        
	        String uri = "https://www.facebook.com/dialog/oauth?client_id=167808633292329&redirect_uri=http://www.facebook.com/connect/login_success.html&display=touch&response_type=token&scope=user_about_me,friends_about_me,user_checkins,friends_checkins,user_hometown,friends_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,friends_location,user_relationships,friends_relationships,user_relationship_details,friends_relationship_details,offline_access,user_birthday,friends_birthday,user_groups,friends_groups";	        
	        webview.loadUrl(uri);
	        setContentView(webview);
	        
		}
		
		private void processURL(String url){
			int start = url.indexOf("access_token=");
			int end = url.indexOf("&");
			String accessToken = url.substring(start, end);
			
			SharedPreferences prefs = getSharedPreferences(getString(R.string.package_name), Context.MODE_PRIVATE);
			prefs.edit().putString(ActionsFacade.EXTRA_ACCESS_TOKEN, accessToken).commit();
		}
}