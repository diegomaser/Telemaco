package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.control.sync.SyncAdapter;
import com.diegomartin.telemaco.view.HTMLViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActionsFacade {
	// Information source: http://developer.android.com/guide/appendix/g-app-intents.html
	
	public final static String EXTRA_TRIP = "trip";
	public final static String EXTRA_CITY = "city";
	public final static String EXTRA_COUNTRY = "country";
	public final static String EXTRA_NOTE = "note";
	
	private static ActionsFacade instance;
	
	private ActionsFacade(){}
	
	public static ActionsFacade getInstance(){
		if (instance==null) instance = new ActionsFacade();
		return instance;
	}
	
	public Intent launchMaps(double lat, double lng){
		//geo:latitude,longitude
		//geo:latitude,longitude?z=zoom
		// If doesnt work, check: http://stackoverflow.com/questions/2553251/launch-google-maps-app
		Intent maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lng));
		return maps;
	}
	
	public Intent launchMaps(String q){
		//geo:0,0?q=my+street+address
		//geo:0,0?q=business+near+city
		Intent maps = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+q));
		return maps;
	}
	
	public Intent launchStreetView(double lat, double lng){
		// 1,yaw,,pitch,zoom&mz=21
		// yaw = direccion, pitch = angulo de vision (negativo es arriba, 90 es mirar al suelo),
		// zoom = zoom en la imagen streetview, mz = zoom en el mapa en caso de ir al mapa después de abrir la aplicación de mapas
		Intent streetview = new Intent(
							android.content.Intent.ACTION_VIEW,
							Uri.parse("google.streetview:cbll="+lat+","+lng+
							"&cbp=1,99.56,,1,-5.27&mz=21"));
		return streetview;
	}
	
	public Intent launchNavigation(double lat, double lng){
		//Intent { act=android.intent.action.VIEW dat=google.navigation:///?q=Some%20place cmp=brut.googlemaps/com.google.android.maps.driveabout.app.NavigationActivity }
		final double distance = GeoFacade.getInstance().distanceTo(lat, lng);
		Uri location;
		
		if (distance>1000) location = Uri.parse("google.navigation:ll=" + lat + "," + lng);
		else location = Uri.parse("google.navigation:ll=" + lng + "," + lat + "&mode=w");
		
		return new Intent(Intent.ACTION_VIEW, location);
	}
	
	public Intent launchNavigation(String q){
		// No sabemos si es mejor ir andando o en coche
		Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + q));
		//Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + q + "&mode=w"));
		return navigation;
	}
	
	// public Intent launchPlaces(double lat, double lng){ }
	
	public Intent share(String subject, String txt){
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_SUBJECT, subject);
		share.putExtra(Intent.EXTRA_TEXT, txt);
		return Intent.createChooser(share, subject);
	}
	
	public void launchSync(Context context){
		SyncAdapter sa = new SyncAdapter(context, false);
		sa.launchSync();
	}
	
	public Intent launchExternalBrowser(String url){
		return new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
	}
	
	public Intent launchBrowserURL(Activity previousActivity, String url){
		Intent browser = new Intent(previousActivity, HTMLViewActivity.class);
		browser.putExtra("uri", url);
		return browser;
	}
	
	public Intent launchBrowserHTML(Activity previousActivity, String html){
		Intent browser = new Intent(previousActivity, HTMLViewActivity.class);
		browser.putExtra("source", html);
		return browser;
	}
	
	public Intent launchHelp(Activity previousActivity){
		return this.launchBrowserURL(previousActivity, "file:///android_asset/help/help.html");
	}
}