package com.ffapp.waterprice.bean;

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
        return sumRatifiedWaterConsumption;
    }

    public void setSumRealWaterConsumption(String sumRealWaterConsumption) {
        this.sumRealWaterConsumption = sumRealWaterConsumption;
    }
    public String getSumRealWaterConsumption() {
        return sumRealWaterConsumption;
    }

    public void setLastWaterConsumption(String lastWaterConsumption) {
        this.lastWaterConsumption = lastWaterConsumption;
    }
    public String getLastWaterConsumption() {
        return lastWaterConsumption;
    }

    public void setSumDevice(int sumDevice) {
        this.sumDevice = sumDevice;
    }
    public int getSumDevice() {
        return sumDevice;
    }

}
