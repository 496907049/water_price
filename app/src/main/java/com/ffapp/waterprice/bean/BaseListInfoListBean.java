package com.ffapp.waterprice.bean;



import android.text.TextUtils;

import java.util.ArrayList;

public class BaseListInfoListBean extends BaseListBean {

    private ArrayList<BaseListDataListBean> list = new ArrayList<BaseListDataListBean>();

    private String title;

    public ArrayList<BaseListDataListBean> getList() {
        return list;
    }

    public void setList(ArrayList<BaseListDataListBean> list) {
        this.list = list;
    }

    public String getTitle() {
        if(TextUtils.isEmpty(title))return "";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
