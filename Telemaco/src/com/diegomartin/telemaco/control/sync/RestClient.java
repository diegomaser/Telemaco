/* Source: http://www.gruposp2p.org/wordpress/?p=380 */

package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import android.util.Log;

public class RestClient {
    public interface RequestCallback {
    	public void onError(Throwable exception);
    	public void onResponseReceived(HttpResponse response);
    }

    public static void doGet(final String url, final RequestCallback callback) {
    	Log.i("doGet", " - url: " + url);
    	final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
    	Thread thread = new Thread(){
    		public void run() {
    	        HttpGet httpget = new HttpGet(url);
    	        HttpResponse response;
    	        try {
    	            response = httpClient.execute(httpget);
    	            callback.onResponseReceived(response);
    	        } catch (Exception ex) {
    	        	callback.onError(ex);
    	        }
    		}
    	};
    	thread.start();
    }

    public static void doPost (final String url,final JSONObject json, final RequestCallback callback) {
    	Log.i("doPost a url: " + url, "- JSON: " + json.toString());
    	final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
    	Thread thread = new Thread(){
    		public void run() {
    			HttpPost httpPost = new HttpPost(url);
    	    	httpPost.addHeader("Accept", "application/json");
    	    	httpPost.addHeader("Content-Type", "application/json");
    	    	try {
    	    	    StringEntity entity = new StringEntity(json.toString(), "UTF-8");
    	    	    entity.setContentType("application/json");
    	    	    httpPost.setEntity(entity);
    	    	    // execute is a blocking call, it's best to call this code in a thread separate from the ui's
    	    	    HttpResponse response = httpClient.execute(httpPost);
    	    	    callback.onResponseReceived(response);
    	    	}
    	    	catch (Exception ex) {
    	    		callback.onError(ex);
    	    	}
    		}
    	};
    	thread.start();
    }

    public static void doPut (final String url,final JSONObject json,final RequestCallback callback) {
    	Log.i("doPut a url: " + url, "- JSON: " + json.toString());
    	final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
    	Thread thread = new Thread(){
    		public void run() {
    			HttpPut httpPut = new HttpPut(url);
    			httpPut.addHeader("Accept", "application/json");
    			httpPut.addHeader("Content-Type", "application/json");
    	    	try {
    	    	    StringEntity entity = new StringEntity(json.toString(), "UTF-8");
    	    	    entity.setContentType("application/json");
    	    	    httpPut.setEntity(entity);
    	    	    HttpResponse response = httpClient.execute(httpPut);
    	    	    callback.onResponseReceived(response);
    	    	}
    	    	catch (Exception ex) {
    	    		callback.onError(ex);
    	    	}
    		}
    	};
    	thread.start();
    }

    public static void doDelete (final String url, final RequestCallback callback) {
    	Log.i("doDelete" , " - url: " + url);
    	final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
    	Thread thread = new Thread(){
    		public void run() {
    			HttpDelete httpDelete = new HttpDelete(url);
    			httpDelete.addHeader("Accept", "application/json");
    	    	try {
    	    	    HttpResponse response = httpClient.execute(httpDelete);
    	    	    callback.onResponseReceived(response);
    	    	}
    	    	catch (Exception ex) {
    	    		callback.onError(ex);
    	    	}
    		}
    	};
    	thread.start();
    }
}
