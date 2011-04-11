package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;

public interface IRequestCallback {
	public void onRequestResponse(HttpResponse response);
	public void onRequestError(Throwable exception);
}
