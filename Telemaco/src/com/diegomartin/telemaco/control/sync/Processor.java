package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpResponse;

public class Processor implements IRequestCallback{
	@Override
	public void onRequestResponse(HttpResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onRequestError(Throwable exception) {
		// TODO Auto-generated method stub
		// Errores deben devolverse al Processor o al SyncAdapter?
	}
}
