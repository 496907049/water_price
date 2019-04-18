package com.ffapp.baseapp.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;

public class BaseListDataListBean extends BaseListBean {

    private ArrayList<BaseListData> list = new ArrayList<BaseListData>();

    private String title;

    @JSONField(name = "data")
    public ArrayList<BaseListData> getList() {
        return list;
    }

    @JSONField(name = "data")
    public void setList(ArrayList<BaseListData> list) {
        this.list = list;
    }


    public void addListBean(BaseListDataListBean listbean) {
        if (listbean == null)
            return;
        if (this.getCurrentPage() == INIT_PAGE) {
            setList(listbean.getList());
        } else {
            if (list == null)
                list = new ArrayList<BaseListData>();
            list.addAll(listbean.getList());
        }
        setListBeanData(listbean);
    }

    public  ArrayList<String> getListString(){
        ArrayList<String> listString = new ArrayList<>();
        for(BaseListData data:list){
            listString.add(data.getName());
        }
        return  listString;
    }

    public BaseListData getDataById(String id){
        for(BaseListData data:list){
           if(id.equals(data.getId()))return data;
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCheck(int position){
        list.get(position).setCheck(!list.get(position).isCheck());
    }

    public  String getParamsNoCheck(){
        StringBuilder builder = new StringBuilder();
        for(BaseListData data:list){
            builder.append(data.isCheck()?"1":"0");
        }
        return  builder.toString();
    }
}
