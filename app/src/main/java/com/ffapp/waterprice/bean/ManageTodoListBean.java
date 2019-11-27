package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class ManageTodoListBean extends BaseListBeanYL {

    @JSONField(name = "data")
    private ArrayList<ManageTodoListData> list = new ArrayList<ManageTodoListData>();

    public ArrayList<ManageTodoListData> getList() {
        return list;
    }

    public void setList(ArrayList<ManageTodoListData> list) {
        this.list = list;
    }

    public void addListBean(ManageTodoListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<ManageTodoListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
