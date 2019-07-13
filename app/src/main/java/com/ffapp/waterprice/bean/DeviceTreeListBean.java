package com.ffapp.waterprice.bean;

import java.util.ArrayList;

public class DeviceTreeListBean extends BaseListBean {

    private ArrayList<DeviceTreeListData> list = new ArrayList<DeviceTreeListData>();

    public ArrayList<DeviceTreeListData> getList() {
        return list;
    }

    public void setList(ArrayList<DeviceTreeListData> list) {
        this.list = list;
    }

    public void addListBean(DeviceTreeListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<DeviceTreeListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
