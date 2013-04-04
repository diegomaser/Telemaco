package com.diegomartin.telemaco.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HTMLViewActivity extends Activity {
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        WebView webview = new WebView(this);
	        webview.setWebViewClient(new WebViewClient());
	        getWindow().requestFeature(Window.FEATURE_PROGRESS);

	        webview.getSettings().setJavaScriptEnabled(true);   
	        webview.getSettings().setSupportZoom(true);      
	        webview.getSettings().setBuiltInZoomControls(true);
	        
	        Bundle receive = getIntent().getExtras();
	        String uri = (String) receive.getString("uri");
	        String source = (String) receive.getString("source");
	        
	        if (uri != null){
		        webview.loadUrl(uri);
	        }
	        else if (source != null){
		        webview.loadData(source, "text/html", "utf-8");
	        }
	        
	        setContentView(webview);
		}
}