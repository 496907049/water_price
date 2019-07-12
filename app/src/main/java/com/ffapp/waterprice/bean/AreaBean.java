package com.ffapp.waterprice.bean;

public class AreaBean extends BasisBean {

    private String areaNumber;   //区域编号
    private String id;      //区域ID
    private String latitude;   //地图纬度
    private String longitude;    //地图经度
    private String name;     //区域名称
    private String parentCode;    //父节点编号
    private String parentId;      //父节点ID

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public String getParentCode() {
        return parentCode;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentId() {
        return parentId;
    }

}
