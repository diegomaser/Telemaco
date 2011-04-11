package com.diegomartin.telemaco.control;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoFacade {
	private static GeoFacade instance;
	private Location location;
	private LocationManager locationManager;
	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) { }
		
		@Override
		public void onProviderEnabled(String provider) { }
		
		@Override
		public void onProviderDisabled(String provider) { }
		
		@Override
		public void onLocationChanged(Location location) {
			GeoFacade.getInstance().setLocation(location);
		}
	};

	private GeoFacade(){ }
	
	public static GeoFacade getInstance(){
		if (instance==null) instance = new GeoFacade();
		return instance;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void startPositioning(Context c){
		this.locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		final Criteria criteria = new Criteria();
		int freq = 2*60000; // 2 minutes in milliseconds
		int distance = 100; // meters
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(false);
		
		String provider = this.locationManager.getBestProvider(criteria, true);
		this.locationManager.requestLocationUpdates(provider, freq, distance, this.locationListener);
		// Los saltos en el gps pueden eliminarse con el uso de 3 proveedores,
		// unos más exactos que otros, ver ponencia en Google I/O
		// Google I/O 2010 - A beginner's guide to Android - http://www.youtube.com/watch?v=yqCj83leYRE
	}
	
	public void stopPositioning(){
		this.locationManager.removeUpdates(this.locationListener);
	}
	
	// TODO: NFC tags y QR images
	
	public float distanceBetween(double lat1, double lng1, double lat2, double lng2){
		float[] results = {};
		Location.distanceBetween(lat1, lng1, lat2, lng2, results);
		return results[0];
	}
	
	public float distanceTo(double lat, double lng){
		float[] results = {};
		Location.distanceBetween(this.getLocation().getLatitude(), this.getLocation().getLongitude(), lat, lng, results);
		return results[0];
	}
}
