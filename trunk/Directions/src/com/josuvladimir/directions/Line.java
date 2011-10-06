package com.josuvladimir.directions;

import java.util.ArrayList;
import java.util.List;

import android.text.format.Time;

public class Line {
	Type mType;
	String mName;
	List<Schedule> mSchedules;
	private class Schedule {
		
		private Station mStation;
		private Time mTime;
		@SuppressWarnings("unused")
		public Schedule(Station station, Time time) {
			setStation(station);
			setTime(time);
		}
		public void setStation(Station mStation) {
			this.mStation = mStation;
		}
		public Station getStation() {
			return mStation;
		}
		public void setTime(Time mTime) {
			this.mTime = mTime;
		}
		@SuppressWarnings("unused")
		public Time getTime() {
			return mTime;
		}
	}
	
	public Line(String name, Type type) {
		mName = name;
		mType = type;
	}
	public List<Station> getStations() {
		List<Station> stations = new ArrayList<Station>();
		for (Schedule schedule : mSchedules) {
			stations.add(schedule.getStation());
		}
		return stations;
	}
	
}
