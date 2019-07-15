package com.mic.adressselectorlib;

import java.util.ArrayList;

/**
 * Author: Blincheng.
 * Date: 2017/5/9.
 * Description:
 */

public class City implements CityInterface {
    private String name;
    private String id;
    private ArrayList<DeviceTreeChildListData> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<DeviceTreeChildListData> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<DeviceTreeChildListData> children) {
        this.children = children;
    }

    @Override
    public String getCityName() {
        return name;
    }

    @Override
    public ArrayList<DeviceTreeChildListData> getCityChildren() {
        return children;
    }

    @Override
    public String getAreaId() {
        return id;
    }

}
