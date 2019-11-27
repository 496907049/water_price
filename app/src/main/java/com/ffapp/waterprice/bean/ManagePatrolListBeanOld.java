package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class ManagePatrolListBeanOld extends BaseListBeanYL {

    @JSONField(name = "data")
    private ArrayList<ManagePatrolListDataOld> list = new ArrayList<ManagePatrolListDataOld>();

    public ArrayList<ManagePatrolListDataOld> getList() {
        return list;
    }

    public void setList(ArrayList<ManagePatrolListDataOld> list) {
        this.list = list;
    }

    public void addListBean(ManagePatrolListBeanOld listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<ManagePatrolListDataOld>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
