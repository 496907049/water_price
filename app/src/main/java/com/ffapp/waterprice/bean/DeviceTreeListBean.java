package com.ffapp.waterprice.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class DeviceTreeListBean extends BaseListBeanBc{

    @JSONField(name = "list")
    private ArrayList<DeviceTreeListData> list = new ArrayList<DeviceTreeListData>();

    public ArrayList<DeviceTreeListData> getList() {
        return list;
    }

    public void setList(ArrayList<DeviceTreeListData> list) {
        this.list = list;
    }



}
