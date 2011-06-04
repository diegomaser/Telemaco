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
	
	private static String exec(HttpUriRequest request, IRequestCallback callback){
		final HttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT);
    	HttpResponse response;
    	String content = "";
		try {
			response = httpClient.execute(request);
			content = callback.onRequestResponse(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			callback.onRequestError(ex);
		}
		return content;
	}
	
	public static String get(final String url, final IRequestCallback callback) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        return exec(req, callback);
    }
	
	public static String get(final String url) {
		return get(url, new Processor());
    }
	
    public static void post (final String url, final JSONObject obj, final IRequestCallback callback) {
    	Log.i("HTTP POST", url);
    	Log.i("JSON", obj.toString());
    	
		HttpPost req = new HttpPost(url);
    	req.addHeader("Accept", CONTENT_TYPE);
    	req.addHeader("Content-Type", CONTENT_TYPE);
    	
	    StringEntity entity = null;
		try {
			entity = new StringEntity(obj.toString(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    entity.setContentType(CONTENT_TYPE);
	    req.setEntity(entity);
	    
	    exec(req, callback);
    }
	
	public static void put (final String url, final JSONObject obj, final IRequestCallback callback) {
    	Log.i("HTTP PUT", url);
    	Log.i("JSON", obj.toString());
    	
		HttpPut req = new HttpPut(url);
		req.addHeader("Accept", CONTENT_TYPE);
		req.addHeader("Content-Type", CONTENT_TYPE);
		
	    StringEntity entity = null;
		try {
			entity = new StringEntity(obj.toString(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    entity.setContentType(CONTENT_TYPE);
	    req.setEntity(entity);
	    
	    exec(req, callback);
    }
	
    public static void delete (final String url, final IRequestCallback callback) {
		Log.i("HTTP DELETE", url);
		HttpDelete req = new HttpDelete(url);
		req.addHeader("Accept", CONTENT_TYPE);
    	exec(req, callback);
    }
}
