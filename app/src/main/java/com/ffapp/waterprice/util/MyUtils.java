package com.ffapp.waterprice.util;

import android.text.TextUtils;

import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;

import my.MySharedPreferences;

/**
 * Created by niany on
 */

public class MyUtils {

    private static final String SP_ACCESSKEY= "sp_accessKey";
    private static final String SP_AREAID= "sp_areaId";
    private static final String SP_USER_NAME= "sp_username";
    private static final String SP_IP= "app_ip";
    private static final String SP_TENANT= "sp_tenant";
    private static final String SP_AMBIENT= "sp_ambient";
    private static final String SP_KEY_USER = "user";


    public static String getIp(){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.mContext);
        if(TextUtils.isEmpty(msp.getString(SP_IP))){
            return  Constants.URL_SERVICE;
        }

        return msp.getString(SP_IP);
    }

    public static String getUser(){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.mContext);
        if(TextUtils.isEmpty(msp.getString(SP_KEY_USER))){
            return "";
        }
        return msp.getString(SP_KEY_USER);
    }

    public static void putIP( String ip){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_IP,ip);
    }

    public static String getServiceAPI(){
        return getIp()+ "";
    }

    public static String getImageUrlBySize(String urlorg,int with,int height){
        if(TextUtils.isEmpty(urlorg))return "";
        if(urlorg.endsWith(".jpg")){
          return   urlorg+"@w"+with+"_h"+height+".jpg";
        }else if(urlorg.endsWith(".png")){
            return   urlorg+"@w"+with+"_h"+height+".png";
        }else{
            return   urlorg+"@w"+with+"_h"+height+".jpg";
        }
    }

//    public static String getImageUrl(String urlorg){
//        if(TextUtils.isEmpty(urlorg))return "";
//
//        LogUtil.i("getImageUrl--->"+Constants.URL_API_IMG+urlorg);
//        return   getServiceAPI()+Constants.URL_API_IMG+urlorg;
//    }
//    public static String getImageUrlCommon(String urlorg){
//        if(TextUtils.isEmpty(urlorg))return "";
//
//        return   getServiceAPI()+Constants.URL_API_IMG_COMMON+urlorg;
//    }


//    public static String getIP( ){
//        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
//        return  msp.getString(SP_SERVERURL);
//    }

    public static String getTenant( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_TENANT,"");
    }
    public static void putTenant( String tenant){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
          msp.putString(SP_TENANT,tenant);
    }

    public static String getAccessKey( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_ACCESSKEY,"");
    }

    public static String getAmbient( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_AMBIENT,"");
    }

    public static void putAccessKey( String key){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_ACCESSKEY,key);
    }

    public static void putAreaId( String areaId){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_AREAID,areaId);
    }
    public static String getAreaId( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
       return msp.getString(SP_AREAID);
    }


    public static void putUserName( String userName){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_USER_NAME,userName);
    }
    public static String getUserName( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return msp.getString(SP_USER_NAME);
    }

    public static  void putSerciceData(BaseListData data){
        if(data == null)return;
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_IP,data.getId());
        msp.putString(SP_ACCESSKEY,data.getAccessKey());
        msp.putString(SP_TENANT,data.getTenant());
        msp.putString(SP_AMBIENT,data.getName());
    }

    public static BaseListData  getSerciceData(){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        if(TextUtils.isEmpty(msp.getString(SP_IP))){
            return null;
        }
        BaseListData data = new BaseListData();
        data.setAccessKey(msp.getString(SP_ACCESSKEY));
        data.setTenant(msp.getString(SP_TENANT));
        data.setAccount(msp.getString(SP_KEY_USER));
        data.setId(msp.getString(SP_IP));
        data.setName(msp.getString(SP_AMBIENT));
        return data;
    }

}
