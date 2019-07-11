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



//	{
//		"secondaryname": "厦门市",
//			"conditionDay": "小雨",
//			"conditionIdDay": "7",
//			"conditionIdNight": "7",
//			"conditionNight": "小雨",
//			"humidity": "90",
//			"predictDate": "2019-07-10",
//			"tempDay": "32",
//			"tempNight": "25",
//			"updatetime": "2019-07-10 15:10:00",
//			"windDegreesDay": "225",
//			"windDegreesNight": "225",
//			"windDirDay": "西南风",
//			"windDirNight": "西南风",
//			"windLevelDay": "3-4",
//			"windLevelNight": "3-4"
//	}



	private String secondaryname;
	private String conditionDay;
	private String conditionIdDay;
	private String conditionIdNight;
	private String conditionNight;
	private String humidity;
	private String predictDate;
	private String tempDay;
	private String tempNight;
	private String updatetime;
	private String windDegreesDay;
	private String windDegreesNight;
	private String windDirDay;
	private String windDirNight;
	private String windLevelDay;
	private String windLevelNight;
	public void setSecondaryname(String secondaryname) {
		this.secondaryname = secondaryname;
	}
	public String getSecondaryname() {
		return secondaryname;
	}

	public void setConditionDay(String conditionDay) {
		this.conditionDay = conditionDay;
	}
	public String getConditionDay() {
		return conditionDay;
	}

	public void setConditionIdDay(String conditionIdDay) {
		this.conditionIdDay = conditionIdDay;
	}
	public String getConditionIdDay() {
		return conditionIdDay;
	}

	public void setConditionIdNight(String conditionIdNight) {
		this.conditionIdNight = conditionIdNight;
	}
	public String getConditionIdNight() {
		return conditionIdNight;
	}

	public void setConditionNight(String conditionNight) {
		this.conditionNight = conditionNight;
	}
	public String getConditionNight() {
		return conditionNight;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getHumidity() {
		return humidity;
	}

	public void setPredictDate(String predictDate) {
		this.predictDate = predictDate;
	}
	public String getPredictDate() {
		return predictDate;
	}

	public void setTempDay(String tempDay) {
		this.tempDay = tempDay;
	}
	public String getTempDay() {
		return tempDay;
	}

	public void setTempNight(String tempNight) {
		this.tempNight = tempNight;
	}
	public String getTempNight() {
		return tempNight;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdatetime() {
		return updatetime;
	}

	public void setWindDegreesDay(String windDegreesDay) {
		this.windDegreesDay = windDegreesDay;
	}
	public String getWindDegreesDay() {
		return windDegreesDay;
	}

	public void setWindDegreesNight(String windDegreesNight) {
		this.windDegreesNight = windDegreesNight;
	}
	public String getWindDegreesNight() {
		return windDegreesNight;
	}

	public void setWindDirDay(String windDirDay) {
		this.windDirDay = windDirDay;
	}
	public String getWindDirDay() {
		return windDirDay;
	}

	public void setWindDirNight(String windDirNight) {
		this.windDirNight = windDirNight;
	}
	public String getWindDirNight() {
		return windDirNight;
	}

	public void setWindLevelDay(String windLevelDay) {
		this.windLevelDay = windLevelDay;
	}
	public String getWindLevelDay() {
		return windLevelDay;
	}

	public void setWindLevelNight(String windLevelNight) {
		this.windLevelNight = windLevelNight;
	}
	public String getWindLevelNight() {
		return windLevelNight;
	}
}
