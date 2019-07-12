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

    public static  void putSerciceData(BaseListData data){
        if(data == null)return;
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_ACCESSKEY,data.getAccessKey());
        msp.putString(SP_TENANT,data.getTenant());
        msp.putString(SP_AMBIENT,data.getName());
        msp.putString(SP_IP,data.getId());
    }
}
