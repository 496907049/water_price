package com.ffapp.waterprice.bean;

public class DataOverviewBean extends BasisBean{
    private int sumWaterUser;
    private int sumLineDevice;
    private String sumRatifiedWaterConsumption;
    private String sumRealWaterConsumption;
    private String lastWaterConsumption;
    private int sumDevice;
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
