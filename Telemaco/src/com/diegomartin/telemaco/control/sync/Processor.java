package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.content.Context;
import android.util.Log;

import com.diegomartin.telemaco.control.FileUtils;

public class Processor implements IRequestCallback{
	private String content;
	private int code;
	
	public Processor(){
		this.setContent("");
		this.setCode(-1);
	}
	
	@Override
	public void onRequestResponse(HttpResponse response, Context c) {
		try{
			HttpEntity http = response.getEntity();
			this.setCode(response.getStatusLine().getStatusCode());
			this.setContent(FileUtils.read(http.getContent(), c));
		} catch (Exception e) {
			Log.e("HTTP Response Error", e.getMessage(), e);
		}
	}
	
	@Override
	public void onRequestError(Throwable e) {
		Log.e("HTTP Response Error", e.getMessage(), e);
	}

	private void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	private void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
