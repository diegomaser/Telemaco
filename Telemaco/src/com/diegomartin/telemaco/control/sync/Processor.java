package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;

import com.diegomartin.telemaco.control.Utils;

public class Processor implements IRequestCallback{
	@Override
	public void onRequestResponse(HttpUriRequest request, HttpResponse response) {
		HttpEntity http = response.getEntity();
		try {
			String content = Utils.read(http.getContent());
			this.response(content);
		} catch (Exception e) {
			// TODO: Error handling  
			e.printStackTrace();
		}
	}
	
	@Override
	public void onRequestError(Throwable exception) {
		// TODO: Errores deben devolverse al Processor o al SyncAdapter?
		// Creo que deben capturarse aqui y mostrar Bundle con información del error
	}
	
	public void response(String content) throws JSONException {	}
}
