package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;

import android.content.Context;

public interface IRequestCallback {
	public void onRequestResponse(HttpResponse response, Context c);
	public void onRequestError(Throwable exception);
	public int getCode();
	public String getContent();
}
