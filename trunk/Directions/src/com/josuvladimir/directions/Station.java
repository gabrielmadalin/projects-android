package com.josuvladimir.directions;

import com.josuvladimir.util.Util;

import android.location.Location;

public class Station {
	private String mName;
	private Location mLocation;
	private Type mType;
	public Station() {
	}
	public static Station getRandomStation() {
		Station station = new Station();
		station.setName(Util.getRandomString(7));
		return station;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getName() {
		return mName;
	}
	public void setLocation(Location mLocation) {
		this.mLocation = mLocation;
	}
	public Location getLocation() {
		return mLocation;
	}
	public void setType(Type mType) {
		this.mType = mType;
	}
	public Type getType() {
		return mType;
	}
}
