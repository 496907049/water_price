package com.ffapp.waterprice.bean;

import android.text.TextUtils;

public class DataOverviewBean extends BasisBean{
    private int sumWaterUser;     //用水户数量
    private int sumLineDevice;          //在线数量
    private String sumRatifiedWaterConsumption;   //统计指标用水量
    private String sumRealWaterConsumption;     //实际水量
    private String lastWaterConsumption;     //剩余水量
    private int sumDevice;      //站点数量
    public void setSumWaterUser(int sumWaterUser) {
        this.sumWaterUser = sumWaterUser;
    }
    public int getSumWaterUser() {
        return sumWaterUser;
    }

    public void setSumLineDevice(int sumLineDevice) {
        this.sumLineDevice = sumLineDevice;
    }
    public int getSumLineDevice() {
        return sumLineDevice;
    }

    public void setSumRatifiedWaterConsumption(String sumRatifiedWaterConsumption) {
        this.sumRatifiedWaterConsumption = sumRatifiedWaterConsumption;
    }
    public String getSumRatifiedWaterConsumption() {
        if(TextUtils.isEmpty(sumRatifiedWaterConsumption)){
            return "0";
        }
        return sumRatifiedWaterConsumption;
    }

    public void setSumRealWaterConsumption(String sumRealWaterConsumption) {
        this.sumRealWaterConsumption = sumRealWaterConsumption;
    }
    public String getSumRealWaterConsumption() {
        if(TextUtils.isEmpty(sumRealWaterConsumption)){
            return "0";
        }
        return sumRealWaterConsumption;
    }

    public void setLastWaterConsumption(String lastWaterConsumption) {
        this.lastWaterConsumption = lastWaterConsumption;
    }
    public String getLastWaterConsumption() {
        if(TextUtils.isEmpty(lastWaterConsumption)){
            return "0";
        }
        return lastWaterConsumption;
    }

    public void setSumDevice(int sumDevice) {
        this.sumDevice = sumDevice;
    }
    public int getSumDevice() {
        return sumDevice;
    }

}
