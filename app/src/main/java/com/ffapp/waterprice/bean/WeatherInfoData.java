package com.ffapp.waterprice.bean;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfoData extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * counname : 中国
	 * province : 福建省
	 * cityName : 厦门市
	 * areaName : 思明区
	 * condition : 晴
	 * icon : 0
	 * iconSrc : http://api.dev.gk100.ff-cloud.net/public/images/weather/W0.png
	 * temp : 22
	 * humidity : 47
	 * windDir : 东南风
	 * windLevel : 2
	 */

	private String counname;
	private String province;
	private String cityName;
	private String areaName;
	private String condition;
	private String icon;
	private String iconSrc;
	private String temp;
	private String humidity;
	private String windDir;
	private String windLevel;

	private ArrayList<WeatherInfoDataHour> hourly;
	private List<WeatherInfoDataDay> forecast;

	public String getCounname() {
		return counname;
	}

	public void setCounname(String counname) {
		this.counname = counname;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconSrc() {
		return iconSrc;
	}

	public void setIconSrc(String iconSrc) {
		this.iconSrc = iconSrc;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindDir() {
		return windDir;
	}

	public void setWindDir(String windDir) {
		this.windDir = windDir;
	}

	public String getWindLevel() {
		return windLevel;
	}

	public void setWindLevel(String windLevel) {
		this.windLevel = windLevel;
	}

	public ArrayList<WeatherInfoDataHour> getHourly() {
		return hourly;
	}

	public void setHourly(ArrayList<WeatherInfoDataHour> hourly) {
		this.hourly = hourly;
	}

	public List<WeatherInfoDataDay> getForecast() {
		return forecast;
	}

	public void setForecast(List<WeatherInfoDataDay> forecast) {
		this.forecast = forecast;
	}
}
