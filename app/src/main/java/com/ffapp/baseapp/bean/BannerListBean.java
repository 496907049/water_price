package com.ffapp.baseapp.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class BannerListBean extends BaseListBeanBc {

    public final static String ID_HOME = "app",ID_INVITE = "invite",ID_NEWS = "article",ID_CANDY = "drop";

    @JSONField(name = "data")
    private ArrayList<BannerListData> list = new ArrayList<BannerListData>();

    public ArrayList<BannerListData> getList() {
        return list;
    }

    public void setList(ArrayList<BannerListData> list) {
        this.list = list;
    }

    public void addListBean(BannerListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<BannerListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }
}
