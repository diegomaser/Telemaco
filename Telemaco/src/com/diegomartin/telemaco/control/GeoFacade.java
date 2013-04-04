package com.diegomartin.telemaco.control;

import com.diegomartin.telemaco.R;
import com.diegomartin.telemaco.view.ToastFacade;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

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
		public void onLocationChanged(Location l) {
			location = l;
			if (location != null) Log.i("GEO", "New location" + location.toString());
		}
	};

	private GeoFacade(Context c){
		startPositioning(c);
	}
	
	public static GeoFacade getInstance(Context c){
		if (instance==null) instance = new GeoFacade(c);
		return instance;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void startPositioning(Context c){
		ToastFacade.show(c, c.getString(R.string.updating_location));
		
		// Los saltos en el gps pueden eliminarse con el uso de 3 proveedores,
		// unos más exactos que otros, ver ponencia en Google I/O
		// Google I/O 2010 - A beginner's guide to Android - http://www.youtube.com/watch?v=yqCj83leYRE
		
		this.locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		final Criteria criteria = new Criteria();
		int freq = 2*60000; // 2 minutes in milliseconds
		int distance = 100; // meters
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		
		String provider = this.locationManager.getBestProvider(criteria, true);
		this.locationManager.requestLocationUpdates(provider, freq, distance, this.locationListener);

		this.location = this.locationManager.getLastKnownLocation(provider);
		if (location != null) Log.i("GEO", "Last location" + location.toString());
	}
	
	/*private void stopPositioning(){
		this.locationManager.removeUpdates(this.locationListener);
	}*/

	public float distanceTo(double lat, double lng){
		Location l = this.getLocation();
		if (l != null){
			Location dest = new Location("destination");
			dest.setLatitude(lat);
			dest.setLongitude(lng);
			return l.distanceTo(dest);
		}
		else return -1;
	}
}
