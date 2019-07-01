package com.ffapp.waterprice.bean;


import java.util.ArrayList;

public class AlarmMsgListBean extends BaseListBeanYL {

    private ArrayList<AlarmMsgListData> list = new ArrayList<AlarmMsgListData>();
    private int unreadNum;

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public ArrayList<AlarmMsgListData> getList() {
        return list;
    }

    public void setList(ArrayList<AlarmMsgListData> list) {
        this.list = list;
    }

    public void addListBean(AlarmMsgListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrent_page() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<AlarmMsgListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
