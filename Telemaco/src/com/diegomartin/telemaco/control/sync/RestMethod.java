package com.diegomartin.telemaco.control.sync;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;
import android.util.Log;

public class RestMethod {
	// Reference: http://developer.android.com/resources/samples/SampleSyncAdapter/src/com/example/android/samplesync/client/NetworkUtilities.html
	
	public static final int TIMEOUT = 10000;
	public static final String CONTENT_TYPE = "application/json";
	public static final String ENCODING = "UTF-8";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
	
	// auth
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
    
    private static IRequestCallback processRequest(Context c, HttpUriRequest request, DefaultHttpClient httpClient){    	    	
        final ExecutorService service;
        final Future<IRequestCallback> task;
        IRequestCallback callback = new Processor();

        MyThread thread = new MyThread(c, request, httpClient);
        service = Executors.newFixedThreadPool(1);        
        task    = service.submit(thread);
        try {
			callback = task.get();
		} catch (Exception e) {
			ToastFacade.show(c, c.getString(R.string.error_connecting));
		}
		return callback;
    }
    
    public static class MyThread implements Callable<IRequestCallback>{
    	private IRequestCallback callback;
    	private DefaultHttpClient httpClient;
    	private HttpUriRequest request;
    	private Context context;
    	
		public MyThread(Context context, HttpUriRequest request, DefaultHttpClient httpClient){
			this.httpClient = httpClient;
			this.request = request;
			this.context = context;
			this.callback = new Processor();
		}
		
		public IRequestCallback call() throws SocketException {
			try{
				HttpResponse response = this.httpClient.execute(this.request);
				this.callback.onRequestResponse(response, context);
			}
			catch(SocketException e){
				throw e;
			}
			catch(Exception ex){
				this.callback.onRequestError(ex);
			}
			return this.callback;
		}
	}
	
    // exec
	private static String exec(Context c, HttpUriRequest request, String user, String password){
		final DefaultHttpClient httpClient = getHttpClient(user, password);
		IRequestCallback callback = processRequest(c, request, httpClient);
    	String content = callback.getContent();
		return content;
	}
	
	private static String exec(Context c, HttpUriRequest request){
		final DefaultHttpClient httpClient = getHttpClient();
		IRequestCallback callback = processRequest(c, request, httpClient);
    	String content = callback.getContent();
		return content;
	}
	
	// execCode
	private static int execCode(Context c, HttpUriRequest request, String user, String password){
		final DefaultHttpClient httpClient = getHttpClient(user, password);
		IRequestCallback callback = processRequest(c, request, httpClient);
    	int statusCode = callback.getCode();
		return statusCode;
	}
	
	private static int execCode(final Context c, final HttpUriRequest request){
		final DefaultHttpClient httpClient = getHttpClient();
		IRequestCallback callback = processRequest(c, request, httpClient);
    	int statusCode = callback.getCode();
		return statusCode;
	}
	
	// Login
	public static int getCode(Context c, String url, String user, String password){
		Log.i("HTTP GET", url);
		HttpGet req = new HttpGet(url);
		int statusCode = execCode(c, req, user, password);
		Log.i("HTTP GET Code", String.valueOf(statusCode));
		return statusCode;
	}
	
	// GET	
	public static String get(Context c, final String url) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        String content = exec(c, req);
		Log.i("HTTP GET Response", content);
        return content;
    }
	
	public static String get(Context c, final String url, String user, String password) {
		Log.i("HTTP GET", url);
        HttpGet req = new HttpGet(url);
        String content = exec(c, req, user, password);
		Log.i("HTTP GET Response", content);
        return content;
    }
	
	// POST    
    public static int post (Context c, final String url, final JSONObject obj, String user, String password) {
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

	    return execCode(c, req, user, password);
    }
    
    public static int post (Context c, final String url, final JSONObject obj) {
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

	    return execCode(c, req);
    }
		
	public static int put (Context c, final String url, final JSONObject obj, String user, String password) {
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
	    return execCode(c, req, user, password);
	}
	
	// DELETE
    public static int delete(Context c, final String url, String user, String password) {
		Log.i("HTTP DELETE", url);
		HttpDelete req = new HttpDelete(url);
		req.addHeader("Accept", CONTENT_TYPE);
    	return execCode(c, req, user, password);
    }
}
