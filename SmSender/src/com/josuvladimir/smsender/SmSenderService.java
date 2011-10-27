package com.josuvladimir.smsender;

import com.josuvladimir.util.Util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

public class SmSenderService extends Service {

	private static final String ACTION_SMS_SENT = "com.josuvladimir.util.";
	private LocationManager mLocationManager;
	@SuppressWarnings("unused")
	private LocationListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "SMService started", Toast.LENGTH_SHORT).show();
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		setLocationManager(mLocationManager);
	}

	private void setLocationManager(LocationManager locationManager) {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String provider = locationManager.getBestProvider(criteria, true);
//		locationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, listener);
		Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
		String destinationAddress = "+40742503976";
		String textMessage = "Latitude: " + lastKnownLocation.getLatitude() + " Longitude: " + lastKnownLocation.getLongitude();
		Util.sendSMS(this, destinationAddress, textMessage, ACTION_SMS_SENT);
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "SMService destroyed", Toast.LENGTH_SHORT).show();
		startService(new Intent(this,SmSenderService.class));
	}

}
