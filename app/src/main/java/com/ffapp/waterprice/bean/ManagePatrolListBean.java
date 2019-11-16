package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class ManagePatrolListBean extends BaseListBeanYL {

    @JSONField(name = "data")
    private ArrayList<ManagePatrolListData> list = new ArrayList<ManagePatrolListData>();

    public ArrayList<ManagePatrolListData> getList() {
        return list;
    }

    public void setList(ArrayList<ManagePatrolListData> list) {
        this.list = list;
    }

    public void addListBean(ManagePatrolListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<ManagePatrolListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
