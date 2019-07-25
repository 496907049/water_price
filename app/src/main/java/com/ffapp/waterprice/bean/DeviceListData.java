package com.ffapp.waterprice.bean;

import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;
import com.ffapp.waterprice.R;

public class DeviceListData extends BasisBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String lgtd;
    private String lttd;
    private String stlc;
    private String stnm;

    private boolean isCheck;
    private int dsfl;

    private long createDate;
    private String deviceCode;
    private String deviceName;
    private String deviceTypeId;
    private String id;
    private long messageAt;
    private int state;     //1是在线，其他都是不在线

    private String rainfall;//降雨量(mm)
    private String waterLevel;//水位(m)
    private String gateUp;//闸上水位(m)
    private String gateDown;//闸下水位(m)
    private String flowVelocity;//流速(m/s)
    private String averageFlowVelocity;//平均流速(m/s)
    private String flow;//瞬时流量(m³/s)
    private String totalFlow;//累计流量(m³)
    private String depth;//埋深(mm)
    private String soilTemperature;//土壤温度(°C)
    private String soilHumidity;//土壤湿度(%RH)
    private String soilSalinity;//土壤盐分(μS/cm)
    private String soilPh;//土壤PH值
    private String soilWaterPotential;//土壤水势
    private String temperature;//温度(°C)
    private String humidity;//湿度(%RH)
    private String atmos;//大气压力(Kpa)
    private String windSpeed;//风速(m/s)
    private String windDirection;//风向
    private String radiation;//辐射(W/㎡)
    private String pressure;//压力(Mpa)
    private String waterTemperature;//水温(°C)

    public int getDsfl() {
        return dsfl;
    }

    public void setDsfl(int dsfl) {
        this.dsfl = dsfl;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getGateUp() {
        return gateUp;
    }

    public void setGateUp(String gateUp) {
        this.gateUp = gateUp;
    }

    public String getGateDown() {
        return gateDown;
    }

    public void setGateDown(String gateDown) {
        this.gateDown = gateDown;
    }

    public String getFlowVelocity() {
        return flowVelocity;
    }

    public void setFlowVelocity(String flowVelocity) {
        this.flowVelocity = flowVelocity;
    }

    public String getAverageFlowVelocity() {
        return averageFlowVelocity;
    }

    public void setAverageFlowVelocity(String averageFlowVelocity) {
        this.averageFlowVelocity = averageFlowVelocity;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(String soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public String getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(String soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public String getSoilSalinity() {
        return soilSalinity;
    }

    public void setSoilSalinity(String soilSalinity) {
        this.soilSalinity = soilSalinity;
    }

    public String getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(String soilPh) {
        this.soilPh = soilPh;
    }

    public String getSoilWaterPotential() {
        return soilWaterPotential;
    }

    public void setSoilWaterPotential(String soilWaterPotential) {
        this.soilWaterPotential = soilWaterPotential;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getAtmos() {
        return atmos;
    }

    public void setAtmos(String atmos) {
        this.atmos = atmos;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getRadiation() {
        return radiation;
    }

    public void setRadiation(String radiation) {
        this.radiation = radiation;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(String waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(String signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    private String signalIntensity;//信号强度
    private String voltage;//电压电量


    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMessageAt(long messageAt) {
        this.messageAt = messageAt;
    }

    public long getMessageAt() {
        return messageAt;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getVoltage() {
        return voltage;
    }


    public String getLgtd() {
        return lgtd;
    }

    public void setLgtd(String lgtd) {
        this.lgtd = lgtd;
    }

    public String getLttd() {
        return lttd;
    }

    public void setLttd(String lttd) {
        this.lttd = lttd;
    }

    public String getStlc() {
        if (TextUtils.isEmpty(stlc)) return "-";
        return stlc;
    }

    public void setStlc(String stlc) {
        this.stlc = stlc;
    }

    public String getStnm() {
        if (TextUtils.isEmpty(stnm)) return "-";
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getMapMarkerResid() {
        switch (typeId) {
//            case "1":
//                return R.drawable.map_icon_location_grey;
//            case "2" :
//                return R.drawable.map_icon_location_blue;
//            case "3":
//                return R.drawable.map_icon_location_red;
//            case "4":
//                return R.drawable.map_icon_location_blue;
//            default:
//                return R.drawable.map_icon_location_grey;
        }
        return 0;
    }

    public LatLng getLatlng() {
        if (TextUtils.isEmpty(lttd)) {
            return new LatLng(0, 0);
        }
        LatLng latLng = new LatLng(Double.valueOf(lttd), Double.valueOf(lgtd));
        return latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String address;
    private String areaId;
    private int cameraChannel;
    private String cameraType;
    private String contact;
    private long createTime;
    private String dvrCode;
    private int isDisable;
    private String latitude;
    private String longitude;
    private String modelId;
    private String password;
    private String phone;
    private String remark;
    private String serverAddress;
    private String typeId;
    private String watershed;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setCameraChannel(int cameraChannel) {
        this.cameraChannel = cameraChannel;
    }

    public int getCameraChannel() {
        return cameraChannel;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setDvrCode(String dvrCode) {
        this.dvrCode = dvrCode;
    }

    public String getDvrCode() {
        return dvrCode;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }

    public int getIsDisable() {
        return isDisable;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setWatershed(String watershed) {
        this.watershed = watershed;
    }

    public String getWatershed() {
        return watershed;
    }


    public String getVideoUrl(){
        String url = String.format("rtmp://%s/dvrlive/%s_%s.live",getServerAddress(),getDeviceCode(),0+"");
//		String url = String.format("rtmp://%s:%s/dvrlive/%s_%s.live",loginBean.getDvrVideoHost(),loginBean.getDvrVideoPort(),getStcd(),params+"");
        return url;
    }

}
