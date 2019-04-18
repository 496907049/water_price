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

    private static final String SP_APPID= "sp_appid";
    private static final String SP_IP= "app_ip";
    private static final String SP_AUTHKEY= "sp_authkey";
    private static final String SP_AMBIENT= "sp_ambient";


    public static String getIp(){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.mContext);
        if(TextUtils.isEmpty(msp.getString(SP_IP))){
            return  Constants.URL_SERVICE;
        }

        return msp.getString(SP_IP);
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

    public static String getAppid( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_APPID,"10001");
    }
    public static void putAppid( String appid){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
          msp.putString(SP_APPID,appid);
    }

    public static String getAuthKey( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_AUTHKEY,"");
    }

    public static String getAmbient( ){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        return  msp.getString(SP_AMBIENT,"");
    }

    public static void putAuthKey( String key){
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_AUTHKEY,key);
    }

    public static  void putSerciceData(BaseListData data){
        if(data == null)return;
        MySharedPreferences msp = new MySharedPreferences(BasisApp.getInstance());
        msp.putString(SP_APPID,data.getAppid());
        msp.putString(SP_AUTHKEY,data.getAuthkey());
        msp.putString(SP_AMBIENT,data.getName());
        msp.putString(SP_IP,data.getId());
    }
}
