package com.ffapp.waterprice.bean;


import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.manage.BackService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;

import my.FileUtils;
import my.TimeUtils;

public class ManageLocationListBean extends BaseListBeanYL {

    public static final String FILE_CACHE = Constants.DIR_FILECACHE
            + "location.data";

    private ArrayList<ManageLocationListData> list = new ArrayList<ManageLocationListData>();

    public ArrayList<ManageLocationListData> getList() {
        return list;
    }
    public void setList(ArrayList<ManageLocationListData> list) {
        this.list = list;
    }

    public static ManageLocationListBean getFromCache() {
        ManageLocationListBean data;
        data = (ManageLocationListBean) FileUtils.readObject(FILE_CACHE);
//        LogUtil.i("login---->getFromCache---" + (data == null));
        return data;
    }

    public void save() {
        FileUtils.cacheObject(FILE_CACHE, this);
    }
    private static void delete() {
        FileUtils.cacheObject(FILE_CACHE, null);
    }

    public static void addLocation(double latitude,double longtitude){
        ManageLocationListBean listBean = getFromCache();
        if(listBean == null)listBean = new ManageLocationListBean();
        listBean.check();
//        latitude -= new Random().nextInt(10)*1.2*0.02;
//        longtitude+=  new Random().nextInt(10)*1.2*0.02;
        ManageLocationListData data = new ManageLocationListData();
        data.setLatitude(latitude);
        data.setLongtitude(longtitude);
        data.setTime(Calendar.getInstance().getTimeInMillis());
        listBean.getList().add(data);
        listBean.save();
        EventBus.getDefault().post(new BackService.EventLocation());
    }

    public void check(){
        if(list.size() == 0)return;

        ManageLocationListData data;
        ArrayList<ManageLocationListData> listnew = new ArrayList<>();
        for(int i = 0 , l = list.size(); i < l ;i ++){
            data = list.get(i);
            if(TimeUtils.getDistanceDays(data.getTime(), Calendar.getInstance().getTime().getTime()) >=1){
            }else {
                listnew.add(data);
            }
        }
        list.clear();
        list.addAll(listnew);

//        ManageLocationListData lastdata = list.get(list.size()-1);
//        if(TimeUtils.getDistanceDays(lastdata.getTime(), Calendar.getInstance().getTime().getTime()) >=1){
//            list.clear();
//            save();
//        }

    }
}
