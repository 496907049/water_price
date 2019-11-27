package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class ManageMaintainListBean extends BaseListBeanYL {

    @JSONField(name = "data")
    private ArrayList<ManageMaintainListData> list = new ArrayList<ManageMaintainListData>();

    public ArrayList<ManageMaintainListData> getList() {
        return list;
    }

    public void setList(ArrayList<ManageMaintainListData> list) {
        this.list = list;
    }

    public void addListBean(ManageMaintainListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<ManageMaintainListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
