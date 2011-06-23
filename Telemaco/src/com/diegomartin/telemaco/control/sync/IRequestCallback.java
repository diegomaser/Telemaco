package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;

public interface IRequestCallback {
	public String onRequestResponse(HttpUriRequest request, HttpResponse response, Context c);
	public void onRequestError(Throwable exception, Context c);
}
