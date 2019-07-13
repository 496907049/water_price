package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.jaygoo.bean.Site;

import java.util.ArrayList;

public class DeviceTypeListBean extends BaseListBean {

    @JSONField(name = "data")
    private ArrayList<Site> list = new ArrayList<Site>();

    public ArrayList<Site> getList() {
        return list;
    }

    public void setList(ArrayList<Site> list) {
        this.list = list;
    }

    public void addListBean(DeviceTypeListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<Site>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
