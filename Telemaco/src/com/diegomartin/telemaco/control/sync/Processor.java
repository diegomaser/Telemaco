package com.diegomartin.telemaco.control.sync;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;

import com.diegomartin.telemaco.control.FileUtils;
import com.diegomartin.telemaco.view.ToastFacade;

public class Processor implements IRequestCallback{
	@Override
	public String onRequestResponse(HttpUriRequest request, HttpResponse response, Context c) {
		HttpEntity http = response.getEntity();
		String content = "";
		try {
			content = FileUtils.read(http.getContent(), c);
		} catch (Exception e) {
			ToastFacade.show(c, e);
		}
		return content;
	}
	
	@Override
	public void onRequestError(Throwable e, Context c) {
		// TODO: Errores deben devolverse al Processor o al SyncAdapter?
		ToastFacade.show(c, e.getMessage());
	}
}
