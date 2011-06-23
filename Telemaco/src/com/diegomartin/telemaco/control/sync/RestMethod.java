package com.diegomartin.telemaco.control.sync;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;
import android.util.Log;

public class RestMethod {
	// TODO: Thread aparte para las requests
	// TODO: Setear User-Agent para que las consultas a Wikipedia funcionen
	// TODO: Traer HTML en el GET
	// Reference: http://developer.android.com/resources/samples/SampleSyncAdapter/src/com/example/android/samplesync/client/NetworkUtilities.html
	// Source: http://www.gruposp2p.org/wordpress/?p=380
	
	public static final int TIMEOUT = 10000;
	public static final String CONTENT_TYPE = "application/json";
	public static final String ENCODING = "UTF-8";
	
	private static String exec(Context c, HttpUriRequest request, IRequestCallback callback){
		final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT);
    	HttpResponse response;
    	String content = "";
		try {
			response = httpClient.execute(request);
			content = callback.onRequestResponse(request, response, c);
		} catch (Exception ex) {
			ToastFacade.show(c, ex);
			callback.onRequestError(ex, c);
		}
		return content;
	}
	
	public static String get(Context c, final String url, final IRequestCallback callback) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        return exec(c, req, callback);
    }
	
	public static String get(Context c, final String url) {
		return get(c, url, new Processor());
    }
	
    public static void post (Context c, final String url, final JSONObject obj, final IRequestCallback callback) {
    	Log.i("HTTP POST", url);
    	Log.i("JSON", obj.toString());
    	
		HttpPost req = new HttpPost(url);
    	req.addHeader("Accept", CONTENT_TYPE);
    	req.addHeader("Content-Type", CONTENT_TYPE);
    	
	    StringEntity entity = null;
		try {
			entity = new StringEntity(obj.toString(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			ToastFacade.show(c, e);
		}
	    entity.setContentType(CONTENT_TYPE);
	    req.setEntity(entity);
	    
	    exec(c, req, callback);
    }
	
	public static void put (Context c, final String url, final JSONObject obj, final IRequestCallback callback) {
    	Log.i("HTTP PUT", url);
    	Log.i("JSON", obj.toString());
    	
		HttpPut req = new HttpPut(url);
		req.addHeader("Accept", CONTENT_TYPE);
		req.addHeader("Content-Type", CONTENT_TYPE);
		
	    StringEntity entity = null;
		try {
			entity = new StringEntity(obj.toString(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			ToastFacade.show(c, e);
		}
	    entity.setContentType(CONTENT_TYPE);
	    req.setEntity(entity);
	    
	    exec(c, req, callback);
    }
	
    public static void delete(Context c, final String url, final IRequestCallback callback) {
		Log.i("HTTP DELETE", url);
		HttpDelete req = new HttpDelete(url);
		req.addHeader("Accept", CONTENT_TYPE);
    	exec(c, req, callback);
    }
}
