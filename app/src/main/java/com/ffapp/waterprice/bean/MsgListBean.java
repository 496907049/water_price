package com.ffapp.waterprice.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class MsgListBean extends BaseListBeanYL {

    @JSONField(name = "data")
    private ArrayList<MsgListData> list = new ArrayList<MsgListData>();

    public ArrayList<MsgListData> getList() {
        return list;
    }

    public void setList(ArrayList<MsgListData> list) {
        this.list = list;
    }

    public void addListBean(MsgListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<MsgListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
