package com.ffapp.waterprice.bean;


public class ManageLocationListData extends BasisBean {

	private double latitude;
	private double longtitude;

	private long time;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
