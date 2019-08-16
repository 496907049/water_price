package com.ffapp.waterprice.bean;

import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;
import com.ffapp.waterprice.R;

import java.util.Map;

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
    private long countDate;
    private int state;     //1是在线，其他都是不在线

    private String waterUserName;
    private String waterUserNumber;

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
    private String salinity;//土壤盐分(μS/cm)
    private String soilPh;//土壤PH值
    private String ph;//土壤PH值
    private String soilWaterPotential;//土壤水势
    private String temperature;//温度(°C)
    private String humidity;//湿度(%RH)
    private String atmos;//大气压力(Kpa)
    private String windSpeed;//风速(m/s)
    private String windDirection;//风向
    private String radiation;//辐射(W/㎡)
    private String pressure;//压力(Mpa)
    private String waterTemperature;//水温(°C)
    private String signalIntensity;//信号强度
    private String voltage;//电压电量
    private String ext1;//TD11
    private String ext2;//TD12
    private String ext3;//TD13
    private String ext4;//TD14
    private String ext5;//TD15
    private String ext6;//TD16
    private String ext7;//TD17
    private String ratifiedWaterConsumption;//核定用水量
    private String realWaterConsumption;//实际用水量
    private String availableCount;//剩余用水量

    private String warningType;//报警类型

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getRatifiedWaterConsumption() {
        return ratifiedWaterConsumption;
    }

    public void setRatifiedWaterConsumption(String ratifiedWaterConsumption) {
        this.ratifiedWaterConsumption = ratifiedWaterConsumption;
    }

    public String getRealWaterConsumption() {
        return realWaterConsumption;
    }

    public void setRealWaterConsumption(String realWaterConsumption) {
        this.realWaterConsumption = realWaterConsumption;
    }

    public String getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(String availableCount) {
        this.availableCount = availableCount;
    }

    public int getDsfl() {
        return dsfl;
    }

    public void setDsfl(int dsfl) {
        this.dsfl = dsfl;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public String getSalinity() {
        return salinity;
    }

    public void setSalinity(String salinity) {
        this.salinity = salinity;
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

    public String getExt1() {
        return ext1;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getExt4() {
        return ext4;
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4;
    }

    public String getExt5() {
        return ext5;
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5;
    }

    public String getExt6() {
        return ext6;
    }

    public void setExt6(String ext6) {
        this.ext6 = ext6;
    }

    public String getExt7() {
        return ext7;
    }

    public void setExt7(String ext7) {
        this.ext7 = ext7;
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
        if (TextUtils.isEmpty(stlc)) return "";   //此处显示内容
        return stlc;
    }

    public void setStlc(String stlc) {
        this.stlc = stlc;
    }

    public String getStnm() {
        if (TextUtils.isEmpty(deviceName)) return "-";
        return deviceName;
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
            case "1":
                if(state != 1){
                    return R.mipmap.map_icon_rain_offline;
                }
                return R.mipmap.map_icon_rain;
            case "2" :
                if(state != 1){
                    return R.mipmap.map_icon_water_offline;
                }
                return R.mipmap.map_icon_water;
            case "3":
                if(state != 1){
                    return R.mipmap.map_icon_video_offline;
                }
                return R.mipmap.map_icon_video;
            case "4":
                if(state != 1){
                    return R.mipmap.map_icon_weather_offline;
                }
                return R.mipmap.map_icon_weather;
            default:
                if(state != 1){
                    return R.mipmap.map_icon_soil_offline;
                }
                return R.mipmap.map_icon_soil;
        }
    }

    public LatLng getLatlng() {
        if (TextUtils.isEmpty(latitude)) {
            return new LatLng(0, 0);
        }
        LatLng latLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        return latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaterUserName() {
        return waterUserName;
    }

    public void setWaterUserName(String waterUserName) {
        this.waterUserName = waterUserName;
    }

    public String getWaterUserNumber() {
        return waterUserNumber;
    }

    public void setWaterUserNumber(String waterUserNumber) {
        this.waterUserNumber = waterUserNumber;
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
    private String appAddress;
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

    public long getCountDate() {
        return countDate;
    }

    public void setCountDate(long countDate) {
        this.countDate = countDate;
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

    public String getAppAddress() {
        return appAddress;
    }

    public void setAppAddress(String appAddress) {
        this.appAddress = appAddress;
    }

    public String getVideoUrl(){
        String url = String.format("rtmp://%s/dvrlive/%s_%s.live",getAppAddress(),getDeviceCode(),0+"");
//        String url = "rtmp://47.99.32.215:9592/dvrlive/2019051003_0.live";
//		String url = String.format("rtmp://%s:%s/dvrlive/%s_%s.live",loginBean.getDvrVideoHost(),loginBean.getDvrVideoPort(),getStcd(),params+"");
        return url;
    }


    /**
     * 获取流量列表分页数据
     */

    private String title;
    private Map<String,String> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }
}
