package com.diegomartin.telemaco.control.sync;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

import org.json.JSONObject;

import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;
import android.util.Log;

public class RestMethod {
	// TODO: Thread aparte para las requests
	// Reference: http://developer.android.com/resources/samples/SampleSyncAdapter/src/com/example/android/samplesync/client/NetworkUtilities.html
	// Source: http://www.gruposp2p.org/wordpress/?p=380
	
	public static final int TIMEOUT = 10000;
	public static final String CONTENT_TYPE = "application/json";
	public static final String ENCODING = "UTF-8";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
	    
    private static DefaultHttpClient getHttpClient(){
   		DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT);
    	HttpProtocolParams.setUserAgent(httpClient.getParams(), USER_AGENT);
    	return httpClient;
    }
    
    private static DefaultHttpClient getHttpClient(String user, String password){
    	DefaultHttpClient httpClient = getHttpClient();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(null, -1), credentials);
    	return httpClient;
    }
	
	private static String exec(Context c, HttpUriRequest request, IRequestCallback callback, String user, String password){
    	DefaultHttpClient httpClient = getHttpClient(user, password);
    	
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
	
	public static int login(Context c, String url, String user, String password){
        HttpGet req = new HttpGet(url);
    	DefaultHttpClient httpClient = getHttpClient(user, password);
    	HttpResponse response;
    	int statusCode = -1;
    	
		try {
			response = httpClient.execute(req);
			statusCode = response.getStatusLine().getStatusCode();			
		} catch (Exception ex) {
			ToastFacade.show(c, ex);
		}

		return statusCode;
	}
	
	private static String exec(Context c, HttpUriRequest request, IRequestCallback callback){
    	DefaultHttpClient httpClient = getHttpClient();
    	
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
		
	// GET
	public static String get(Context c, final String url, final IRequestCallback callback) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        String content = exec(c, req, callback);
		Log.i("HTTP GET Response", content);
        return content;
    }
	
	public static String get(Context c, final String url) {
		return get(c, url, new Processor());
    }

	// GET auth
	public static String get(Context c, final String url, final IRequestCallback callback, String user, String password) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        String content = exec(c, req, callback, user, password);
		Log.i("HTTP GET Response", content);
        return content;
    }
	
	public static String get(Context c, final String url, String user, String password) {
		return get(c, url, new Processor(), user, password);
    }
	
	// POST
    public static void post (Context c, final String url, final JSONObject obj, final IRequestCallback callback, String user, String password) {
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

	    exec(c, req, callback, user, password);
    }
    
    public static void post (Context c, final String url, final JSONObject obj, String user, String password) {
    	post(c, url, obj, new Processor(), user, password);
    }
	
    // PUT
	public static void put (Context c, final String url, final JSONObject obj, final IRequestCallback callback, String user, String password) {
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
	    exec(c, req, callback, user, password);
    }
	
	public static void put (Context c, final String url, final JSONObject obj, String user, String password) {
		put(c, url, obj, new Processor(), user, password);
	}
	
	// DELETE
    public static void delete(Context c, final String url, final IRequestCallback callback, String user, String password) {
		Log.i("HTTP DELETE", url);
		HttpDelete req = new HttpDelete(url);
		req.addHeader("Accept", CONTENT_TYPE);
    	exec(c, req, callback, user, password);
    }
    
    public static void delete(Context c, final String url, String user, String password) {
    	delete(c, url, new Processor(), user, password);
    }
}
