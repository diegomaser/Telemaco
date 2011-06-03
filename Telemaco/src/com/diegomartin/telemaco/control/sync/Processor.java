package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import com.diegomartin.telemaco.control.FileUtils;

public class Processor implements IRequestCallback{
	@Override
	public String onRequestResponse(HttpUriRequest request, HttpResponse response) {
		HttpEntity http = response.getEntity();
		String content = "";
		try {
			content = FileUtils.read(http.getContent());
		} catch (Exception e) {
			// TODO: Error handling  
			e.printStackTrace();
		}
		return content;
	}
	
	@Override
	public void onRequestError(Throwable exception) {
		// TODO: Errores deben devolverse al Processor o al SyncAdapter?
		// Creo que deben capturarse aqui y mostrar Bundle con información del error
	}
}
