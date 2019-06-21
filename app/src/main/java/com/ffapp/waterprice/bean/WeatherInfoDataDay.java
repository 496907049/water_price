package com.ffapp.waterprice.bean;

import my.TimeUtils;

public class WeatherInfoDataDay extends BasisBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * conditionDay : 晴
	 * conditionIdDay : 0
	 * conditionIdNight : 31
	 * conditionNight : 多云
	 * moonphase : WaxingCrescent
	 * moonrise : 2019-03-12 09:50:00
	 * moonset : 2019-03-12 23:09:00
	 * predictDate : 2019-03-12
	 * sunrise : 2019-03-12 06:20:00
	 * sunset : 2019-03-12 18:15:00
	 * tempDay : 23
	 * tempNight : 12
	 * updatetime : 2019-03-12 23:05:00
	 * windDegreesDay : 135
	 * windDegreesNight : 135
	 * windDirDay : 东南风
	 * windDirNight : 东南风
	 * windLevelDay : 3-4
	 * windLevelNight : 3-4
	 * windSpeedDay : 5.6
	 * windSpeedNight : 5.6
	 * conditionIdDaySrc : http://api.dev.gk100.ff-cloud.net/public/images/weather/W0.png
	 * conditionIdNightSrc : http://api.dev.gk100.ff-cloud.net/public/images/weather/W31.png
	 */

	private String conditionDay;
	private String conditionIdDay;
	private String conditionIdNight;
	private String conditionNight;
	private String moonphase;
	private String moonrise;
	private String moonset;
	private String predictDate;
	private String sunrise;
	private String sunset;
	private String tempDay;
	private String tempNight;
	private String updatetime;
	private String windDegreesDay;
	private String windDegreesNight;
	private String windDirDay;
	private String windDirNight;
	private String windLevelDay;
	private String windLevelNight;
	private String windSpeedDay;
	private String windSpeedNight;
	private String conditionIdDaySrc;
	private String conditionIdNightSrc;

	public String getConditionDay() {
		return conditionDay;
	}

	public void setConditionDay(String conditionDay) {
		this.conditionDay = conditionDay;
	}

	public String getConditionIdDay() {
		return conditionIdDay;
	}

	public void setConditionIdDay(String conditionIdDay) {
		this.conditionIdDay = conditionIdDay;
	}

	public String getConditionIdNight() {
		return conditionIdNight;
	}

	public void setConditionIdNight(String conditionIdNight) {
		this.conditionIdNight = conditionIdNight;
	}

	public String getConditionNight() {
		return conditionNight;
	}

	public void setConditionNight(String conditionNight) {
		this.conditionNight = conditionNight;
	}

	public String getMoonphase() {
		return moonphase;
	}

	public void setMoonphase(String moonphase) {
		this.moonphase = moonphase;
	}

	public String getMoonrise() {
		return moonrise;
	}

	public void setMoonrise(String moonrise) {
		this.moonrise = moonrise;
	}

	public String getMoonset() {
		return moonset;
	}

	public void setMoonset(String moonset) {
		this.moonset = moonset;
	}

	public String getPredictDate() {
		return predictDate;
	}

	public String getPredictDateStr() {
		return TimeUtils.formatToFomat(predictDate,"yyyy-MM-dd","MM-dd");
	}

	public void setPredictDate(String predictDate) {
		this.predictDate = predictDate;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getTempDay() {
		return tempDay;
	}
	public int getTempDayInt() {
		return Integer.valueOf(tempDay);
	}

	public void setTempDay(String tempDay) {
		this.tempDay = tempDay;
	}

	public String getTempNight() {
		return tempNight;
	}
	public int getTempNightInt() {
		return Integer.valueOf(tempNight);
	}
	public void setTempNight(String tempNight) {
		this.tempNight = tempNight;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getWindDegreesDay() {
		return windDegreesDay;
	}

	public void setWindDegreesDay(String windDegreesDay) {
		this.windDegreesDay = windDegreesDay;
	}

	public String getWindDegreesNight() {
		return windDegreesNight;
	}

	public void setWindDegreesNight(String windDegreesNight) {
		this.windDegreesNight = windDegreesNight;
	}

	public String getWindDirDay() {
		return windDirDay;
	}

	public void setWindDirDay(String windDirDay) {
		this.windDirDay = windDirDay;
	}

	public String getWindDirNight() {
		return windDirNight;
	}

	public void setWindDirNight(String windDirNight) {
		this.windDirNight = windDirNight;
	}

	public String getWindLevelDay() {
		return windLevelDay;
	}

	public void setWindLevelDay(String windLevelDay) {
		this.windLevelDay = windLevelDay;
	}

	public String getWindLevelNight() {
		return windLevelNight;
	}

	public void setWindLevelNight(String windLevelNight) {
		this.windLevelNight = windLevelNight;
	}

	public String getWindSpeedDay() {
		return windSpeedDay;
	}

	public void setWindSpeedDay(String windSpeedDay) {
		this.windSpeedDay = windSpeedDay;
	}

	public String getWindSpeedNight() {
		return windSpeedNight;
	}

	public void setWindSpeedNight(String windSpeedNight) {
		this.windSpeedNight = windSpeedNight;
	}

	public String getConditionIdDaySrc() {
		return conditionIdDaySrc;
	}

	public void setConditionIdDaySrc(String conditionIdDaySrc) {
		this.conditionIdDaySrc = conditionIdDaySrc;
	}

	public String getConditionIdNightSrc() {
		return conditionIdNightSrc;
	}

	public void setConditionIdNightSrc(String conditionIdNightSrc) {
		this.conditionIdNightSrc = conditionIdNightSrc;
	}
}
