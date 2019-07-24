package com.ffapp.waterprice.bean;

import java.util.ArrayList;

public class DeviceListBean extends BaseListBeanYL {

    private ArrayList<DeviceListData> list = new ArrayList<DeviceListData>();

    public ArrayList<DeviceListData> getList() {
        return list;
    }

    public void setList(ArrayList<DeviceListData> list) {
        this.list = list;
    }

    public void addListBean(DeviceListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<DeviceListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }

    public void setAllCheckDefault(){
        ArrayList<DeviceListData> list = getList();
        for (DeviceListData data : list){
            data.setCheck(false);
        }
    }


}
