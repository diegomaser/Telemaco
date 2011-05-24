package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface IRequestCallback {
	public void onRequestResponse(HttpUriRequest request, HttpResponse response);
	public void onRequestError(Throwable exception);
}
